package org.mounanga.userservice.service;

import org.mounanga.userservice.dto.MenuDTO;
import org.mounanga.userservice.dto.PageModel;
import org.mounanga.userservice.dto.RoleDTO;
import org.mounanga.userservice.entity.Menu;
import org.mounanga.userservice.entity.Role;

import java.util.List;

public interface MenuService {
  
	MenuDTO createMenu(MenuDTO dto);
    MenuDTO updateMenu(Long id, MenuDTO dto);
   // Menu findMenuById(Long id);
	
}
