package org.mounanga.userservice.repository;

import java.util.Optional;

import org.mounanga.userservice.entity.Branch;
import org.mounanga.userservice.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    Optional<Branch> findByName(String name);
    boolean existsByName(String name);

	/*
	 * @Query("select r from branch r where r.name like :kw or r.branchcode like :kw"
	 * ) Page<Branch> findByNameOrDescription(@Param("kw") String keyword, Pageable
	 * pageable);
	 */

    boolean existsBy();
}