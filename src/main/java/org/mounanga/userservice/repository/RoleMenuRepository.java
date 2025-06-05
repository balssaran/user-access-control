package org.mounanga.userservice.repository;

import java.util.Optional;

import org.mounanga.userservice.entity.RoleMenu;
import org.mounanga.userservice.entity.RoleMenuId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleMenuRepository extends JpaRepository<RoleMenu,RoleMenuId> {
    //Optional<RoleMenu> findByName(String name);
    //boolean existsByName(String name);

    @Query("select m from RoleMenu m where m.role.id = :roleId and m.menu.id = :menuId")
    Optional<RoleMenu> findByRoleandMenu(@Param("roleId") Long roleId,@Param("menuId") Long menuId);
    
	/*
	 * @Query("select m from RoleMenu m where m.Role.id = :roleId and m.Menu.id = :menuId"
	 * ) Optional<RoleMenu> findByRoleandMenu(@Param("roleid") Long
	 * roleId,@Param("menuId") Long menuId);
	 */

    boolean existsBy();
}
