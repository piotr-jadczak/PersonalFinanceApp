package personal.finance.app.demo.domain.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import personal.finance.app.demo.domain.entity.user.User;
import personal.finance.app.demo.repository.user.UserRepository;

import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UniqueUsernameValidatorTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UniqueUsernameValidator validator;

    @ParameterizedTest
    @CsvSource(value = {"testUser", "secondUser", "thirdUser"})
    void should_isValidReturnTrue_When_UserWithUsernameNotInRepository(String username) {
        //given
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        String testUsername = username;
        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        //when
        boolean expected = validator.isValid(testUsername, context);
        //then
        assertTrue(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"testUser", "secondUser", "thirdUser"})
    void should_isValidReturnFalse_When_UserWithUsernameInRepository(String username) {
        //given
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(new User()));
        String testUsername = username;
        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        //when
        boolean expected = validator.isValid(testUsername, context);
        //then
        assertFalse(expected);
    }

    @Test
    void should_isValidReturnFalse_When_UsernameIsEmpty() {
        //given
        lenient().when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        String testUsername = "";
        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        //when
        boolean expected = validator.isValid(testUsername, context);
        //then
        assertFalse(expected);
    }

    @Test
    void should_isValidReturnFalse_When_UsernameIsNull() {
        //given
        lenient().when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        String testUsername = null;
        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        //when
        boolean expected = validator.isValid(testUsername, context);
        //then
        assertFalse(expected);
    }
}
