package personal.finance.app.demo.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import personal.finance.app.demo.service.imp.RegistrationServiceImp;

@SpringBootTest
public class RegistrationRestControllerTests {

    private MockMvc mockMvc;

    @Autowired
    RegistrationServiceImp loginService;

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

}
