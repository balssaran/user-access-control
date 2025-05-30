package org.mounanga.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.mounanga.userservice.enums.Gender;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UserRequestDTO {
    @NotBlank(message = "field 'pin' is mandatory: it cannot be blank")
    private String pin;

    @NotBlank(message = "field 'email' is mandatory: it cannot be blank")
    @Email(message = "field 'email' is not well formated")
    private String email;

    @NotBlank(message = "field 'firstname' is mandatory: it cannot be blank")
    private String firstname;

    @NotBlank(message = "field 'lastname' is mandatory: it cannot be blank")
    private String lastname;

    @NotNull(message = "field 'birthday' is mandatory: it cannot be null")
    private LocalDate birthday;

    @NotBlank(message = "field 'place of birth' is mandatory: it cannot be blank")
    private String placeOfBirth;

    @NotNull(message = "field 'gender' is mandatory: it cannot be null")
    private Gender gender;

    @NotBlank(message = "field 'nationality' is mandatory: it cannot be blank")
    private String nationality;

    @NotBlank(message = "field 'username' is mandatory: it cannot be blank")
    private String username;

    @NotBlank(message = "field 'password' is mandatory: it cannot be blank")
    private String password;

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
    
    
}
