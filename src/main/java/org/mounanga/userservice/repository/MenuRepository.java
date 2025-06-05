package org.mounanga.userservice.repository;

import org.mounanga.userservice.entity.Menu;
import org.mounanga.userservice.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    Optional<Menu> findByName(String name);
    boolean existsByName(String name);

    @Query("select m from Menu m where m.name like :kw")
    Page<Role> findByNameOrDescription(@Param("kw") String keyword, Pageable pageable);

    boolean existsBy();
}
