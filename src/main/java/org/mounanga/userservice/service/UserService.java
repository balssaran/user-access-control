package org.mounanga.userservice.service;

import org.mounanga.userservice.dto.*;

import java.util.List;

public interface UserService {
    UserResponseDTO createUser(UserRequestDTO dto);
    UserResponseDTO updateUser(Long id, UserRequestDTO dto);
    ProfileResponseDTO updateProfile(Long id, UserRequestDTO dto);
    UserResponseDTO getUserById(Long id);
    PageModel<UserResponseDTO> getAllUsers(int page, int size);
    PageModel<UserResponseDTO> searchUsers(String keyword, int page, int size);
    void deleteUserById(Long id);
    void deleteAllUsersByIds(List<Long> ids);
    UserResponseDTO addRoleToUser(UserRoleRequestDTO dto);
    UserResponseDTO removeRoleFromUser(UserRoleRequestDTO dto);
    UserResponseDTO getUserByUsername(String username);
    
    UserRoleMenuResponseDTO getUserRoleMenu(Long userId);
}
