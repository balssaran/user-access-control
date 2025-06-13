package org.mounanga.userservice.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.mounanga.userservice.dto.MenuDTO;
import org.mounanga.userservice.dto.PageModel;
import org.mounanga.userservice.dto.RoleDTO;
import org.mounanga.userservice.entity.Menu;
import org.mounanga.userservice.entity.Role;
import org.mounanga.userservice.entity.RoleMenu;
import org.mounanga.userservice.exception.NotAuthorizedException;
import org.mounanga.userservice.exception.ResourceAlreadyExistException;
import org.mounanga.userservice.exception.RoleNotFoundException;
import org.mounanga.userservice.repository.MenuRepository;
import org.mounanga.userservice.repository.RoleMenuRepository;
import org.mounanga.userservice.repository.RoleRepository;
import org.mounanga.userservice.service.MenuService;
import org.mounanga.userservice.service.RoleMenuService;
import org.mounanga.userservice.service.RoleService;
import org.mounanga.userservice.util.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RoleMenuServiceImpl implements RoleMenuService {

	private static final Logger log = LoggerFactory.getLogger(RoleMenuServiceImpl.class);

	private final RoleMenuRepository roleMenuRepository;

	public RoleMenuServiceImpl(RoleMenuRepository roleMenuRepository) {
		this.roleMenuRepository = roleMenuRepository;
	}

	@Transactional
	@Override
	public RoleMenu createMenu(RoleMenu roleMenu) {
		log.info("In createRole()");
		// Menu menu = Mappers.fromMenuDTO(dto);
		Optional<RoleMenu> roleMenuOptional = roleMenuRepository.findByRoleandMenu(roleMenu.getRole().getId(),
				roleMenu.getMenu().getId());
		if (!roleMenuOptional.isPresent()) {
			throw new ResourceAlreadyExistException("Role and Menu with " + roleMenu.getRole().getId() + ", "
					+ roleMenu.getMenu().getId() + " already exists");
		}

		RoleMenu savedRoleMenu = roleMenuRepository.save(roleMenu);
		log.info("Role Id '{}' and Menu ID '{}' created at '{}' by '{}'", savedRoleMenu.getRole().getId(),
				savedRoleMenu.getMenu().getId());
		return savedRoleMenu;
	}

	@Transactional
	@Override
	public RoleMenu updateMenu(Long id, @NotNull RoleMenu roleMenu) {
		log.info("In updateRole()");
		// Menu menu = findMenuById(id);
		Optional<RoleMenu> roleMenuOptional = roleMenuRepository.findByRoleandMenu(roleMenu.getRole().getId(),
				roleMenu.getMenu().getId());
		if (!roleMenuOptional.isPresent()) {
			throw new ResourceAlreadyExistException("Role and Menu with " + roleMenu.getRole().getId() + ", "
					+ roleMenu.getMenu().getId() + " already exists");
		}

		RoleMenu updatedroleMenu = roleMenuRepository.save(roleMenu);
		log.info("Role Id '{}' and Menu ID '{}' created at '{}' by '{}'", updatedroleMenu.getRole().getId(),
				updatedroleMenu.getMenu().getId());
		return updatedroleMenu;
	}

	public boolean hasAccess(String roleName, String path) {
		
		
	boolean returntype= roleMenuRepository.hasAccess(roleName,
				path);
	return returntype;
		
		
	}

	/*
	 * private Menu findMenuById(Long id) { return roleMenuRepository.findById(id)
	 * .orElseThrow(() -> new RoleNotFoundException("Role with id " + id +
	 * " not found")); }
	 */

}
