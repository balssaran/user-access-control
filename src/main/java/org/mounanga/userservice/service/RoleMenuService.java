package org.mounanga.userservice.service;

import org.mounanga.userservice.entity.RoleMenu;

public interface RoleMenuService {
  
	RoleMenu createMenu(RoleMenu dto);
	RoleMenu updateMenu(Long id, RoleMenu dto);
    
	
}
