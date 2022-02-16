package personal.finance.app.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import personal.finance.app.demo.domain.entity.Role;
import personal.finance.app.demo.domain.entity.User;
import personal.finance.app.demo.repository.RoleRepository;
import personal.finance.app.demo.repository.UserRepository;
import personal.finance.app.demo.service.exception.RoleNotFoundException;
import personal.finance.app.demo.service.imp.LoginServiceImp;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class LoginServiceTests {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;

    private LoginServiceImp loginService;

    @BeforeEach
    void setup() {
        this.loginService = new LoginServiceImp(userRepository,
                passwordEncoder, roleRepository);
    }

    @Nested
    class RegisterUserTests {

        @Test
        void should_ReturnRegisteredUser_When_RegisterUser() {
            // given
            when(LoginServiceTests.this.roleRepository.findByName(anyString()))
                    .thenReturn(Optional.of(new Role(1L, "user")));
            User userToRegister = User.builder().username("testUsername")
                    .password("testPassword").email("test@gmail.com").build();
            // when
            User registeredUser = loginService.registerUser(userToRegister);
            // then
            verify(roleRepository, times(1)).findByName(anyString());
            assertAll(
                    () -> assertEquals("user", registeredUser.getUsername()),
                    () -> assertTrue(passwordEncoder
                            .matches("testPassword", registeredUser.getPassword())),
                    () -> assertEquals("test@gmail.com", registeredUser.getEmail()),
                    () -> assertNotEquals(0L, registeredUser.getId()),
                    () -> assertTrue(registeredUser.isAccountNonLocked()),
                    () -> assertFalse(registeredUser.isEnabled())
            );
        }

        @Test
        void should_ThrowException_When_RoleNotFound() {
            // given
            when(LoginServiceTests.this.roleRepository.findByName(anyString()))
                    .thenReturn(Optional.empty());
            User userToRegister = User.builder().username("testUsername")
                    .password("testPassword").email("test@gmail.com").build();
            // when
            Executable executable = () -> loginService.registerUser(userToRegister);
            // then
            assertThrows(RoleNotFoundException.class, executable);
        }
    }

}
