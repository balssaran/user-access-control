package org.mounanga.userservice.dto;

import java.util.List;

public class UserRoleMenuResponseDTO {
    private Long userId;
    private RoleDTO role;
    private List<MenuDTO> menu;

    public UserRoleMenuResponseDTO() {}
    
    public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public RoleDTO getRole() {
		return role;
	}

	public void setRole(RoleDTO role) {
		this.role = role;
	}

	public List<MenuDTO> getMenu() {
		return menu;
	}

	public void setMenu(List<MenuDTO> menu) {
		this.menu = menu;
	}

	public UserRoleMenuResponseDTO(Long userId, RoleDTO role, List<MenuDTO> menu) {
        this.userId = userId;
        this.role = role;
        this.menu = menu;
    }

    // Getters and setters

    public static class RoleDTO {
        private String roleName;

        public RoleDTO() {}
        public RoleDTO(String roleName) {
            this.roleName = roleName;
        }

		public String getRoleName() {
			return roleName;
		}

		public void setRoleName(String roleName) {
			this.roleName = roleName;
		}

        // Getter and setter
    }

    public static class MenuDTO {
        private Long id;
        private String name;

        public MenuDTO() {}
        public MenuDTO(Long id, String name) {
            this.id = id;
            this.name = name;
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

        // Getters and setters
    }
}
