package group9.group9;

import static org.assertj.core.api.Assertions.assertThat;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(LoginController.class)
public class LoginControllerTests {
    
    @Autowired
    private LoginController controller;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    public void loginShouldReturnCorrectTemplate() throws Exception {
        mockMvc.perform(get("/login"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Continue as a Guest")));
    }

    @Test
    public void loginSubmitShouldOpenLoginIfLoginFailed() throws Exception {
        mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("username", "notuser")
            .param("password", "notpass"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/login"));

        mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("username", "group9")
            .param("password", "wrongpassword"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/login"));
    }

    @Test
    public void loginSubmitShouldOpenLoginIfLoginSucceeded() throws Exception {
        UserEntity loginUser = new UserEntity();
        loginUser.setUserId(1);
        loginUser.setUsername("group9");
        loginUser.setPassword(PasswordEncryption.hash("group9password"));
        List<UserEntity> clients = new ArrayList<>();
        clients.add(loginUser);

        when(userRepository.findByUsername(anyString()))
            .thenReturn(clients);

        mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("username", "group9")
            .param("password", "group9password"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/reservationhistory"));
    }
}

