package org.mounanga.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import org.mounanga.userservice.entity.Branch;
import org.mounanga.userservice.entity.Role;
import org.mounanga.userservice.enums.Gender;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UserRequestDTO {
	/*
	 * @NotBlank(message = "field 'pin' is mandatory: it cannot be blank") private
	 * String pin;
	 */

	private Long userId;

	@NotBlank(message = "field 'email' is mandatory: it cannot be blank")
	@Email(message = "field 'email' is not well formated")
	private String email;

	@NotBlank(message = "field 'firstname' is mandatory: it cannot be blank")
	private String firstname;

	@NotBlank(message = "field 'lastname' is mandatory: it cannot be blank")
	private String lastname;

	/*
	 * @NotNull(message = "field 'birthday' is mandatory: it cannot be null")
	 * private LocalDate birthday;
	 * 
	 * @NotBlank(message =
	 * "field 'place of birth' is mandatory: it cannot be blank") private String
	 * placeOfBirth;
	 */

	@NotNull(message = "field 'gender' is mandatory: it cannot be null")
	private Gender gender;

	/*
	 * @NotBlank(message = "field 'nationality' is mandatory: it cannot be blank")
	 * private String nationality;
	 */

	@NotBlank(message = "field 'username' is mandatory: it cannot be blank")
	private String username;

	
	private Long createdBy;

	/*
	 * private Long roleId;
	 * 
	 * private Long branchId;
	 */

	private Role role;

	private Branch branch;

	private boolean enabled;
	
	private Long lastModifiedBy;
	
	private LocalDateTime lastModifiedDate;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/*
	 * public Long getRoleId() { return roleId; }
	 * 
	 * public void setRoleId(Long roleId) { this.roleId = roleId; }
	 * 
	 * public Long getBranchId() { return branchId; }
	 * 
	 * public void setBranchId(Long branchId) { this.branchId = branchId; }
	 */

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

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Long getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(Long lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public LocalDateTime getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

}
