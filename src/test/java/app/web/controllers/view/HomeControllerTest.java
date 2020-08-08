package app.web.controllers.view;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getIndexShouldReturnStatusOk() throws Exception {
        this.mockMvc.perform(get("/"))

                .andExpect(status().isOk());
    }

    @Test
    public void getAboutUsShouldReturnStatusOk() throws Exception {
        this.mockMvc.perform(get("/about"))
                .andExpect(status().isOk());
    }

    @Test
    public void getHomeShouldReturnStatusOk() throws Exception {
        this.mockMvc.perform(get("/home").with(csrf()))
                .andExpect(status().isFound());

    }

}
