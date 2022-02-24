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
public class UniqueEmailValidatorTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UniqueEmailValidator validator;

    @ParameterizedTest
    @CsvSource(value = {"test@test.com", "test2@test.com", "test2@gmail.com"})
    void should_isValidReturnTrue_When_UserWithEmailNotInRepository(String email) {
        //given
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        String testEmail = email;
        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        //when
        boolean expected = validator.isValid(testEmail, context);
        //then
        assertTrue(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"test@test.com", "test2@test.com", "test2@gmail.com"})
    void should_isValidReturnFalse_When_UserWithUsernameInRepository(String email) {
        //given
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User()));
        String testEmail = email;
        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        //when
        boolean expected = validator.isValid(testEmail, context);
        //then
        assertFalse(expected);
    }

    @Test
    void should_isValidReturnFalse_When_EmailIsEmpty() {
        //given
        lenient().when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        String testEmail = "";
        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        //when
        boolean expected = validator.isValid(testEmail, context);
        //then
        assertFalse(expected);
    }

    @Test
    void should_isValidReturnFalse_When_EmailIsNull() {
        //given
        lenient().when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        String testEmail = null;
        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
        //when
        boolean expected = validator.isValid(testEmail, context);
        //then
        assertFalse(expected);
    }


}
