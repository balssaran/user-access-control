package org.mounanga.userservice.dto;

import jakarta.validation.constraints.NotBlank;

public record MenuDTO(Long id,
                      @NotBlank(message = "field 'menuName' is mandatory: it cannot be blank")
                      String menuName,

                      @NotBlank(message = "field 'urlPath' is mandatory: it cannot be blank")
                      String urlPath) {
}
