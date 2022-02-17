package personal.finance.app.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import personal.finance.app.demo.domain.validator.UniqueEmail;
import personal.finance.app.demo.domain.validator.UniqueUsername;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @Size(min = 4, max = 16, message = "username length must be between 4-16")
    @UniqueUsername
    private String username;

    @Size(min = 8, max = 32, message = "password length must be between 4-32")
    private String password;

    @Email
    @Size(max = 64, message = "email length must be less or equal 64")
    @UniqueEmail
    private String email;
}
