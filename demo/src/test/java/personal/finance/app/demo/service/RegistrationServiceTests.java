package personal.finance.app.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import personal.finance.app.demo.domain.dto.UserDto;
import personal.finance.app.demo.domain.entity.user.Role;
import personal.finance.app.demo.domain.entity.user.User;
import personal.finance.app.demo.repository.user.RoleRepository;
import personal.finance.app.demo.repository.user.UserRepository;
import personal.finance.app.demo.repository.user.VerificationTokenRepository;
import personal.finance.app.demo.service.exception.RoleNotFoundException;
import personal.finance.app.demo.service.imp.EmailServiceImp;
import personal.finance.app.demo.service.imp.RegistrationServiceImp;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RegistrationServiceTests {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private VerificationTokenRepository verificationTokenRepository;
    @Mock
    private EmailServiceImp emailService;

    private RegistrationServiceImp registrationService;

    @BeforeEach
    void setup() {
        this.registrationService = new RegistrationServiceImp(userRepository,
                passwordEncoder, roleRepository,
                verificationTokenRepository,
                emailService);
    }

    @Nested
    class AddUserTests {

        @Test
        void should_ReturnAddedUser_When_AddUser() {
            // given
            when(RegistrationServiceTests.this.roleRepository.findByName(anyString()))
                    .thenReturn(Optional.of(new Role(1L, "user")));
            User userToRegister = User.builder().username("testUsername")
                    .password("testPassword").email("test@gmail.com").build();
            // when
            User registeredUser = registrationService.addUser(userToRegister);
            // then
            verify(roleRepository, times(1)).findByName(anyString());
            assertAll(
                    () -> assertEquals("testUsername", registeredUser.getUsername()),
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
            when(RegistrationServiceTests.this.roleRepository.findByName(anyString()))
                    .thenReturn(Optional.empty());
            User userToRegister = User.builder().username("testUsername")
                    .password("testPassword").email("test@gmail.com").build();
            // when
            Executable executable = () -> registrationService.addUser(userToRegister);
            // then
            assertThrows(RoleNotFoundException.class, executable);
        }
    }

    @Nested
    class MapUserTests {

        @Test
        void should_ReturnUser_When_UserDtoCorrect() {
            // given
            String expUsername = "testUser";
            String expPassword = "testPassword";
            String expEmail = "testEmail@test.com";
            UserDto testUserDto = new UserDto(expUsername,
                    expPassword, expEmail);
            // when
            User mappedUser = registrationService.mapUser(testUserDto);
            // then
            assertAll(
                    () -> assertEquals(expUsername, mappedUser.getUsername()),
                    () -> assertEquals(expPassword, mappedUser.getPassword()),
                    () -> assertEquals(expEmail, mappedUser.getEmail())
            );
        }
    }

}
