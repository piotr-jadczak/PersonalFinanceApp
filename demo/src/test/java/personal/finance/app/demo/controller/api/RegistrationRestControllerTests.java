package personal.finance.app.demo.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import personal.finance.app.demo.domain.dto.UserDto;
import personal.finance.app.demo.service.imp.LoginServiceImp;

@SpringBootTest
public class RegistrationRestControllerTests {

    private MockMvc mockMvc;

    @Autowired
    LoginServiceImp loginService;

    private static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup
                (new RegistrationRestController(loginService)).build();
    }

    @Nested
    class RegisterUserTests {

        // unable to mock Validators to complete the test
        @Test
        void should_ReturnUser_When_CorrectUserDtoPost() {
            // given
            String expUsername = "testUser";
            String expPassword = "testPassword";
            String expEmail = "testEmail";
            UserDto testUserDto = new UserDto(expUsername,
                    expPassword, expEmail);
            // when

            // then
        }
    }
}
