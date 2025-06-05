package org.mounanga.userservice.dto;

import jakarta.validation.constraints.NotBlank;

public record BranchDTO(Long id,
        @NotBlank(message = "field 'name' is mandatory: it cannot be blank")
        String name,

        @NotBlank(message = "field 'branchcode' is mandatory: it cannot be blank")
        String branchcode) {
}