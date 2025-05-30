package org.mounanga.userservice;

import lombok.extern.slf4j.Slf4j;
import org.mounanga.userservice.configuration.ApplicationProperties;
import org.mounanga.userservice.entity.Profile;
import org.mounanga.userservice.entity.Role;
import org.mounanga.userservice.entity.User;
import org.mounanga.userservice.enums.Gender;
import org.mounanga.userservice.repository.RoleRepository;
import org.mounanga.userservice.repository.UserRepository;
import org.mounanga.userservice.service.implementation.AuthenticationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@EnableJpaAuditing
@EnableAsync
@SpringBootApplication
public class UserServiceApplication {

	private static final Logger log = LoggerFactory.getLogger(UserServiceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(RoleRepository roleRepository, UserRepository userRepository,
			PasswordEncoder passwordEncoder, ApplicationProperties applicationProperties) {
		return args -> {
			if (!roleRepository.existsBy()) {
				Role role = new Role();
				role.setName("USER");
				role.setDescription("the default role that all users should have");
				log.info("*************************************************************");
				roleRepository.save(role);
				log.info("USER role added");
				role = new Role();
				role.setName("ADMIN");
				role.setDescription("the default role that all administrators should have");
				roleRepository.save(role);
				log.info("ADMIN role added");
				role = new Role();
				role.setName("SUPER_ADMIN");
				role.setDescription("the default role that all super administrators should have");
				roleRepository.save(role);

				log.info("SUPER admin role added");
			}
			if (!userRepository.existsBy()) {
				log.info("************************************************************");
				String system = "SYSTEM";
				Profile profile = new Profile();
				profile.setGender(Gender.M);
				profile.setFirstname(system);
				profile.setLastname(system);
				profile.setNationality(system);
				profile.setBirthday(LocalDate.now());
				profile.setPin(system);
				profile.setPlaceOfBirth(system);
				profile.setCreatedDate(LocalDateTime.now());
				profile.setCreateBy(system);

				User user = new User();
				user.setProfile(profile);
				user.setEnabled(Boolean.TRUE);
				user.setUsername(system);
				user.setEmail(applicationProperties.getSuperUserEmail());
				user.setPasswordNeedsToBeChanged(true);
				user.setLastLogin(LocalDateTime.now());
				String password = UUID.randomUUID().toString();
				user.setPassword(passwordEncoder.encode(password));
				User savedUser = userRepository.save(user);
				log.info("super user added with username {}", savedUser.getUsername());
				log.info("super user added with password {}", password);
				log.info("You must change this default password");
				List<Role> roles = roleRepository.findAll();
				user.setRoles(roles);
				userRepository.save(user);
				log.info("all roles added to super user");
				log.info("*************************************************************");
			}
		};
	}

}
