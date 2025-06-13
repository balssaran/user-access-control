package org.mounanga.userservice.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Data
@Builder
@ToString
public class Menu {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="menuName",nullable = false, unique = true)
	private String name;

	@ManyToOne
	@JoinColumn(name = "parent_menu_id") // Creates a foreign key column in the table
	private Menu parentMenuid;

	@Column(name="frontend_path")
	private String frontendPath;
	
	@Column(name="backend_path")
	private String backendPath;

	private int displayOrder;

	@Column(nullable = false)
	private Boolean enabled;
	
	@OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
	private List<RoleMenu> roleMenus;
	
	@Column(name="created_date")
	private LocalDateTime createdDate;

	@Column(name="last_modified_date")
	private LocalDateTime lastModifiedDate;

	@Column(name="create_by")
	private String createBy;

	@Column(name="last_modified_by")
	private String lastModifiedBy;

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

	public Menu getParentMenuid() {
		return parentMenuid;
	}

	public void setParentMenuid(Menu parentMenuid) {
		this.parentMenuid = parentMenuid;
	}

	public String getFrontendPath() {
		return frontendPath;
	}

	public void setFrontendPath(String frontendPath) {
		this.frontendPath = frontendPath;
	}

	public String getBackendPath() {
		return backendPath;
	}

	public void setBackendPath(String backendPath) {
		this.backendPath = backendPath;
	}

	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
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


	
	

}
