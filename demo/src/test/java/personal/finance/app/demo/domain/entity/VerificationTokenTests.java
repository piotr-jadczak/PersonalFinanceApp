package personal.finance.app.demo.domain.entity;

import org.junit.jupiter.api.Test;
import personal.finance.app.demo.domain.entity.user.User;
import personal.finance.app.demo.domain.entity.user.VerificationToken;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class VerificationTokenTests {

    @Test
    void should_ExpiryDateBe3DaysAfterToday_When_VerificationTokenConstructor() {
        // given
        User testUser = User.builder().id(1L).username("testUser")
                .password("testPassword").email("test@test.com").build();
        LocalDate expectedDate = LocalDate.now().plusDays(3);
        // when
        VerificationToken actualToken = new VerificationToken("token", testUser);
        // then
        assertEquals(actualToken.getExpiryDate(), expectedDate);
    }
}
