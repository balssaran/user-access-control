package org.mounanga.userservice.entity;

import jakarta.persistence.*;
import lombok.*;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean enabled;

    @Column(nullable = false)
    private LocalDateTime lastLogin;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @CreatedBy
    private String createBy;

    @LastModifiedBy
    private String lastModifiedBy;

    @Column(nullable = false)
    private boolean passwordNeedsToBeChanged = false;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private Profile profile;

    @ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="user_role",joinColumns = @JoinColumn(name="user_id") , inverseJoinColumns = @JoinColumn(name="role_id"))
    private List<Role> roles;

    public boolean isEnabled(){
        return Boolean.TRUE.equals(this.enabled);
    }

    public boolean isDisabled(){
        return Boolean.FALSE.equals(this.enabled);
    }

    public void addRole(Role role){
        if(this.roles == null){
            this.roles = new ArrayList<>();
        }
        if(role != null && !roles.contains(role)){
            this.roles.add(role);
        }
    }

    public void removeRole(Role role){
        if(this.roles != null && role != null && role.getName().equals("USER")){
                this.roles.remove(role);
        }
    }

    public boolean isSuperAdmin(){
        return roles.stream()
                .anyMatch(role -> "SUPER_ADMIN".equals(role.getName()));
    }

    public void markPasswordAsChanged() {
        this.passwordNeedsToBeChanged = false;
    }

	public boolean isPasswordNeedsToBeChanged() {
		// TODO Auto-generated method stub
		return passwordNeedsToBeChanged;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public Profile getProfile() {
		
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public void setPasswordNeedsToBeChanged(boolean passwordNeedsToBeChanged) {
		this.passwordNeedsToBeChanged = passwordNeedsToBeChanged;
	}


}
