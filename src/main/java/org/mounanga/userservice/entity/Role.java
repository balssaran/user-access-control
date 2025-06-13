package org.mounanga.userservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Data
@Builder
@ToString
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	

	@Column(nullable = false, unique = true)
	private String name;

	@Column(nullable = false)
	private String description;

	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
	private List<RoleMenu> roleMenus;

	@Column(name="created_date")
	private LocalDateTime createdDate;

	@Column(name="last_modified_date")
	private LocalDateTime lastModifiedDate;

	@Column(name="create_by")
	private String createBy;

	@Column(name="last_modified_by")
	private String lastModifiedBy;
	
	
	@Column(nullable = false)
	private Boolean enabled;

	public boolean isSuperAdminRole() {
		return this.name.equals("SUPER_ADMIN");
	}

	public boolean isDefaultRole() {
		return switch (this.name) {
		case "USER", "ADMIN", "MODERATOR", "SUPER_ADMIN" -> true;
		default -> false;
		};
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<RoleMenu> getRoleMenus() {
		return roleMenus;
	}

	public void setRoleMenus(List<RoleMenu> roleMenus) {
		this.roleMenus = roleMenus;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	

}
