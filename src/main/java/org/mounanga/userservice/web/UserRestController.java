package org.mounanga.userservice.web;

import jakarta.validation.Valid;
import org.mounanga.userservice.dto.*;
import org.mounanga.userservice.security.SecurityInformation;
import org.mounanga.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserRestController {

    private final UserService userService;
    private final SecurityInformation securityInformation;

    public UserRestController(UserService userService, SecurityInformation securityInformation) {
        this.userService = userService;
        this.securityInformation = securityInformation;
    }

   
    @PostMapping("/create")
    public UserResponseDTO createUser(@RequestBody UserRequestDTO dto) {
        return userService.createUser(dto);
    }

   
    @PutMapping("/update/{id}")
    public UserResponseDTO updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDTO dto) {
        return userService.updateUser(id, dto);
    }

    
    @PutMapping("/update-profile/{id}")
    public ProfileResponseDTO updateProfile(@PathVariable Long id, @Valid @RequestBody UserRequestDTO dto) {
        return userService.updateProfile(id, dto);
    }

    
    @GetMapping("/get/{id}")
    public UserResponseDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

   
    @GetMapping("/list")
    public PageModel<UserResponseDTO> getAllUsers(@RequestParam(defaultValue = "0", name = "page")  int page,
                                                  @RequestParam(defaultValue = "9", name = "size")  int size) {
        return userService.getAllUsers(page, size);
    }

    
    @GetMapping("/search")
    public PageModel<UserResponseDTO> searchUsers(@RequestParam(defaultValue = "", name = "keyword") String keyword,
                                                  @RequestParam(defaultValue = "0", name = "page")  int page,
                                                  @RequestParam(defaultValue = "9", name = "size")  int size) {
        return userService.searchUsers(keyword, page, size);
    }

    
    @DeleteMapping("/delete/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
    }

    
    @DeleteMapping("/delete-all")
    public void deleteAllUsersByIds(@RequestBody List<Long> ids) {
        userService.deleteAllUsersByIds(ids);
    }

    
    @PostMapping("/add-role")
    public UserResponseDTO addRoleToUser(@RequestBody @Valid UserRoleRequestDTO dto) {
        return userService.addRoleToUser(dto);
    }

    
    @PostMapping("/remove-role")
    public UserResponseDTO removeRoleFromUser(@RequestBody @Valid UserRoleRequestDTO dto) {
        return userService.removeRoleFromUser(dto);
    }

   
    @GetMapping("/profile")
    public UserResponseDTO getCurrentUser() {
        return userService.getUserByUsername(
                securityInformation.getCurrentUsername()
        );
    }
    
    
    @GetMapping("/{userId}/menu-info")
    public UserRoleMenuResponseDTO getUserRoleMenus(@PathVariable Long userId) {
    	UserRoleMenuResponseDTO userRoleMenuResponseDTO=userService.getUserRoleMenu(userId);
        return userRoleMenuResponseDTO;
    }
}
