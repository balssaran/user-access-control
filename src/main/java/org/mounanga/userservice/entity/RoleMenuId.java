package org.mounanga.userservice.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RoleMenuId implements Serializable {

    private Long roleId;
    private Long menuId;

    // equals() and hashCode() are required
    
    

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoleMenuId)) return false;
        RoleMenuId that = (RoleMenuId) o;
        return Objects.equals(roleId, that.roleId) &&
               Objects.equals(menuId, that.menuId);
    }

    public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	@Override
    public int hashCode() {
        return Objects.hash(roleId, menuId);
    }

    // Getters and Setters
}