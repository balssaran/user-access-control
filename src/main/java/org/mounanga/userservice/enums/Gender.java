package org.mounanga.userservice.enums;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Gender {
    M("Male"),
    F("Female"),
    O("Other");

    private final String value;

    Gender(String value) {
        this.value = value;
    }

    public String getKey() {
        return name(); // Returns M, F, or O
    }

    public String getValue() {
        return value;  // Returns Male, Female, or Other
    }
    
    public static List<GenderDTO> getAllGenders() {
        return Stream.of(Gender.values())
            .map(g -> new GenderDTO(g.getKey(), g.getValue()))
            .collect(Collectors.toList());
    }

    public record GenderDTO(String key, String value) {}
}
