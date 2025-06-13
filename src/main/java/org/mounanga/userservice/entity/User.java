package org.mounanga.userservice.entity;

import jakarta.persistence.*;
import lombok.*;

import org.mounanga.userservice.enums.Gender;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String email;

	@Column(unique = true, nullable = false)
	private String username;

	@Column( nullable = false)
	private String firstname;

	@Column(nullable = false)
	private String lastname;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private Boolean enabled;

	@Column(name="last_login",nullable = false)
	private LocalDateTime lastLogin;

	@Column(name="created_date")
	private LocalDateTime createdDate;

	@Column(name="last_modified_date")
	private LocalDateTime lastModifiedDate;

	
	@Column(name = "create_by")
	private Long createBy;

	@Column(name="last_modified_by")
	private Long lastModifiedBy;

	@Column(nullable = false)
	private boolean passwordNeedsToBeChanged = false;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id") // this creates a role_id column in the User table
	private Role role;

	@ManyToOne
	@JoinColumn(name = "branch_id") // this creates a role_id column in the User table
	private Branch branch;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;

	public boolean isEnabled() {
		return Boolean.TRUE.equals(this.enabled);
	}

	public boolean isDisabled() {
		return Boolean.FALSE.equals(this.enabled);
	}

	/*
	 * public void addRole(Role role){ if(this.roles == null){ this.roles = new
	 * ArrayList<>(); } if(role != null && !roles.contains(role)){
	 * this.roles.add(role); } }
	 * 
	 * public void removeRole(Role role){ if(this.roles != null && role != null &&
	 * role.getName().equals("USER")){ this.roles.remove(role); } }
	 * 
	 * public boolean isSuperAdmin(){ return roles.stream() .anyMatch(role ->
	 * "SUPER_ADMIN".equals(role.getName())); }
	 */

	public void markPasswordAsChanged() {
		this.passwordNeedsToBeChanged = false;
	}

	public boolean isPasswordNeedsToBeChanged() {
		// TODO Auto-generated method stub
		return passwordNeedsToBeChanged;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public LocalDateTime getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(LocalDateTime lastLogin) {
		this.lastLogin = lastLogin;
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

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public Long getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(Long lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	/*
	 * public Profile getProfile() {
	 * 
	 * return profile; }
	 * 
	 * public void setProfile(Profile profile) { this.profile = profile; }
	 */

	public void setPasswordNeedsToBeChanged(boolean passwordNeedsToBeChanged) {
		this.passwordNeedsToBeChanged = passwordNeedsToBeChanged;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	
}
