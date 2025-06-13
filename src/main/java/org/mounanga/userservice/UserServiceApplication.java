package org.mounanga.userservice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.mounanga.userservice.configuration.ApplicationProperties;
import org.mounanga.userservice.entity.Branch;
import org.mounanga.userservice.entity.Menu;
import org.mounanga.userservice.entity.Role;
import org.mounanga.userservice.entity.RoleMenu;
import org.mounanga.userservice.entity.User;
import org.mounanga.userservice.repository.BranchRepository;
import org.mounanga.userservice.repository.MenuRepository;
import org.mounanga.userservice.repository.RoleMenuRepository;
import org.mounanga.userservice.repository.RoleRepository;
import org.mounanga.userservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableJpaAuditing
@EnableAsync
@SpringBootApplication
public class UserServiceApplication {

	private final RoleMenuRepository roleMenuRepository;

	private final MenuRepository menuRepository;

	private final ApplicationProperties applicationProperties;

	private static final Logger log = LoggerFactory.getLogger(UserServiceApplication.class);

	UserServiceApplication(ApplicationProperties applicationProperties, MenuRepository menuRepository,
			RoleMenuRepository roleMenuRepository) {
		this.applicationProperties = applicationProperties;
		this.menuRepository = menuRepository;
		this.roleMenuRepository = roleMenuRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(RoleRepository roleRepository, UserRepository userRepository,
			BranchRepository branchRepository, MenuRepository menuRepository, RoleMenuRepository roleMenuRepository,
			PasswordEncoder passwordEncoder, ApplicationProperties applicationProperties) {
		return args -> {

			if (!roleRepository.existsBy()) {
				Role role = new Role();
				role.setName("SUPER_ADMIN");
				role.setDescription("the default role that all super administrators should have");
				role.setEnabled(Boolean.TRUE);
				role.setCreatedDate(LocalDateTime.now());
				roleRepository.save(role);

				/*
				 * log.info("SUPER admin role added"); role.setName("USER");
				 * role.setDescription("the default role that all users should have");
				 * log.info("*************************************************************");
				 * roleRepository.save(role); log.info("USER role added"); role = new Role();
				 * role.setName("ADMIN");
				 * role.setDescription("the default role that all administrators should have");
				 * roleRepository.save(role); log.info("ADMIN role added");
				 */

			}
			if (!branchRepository.existsBy()) {
				Branch branch = new Branch();
				branch.setBranchcode("T0001");
				branch.setName("Test Branch");
				branch.setLocation("Mumbai");
				branch.setEnabled(Boolean.TRUE);
				branch.setCreatedDate(LocalDateTime.now());
				branchRepository.save(branch);
			}
			if (!menuRepository.existsBy()) {
				Menu menu = new Menu();
				menu.setName("Dashboard");
				menu.setDisplayOrder(0);
				menu.setEnabled(Boolean.TRUE);
				menu.setFrontendPath("/admin/usercreation");
				menu.setBackendPath("/api/user");

				menuRepository.save(menu);
			}

			if (!roleMenuRepository.existsBy()) {
				RoleMenu roleMenu = new RoleMenu();
				Role role = new Role();
				role.setId(1l);
				roleMenu.setRole(role);
				Menu menu = new Menu();
				menu.setId(1l);
				roleMenu.setMenu(menu);
				roleMenuRepository.save(roleMenu);
			}

			if (!userRepository.existsBy()) {
				log.info("************************************************************");
				String system = "SYSTEM";
				/*
				 * Profile profile = new Profile(); profile.setGender(Gender.M);
				 * profile.setFirstname(system); profile.setLastname(system);
				 * profile.setNationality(system); profile.setBirthday(LocalDate.now());
				 * profile.setPin(system); profile.setPlaceOfBirth(system);
				 * profile.setCreatedDate(LocalDateTime.now()); profile.setCreateBy(system);
				 */

				User user = new User();
				/* user.setProfile(profile); */

				user.setEnabled(Boolean.TRUE);
				user.setUsername(system);
				user.setEmail(applicationProperties.getSuperUserEmail());
				user.setPasswordNeedsToBeChanged(true);
				user.setLastLogin(LocalDateTime.now());
				user.setFirstname("balakrishnan");
				user.setLastname("saranya");
				String password = UUID.randomUUID().toString();
				user.setPassword(passwordEncoder.encode(password));
				Optional<Role> role = roleRepository.findById(1l);
				user.setRole(role.get());
				Optional<Branch> branch = branchRepository.findById(1l);
				if (branch.isPresent()) {
					user.setBranch(branch.get());
				}
				User savedUser = userRepository.save(user);
				log.info("super user added with username {}", savedUser.getUsername());
				log.info("super user added with password {}", password);
				log.info("You must change this default password");
				/*
				 * List<Role> roles = roleRepository.findById(); user.setRole(roles);
				 */
				userRepository.save(user);
				log.info("all roles added to super user");
				log.info("*************************************************************");
			}
		};
	}

}
