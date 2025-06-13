package org.mounanga.userservice.service.implementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.mounanga.userservice.dto.PageModel;
import org.mounanga.userservice.dto.ProfileResponseDTO;
import org.mounanga.userservice.dto.UpdateEmailUsernameDTO;
import org.mounanga.userservice.dto.UserRequestDTO;
import org.mounanga.userservice.dto.UserResponseDTO;
import org.mounanga.userservice.dto.UserRoleMenuResponseDTO;
import org.mounanga.userservice.dto.UserRoleRequestDTO;
import org.mounanga.userservice.entity.Branch;
import org.mounanga.userservice.entity.Menu;
import org.mounanga.userservice.entity.Role;
import org.mounanga.userservice.entity.RoleMenu;
import org.mounanga.userservice.entity.User;
import org.mounanga.userservice.exception.FieldError;
import org.mounanga.userservice.exception.FieldValidationException;
import org.mounanga.userservice.exception.RoleNotFoundException;
import org.mounanga.userservice.exception.UserNotFoundException;
import org.mounanga.userservice.repository.BranchRepository;
import org.mounanga.userservice.repository.RoleRepository;
import org.mounanga.userservice.repository.UserRepository;
import org.mounanga.userservice.service.UserService;
import org.mounanga.userservice.util.MailingService;
import org.mounanga.userservice.util.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	private static final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);

	private final UserRepository userRepository;

	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final BranchRepository branchRepository;

	private final MailingService mailingService;

	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
			PasswordEncoder passwordEncoder, MailingService mailingService, BranchRepository branchRepository) {
		this.userRepository = userRepository;

		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.mailingService = mailingService;
		this.branchRepository = branchRepository;
	}

	@Transactional
	@Override
	public UserResponseDTO createUser(@NotNull UserRequestDTO dto) {
		log.info("In createUser()");
		validationBeforeSaved(dto.getEmail(), dto.getUsername(), dto.getUserId());
	
		User user = Mappers.fromUserRequestDTO(dto);
		/*
		 * Profile profile = Mappers.fromUserProfileRequestDTO(dto);
		 * user.setProfile(profile);
		 */
		String password = UUID.randomUUID().toString();
		user.setPassword(passwordEncoder.encode(password));
		user.setPasswordNeedsToBeChanged(true);
		user.setLastLogin(LocalDateTime.now());

		/*
		 * Role role = roleRepository.findById(dto.getRoleId()) .orElseThrow(() -> new
		 * RuntimeException("Role not found")); Branch branch =
		 * branchRepository.findById(dto.getBranchId()) .orElseThrow(() -> new
		 * RuntimeException("Branch not found")); user.setRole(role);
		 * user.setBranch(branch);
		 */

		User savedUser = userRepository.save(user);

		log.info("User Created successfully");
		/*
		 * String body = String.format( """
		 * 
		 * Your account has been created in our system. <br /> Please use the following
		 * password to log in for the first time. We recommend changing it after logging
		 * in. <br />
		 * 
		 * %s """, password);
		 */
		mailingService.sendMail(user.getEmail(), "User Created", password);

		log.info("User saved with id '{}' at '{}' by '{}'", savedUser.getId(), savedUser.getCreatedDate(),
				savedUser.getCreateBy());
		return Mappers.fromUser(savedUser);
	}

	@Transactional
	@Override
	public UserResponseDTO updateUser(Long id, @NotNull UserRequestDTO dto) {
		log.info("In updateUser()");
		User existingUser = findUserById(id);
		validationBeforeUpdate(existingUser, dto.getEmail(), dto.getUsername());
		existingUser = Mappers.fromUserRequestDTOforUpdate(dto,existingUser);
		User updatedUser = userRepository.save(existingUser);
		log.info("user with id '{}' updated at '{}' by '{}'", updatedUser.getId(), updatedUser.getLastModifiedDate(),
				updatedUser.getLastModifiedBy());
		return Mappers.fromUser(updatedUser);
	}

	/*
	 * @Transactional
	 * 
	 * @Override public ProfileResponseDTO updateProfile(Long id, @NotNull
	 * UserRequestDTO dto) { log.info("In updateProfile()"); Profile profile =
	 * profileRepository.findby(id).orElseThrow(() -> new
	 * UserNotFoundException(id)); profile.setFirstname(dto.getFirstname());
	 * profile.setLastname(dto.getLastname());
	 * profile.setBirthday(dto.getBirthday()); profile.setGender(dto.getGender());
	 * profile.setPlaceOfBirth(dto.getPlaceOfBirth());
	 * profile.setNationality(dto.getNationality());
	 * if(!profile.getPin().equals(dto.getPin()) &&
	 * profileRepository.existsByPin(dto.getPin())) { throw new
	 * ResourceAlreadyExistException("there is already a profile with the same pin"
	 * ); } profile.setPin(dto.getPin()); Profile updatedProfile =
	 * profileRepository.save(profile);
	 * log.info("Profile with id '{}' updated at '{}' by '{}'", id,
	 * updatedProfile.getLastModifiedDate(), updatedProfile.getLastModifiedBy());
	 * return Mappers.fromUserProfile(updatedProfile); }
	 */

	@Override
	public UserResponseDTO getUserById(Long id) {
		log.info("In getUserById()");
		User user = findUserById(id);
		log.info("User with id '{}' found", user.getId());
		return Mappers.fromUser(user);
	}

	@Override
	public PageModel<UserResponseDTO> getAllUsers(int page, int size) {
		log.info("In getAllUsers()");
		Pageable pageable = PageRequest.of(page, size);
		Page<User> users = userRepository.findAll(pageable);
		log.info("'{}' Users found", users.getTotalElements());
		return Mappers.fromPageOfUsers(users, page);
	}

	@Override
	public PageModel<UserResponseDTO> searchUsers(String keyword, int page, int size) {
		log.info("In searchUsers()");
		Pageable pageable = PageRequest.of(page, size);
		Page<User> users = userRepository.search("%" + keyword + "%", pageable);
		log.info("'{}' Users found.", users.getTotalElements());
		return Mappers.fromPageOfUsers(users, page);
	}

	@Transactional
	@Override
	public void deleteUserById(Long id) {
		log.info("In deleteUserById()");
		User user = findUserById(id);
		/*
		 * if(user.isSuperAdmin()){ throw new
		 * NotAuthorizedException("You cannot delete a super administrator."); }
		 */
		userRepository.deleteById(id);
		log.info("User with id {} deleted.", id);
	}

	@Transactional
	@Override
	public void deleteAllUsersByIds(List<Long> ids) {
		log.info("In deleteAllUsersByIds()");
		List<User> users = userRepository.findAllById(ids).stream()
				/* .filter(user -> !user.isSuperAdmin()) */
				.toList();
		userRepository.deleteAll(users);
		log.info(" {} users deleted.", users.size());
	}

	@Transactional
	@Override
	public UserResponseDTO addRoleToUser(@NotNull UserRoleRequestDTO dto) {
		log.info("In addRoleToUser()");
		User user = findUserById(dto.userId());
		// Role role = findRoleByName(dto.roleName());
		/*
		 * if(role.isSuperAdminRole()){ throw new
		 * NotAuthorizedException("You cannot add a new super administrator."); }
		 */
		Role role = new Role();
		role.setId(dto.roleId());
		user.setRole(role);
		User updatedUser = userRepository.save(user);
		log.info("role '{}' has been added to user with id '{}', at '{}', by '{}'.", dto.roleId(), updatedUser.getId(),
				updatedUser.getLastModifiedDate(), updatedUser.getLastModifiedBy());
		return Mappers.fromUser(updatedUser);
	}

	@Transactional
	@Override
	public UserResponseDTO removeRoleFromUser(@NotNull UserRoleRequestDTO dto) {
		log.info("In removeRoleFromUser()");
		User user = findUserById(dto.userId());
		/*
		 * if(user.isSuperAdmin()){ throw new
		 * NotAuthorizedException("You cannot remove a role to a super administrator.");
		 * }
		 */
		// Role role = findRoleByName(dto.roleName());
		// user.removeRole(role);
		User updatedUser = userRepository.save(user);
		log.info("role '{}' has been removed from user with id '{}', at '{}', by '{}'.", dto.roleId(),
				updatedUser.getId(), updatedUser.getLastModifiedDate(), updatedUser.getLastModifiedBy());
		return Mappers.fromUser(updatedUser);
	}

	@Override
	public UserResponseDTO getUserByUsername(String username) {
		log.info("In getUserByUsername()");
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		log.info("user found with id '{}'.", user.getId());
		return Mappers.fromUser(user);
	}

	private User findUserById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("user with id '" + id + "' not found."));
	}

	private Role findRoleByName(String roleName) {
		return roleRepository.findByName(roleName).orElseThrow(() -> new RoleNotFoundException("Role not found."));
	}

	private void validationBeforeSaved(String email, String username, Long userId) {
		List<FieldError> fieldErrors = new ArrayList<>();
		if (userId== null || userId == 0) {
			if (userRepository.existsByEmail(email)) {
				fieldErrors.add(new FieldError("email", "Email already exists"));
			}
			if (userRepository.existsByUsername(username)) {
				fieldErrors.add(new FieldError("username", "Username already exists"));
			}

			if (!fieldErrors.isEmpty()) {
				throw new FieldValidationException("Validation error", fieldErrors);
			}
		}else {
			if (userRepository.existsByEmail(email,userId)) {
				fieldErrors.add(new FieldError("email", "Email already exists"));
			}
			if (userRepository.existsByUsername(username,userId)) {
				fieldErrors.add(new FieldError("username", "Username already exists"));
			}

			if (!fieldErrors.isEmpty()) {
				throw new FieldValidationException("Validation error", fieldErrors);
			}	
		}
	}

	private void validationBeforeUpdate(@NotNull User existingUser, String email, String username) {
		List<FieldError> fieldErrors = new ArrayList<>();
		if (!existingUser.getEmail().equals(email) && userRepository.existsByEmail(email)) {
			fieldErrors.add(new FieldError("email", "Email already exists"));
		}
		if (!existingUser.getUsername().equals(username) && userRepository.existsByUsername(username)) {
			fieldErrors.add(new FieldError("username", "Username already exists"));
		}
		if (!fieldErrors.isEmpty()) {
			throw new FieldValidationException("Validation error", fieldErrors);
		}
	}

	@Override
	public ProfileResponseDTO updateProfile(Long id, UserRequestDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	public UserRoleMenuResponseDTO getUserRoleMenu(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		Role role = user.getRole();
		List<RoleMenu> roleMenus = role.getRoleMenus();

		List<UserRoleMenuResponseDTO.MenuDTO> menus = roleMenus.stream().map(roleMenu -> {
			Menu m = roleMenu.getMenu();
			return new UserRoleMenuResponseDTO.MenuDTO(m.getId(), m.getName());
		}).toList();

		return new UserRoleMenuResponseDTO(user.getId(), new UserRoleMenuResponseDTO.RoleDTO(role.getName()), menus);
	}
}
