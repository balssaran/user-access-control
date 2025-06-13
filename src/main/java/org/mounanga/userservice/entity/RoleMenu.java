package org.mounanga.userservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "role_menu")
public class RoleMenu {

	@EmbeddedId
	private RoleMenuId id = new RoleMenuId();
	
	

	@ManyToOne
	@MapsId("roleId")
	@JoinColumn(name = "role_id")
	@JsonIgnore
	private Role role;

	@ManyToOne
	@MapsId("menuId")
	@JoinColumn(name = "menu_id")
	@JsonIgnore
	private Menu menu;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_date")
	private LocalDateTime createdDate;

	public RoleMenuId getId() {
		return id;
	}

	public void setId(RoleMenuId id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	// Getters and Setters
	
	
}