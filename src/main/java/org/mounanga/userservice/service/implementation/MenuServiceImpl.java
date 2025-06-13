package org.mounanga.userservice.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.mounanga.userservice.dto.MenuDTO;
import org.mounanga.userservice.dto.PageModel;
import org.mounanga.userservice.dto.RoleDTO;
import org.mounanga.userservice.entity.Menu;
import org.mounanga.userservice.entity.Role;
import org.mounanga.userservice.exception.NotAuthorizedException;
import org.mounanga.userservice.exception.ResourceAlreadyExistException;
import org.mounanga.userservice.exception.RoleNotFoundException;
import org.mounanga.userservice.repository.MenuRepository;
import org.mounanga.userservice.repository.RoleRepository;
import org.mounanga.userservice.service.MenuService;
import org.mounanga.userservice.service.RoleService;
import org.mounanga.userservice.util.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class MenuServiceImpl implements MenuService {

	private static final Logger log = LoggerFactory.getLogger(MenuServiceImpl.class);
	
	
    private final MenuRepository menuRepository;

    public MenuServiceImpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Transactional
    @Override
    public MenuDTO createMenu(MenuDTO dto) {
        log.info("In createRole()");
        Menu menu = Mappers.fromMenuDTO(dto);
        if(menuRepository.existsByName(menu.getName())) {
            throw new ResourceAlreadyExistException("Role with name " + menu.getName() + " already exists");
        }
        Menu savedMenu = menuRepository.save(menu);
        log.info("Role with name '{}' created at '{}' by '{}'", menu.getName());
        return Mappers.fromMenu(savedMenu);
    }

    @Transactional
    @Override
    public MenuDTO updateMenu(Long id, @NotNull MenuDTO dto) {
        log.info("In updateRole()");
        Menu menu = findMenuById(id);
        if(!menu.getName().equals(dto.menuName()) && menuRepository.existsByName(dto.menuName())) {
                throw new ResourceAlreadyExistException("Role with name " + dto.menuName() + " already exists");
        }
        menu.setName(dto.menuName());
        menu.setBackendPath(dto.urlPath());
        Menu updatedMenu = menuRepository.save(menu);
        log.info("Role with name '{}' updated at '{}' by '{}'", updatedMenu.getName());
        return Mappers.fromMenu(updatedMenu);
    }

    private Menu findMenuById(Long id) {
        return menuRepository.findById(id)
                .orElseThrow( () -> new RoleNotFoundException("Role with id " + id + " not found"));
    }
    
}
