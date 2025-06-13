package org.mounanga.userservice.repository;

import org.mounanga.userservice.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String email);

	Optional<User> findByUsernameOrEmail(String username, String email);

	boolean existsByEmail(String email);

	@Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END "
			+ "FROM User u WHERE u.id != :userId AND u.email =:email")
	boolean existsByEmail(String email, Long userId);

	@Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END "
			+ "FROM User u WHERE u.id != :userId AND u.username =:username")
	boolean existsByUsername(String username, Long userId);

	boolean existsByUsername(String username);

	boolean existsBy();

	@Query("select u from User u where u.username like :kw ")
	Page<User> search(@Param("kw") String keyword, Pageable pageable);

}
