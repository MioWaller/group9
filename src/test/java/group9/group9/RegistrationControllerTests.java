package group9.group9;

import static org.assertj.core.api.Assertions.assertThat;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(RegistrationController.class)
public class RegistrationControllerTests {
    
    @Autowired
    private RegistrationController controller;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    public void registrationShouldReturnCorrectTemplate() throws Exception {
        mockMvc.perform(get("/registration"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Register")));
    }

    @Test
    public void registrationSubmitShouldOpenRegistrationIfRegistrationFailed() throws Exception {
        mockMvc.perform(post("/registration")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("username", "")
            .param("password", ""))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/registration"));

        mockMvc.perform(post("/registration")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("username", "group9")
            .param("password", ""))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/registration"));
    }

    @Test
    public void registrationSubmitShouldOpenProfileIfRegistrationSucceeded() throws Exception {
        when(userRepository.save(any()))
            .thenAnswer(i -> {
                UserEntity e = i.getArgument(0);
                e.setUserId(1);
                return e;
            });

        mockMvc.perform(post("/registration")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("username", "group9")
            .param("password", "group9password"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/profile"));
    }
}
