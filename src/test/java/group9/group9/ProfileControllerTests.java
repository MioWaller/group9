package group9.group9;

import static org.assertj.core.api.Assertions.assertThat;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProfileController.class)
public class ProfileControllerTests {
    
    @Autowired
    private ProfileController controller;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserInfoRepository userInfoRepository;

    @Test
    public void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    public void profileShouldReturnCorrectTemplate() throws Exception {
        Cookie cookie = new Cookie("user-id", "a");
        Cookie cookie1 = new Cookie("user-id", "1");

        mockMvc.perform(get("/profile")
            .cookie(cookie)
            .cookie(cookie1))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Profile")));
    }

    // Fail on zipcode being less than 5 
    @Test
    public void profileSubmitShouldFailOnShortZipcode() throws Exception {
        mockMvc.perform(post("/profile")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("zipcode", "00"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/profile"));
    }

    // Fail on zipcode being more than 9 digits
    @Test
    public void profileSubmitShouldFailOnLongZipcode() throws Exception {
        mockMvc.perform(post("/profile")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("zipcode", "00000000000000000000000000000000"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/profile"));
    }

    // Fail on zipcode that is not number
    @Test
    public void profileSubmitShouldFailOnNonNumberZipcode() throws Exception {
        mockMvc.perform(post("/profile")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("zipcode", "akjfhbskdja"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/profile"));
    }

    // Fail on not having address1 which is required
    @Test
    public void profileSubmitShouldFailOnEmptyAddress1() throws Exception {
        mockMvc.perform(post("/profile")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("zipcode", "000000")
            .param("address1", ""))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/profile"));
    }

    // Fail on the number of letters being more than 100
    @Test
    public void profileSubmitShouldFailOnTooLongAddress1() throws Exception {
        mockMvc.perform(post("/profile")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("zipcode", "000000")
            .param("address1", "mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/profile"));
    }

    // Fail on the city input being empty
    @Test
    public void profileSubmitShouldFailOnEmptyCity() throws Exception {
        mockMvc.perform(post("/profile")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("zipcode", "000000")
            .param("address1", "mmsksnfkiub")
            .param("city", ""))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/profile"));
    }

    // Fail on the number of letters in the city being more than 100
    @Test
    public void profileSubmitShouldFailOnTooLongCity() throws Exception {
        mockMvc.perform(post("/profile")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("zipcode", "000000")
            .param("address1", "akjfhie")
            .param("city", "mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/profile"));
    }

    // Fail on the state input being empty
    @Test
    public void profileSubmitShouldFailOnEmptyState() throws Exception {
        mockMvc.perform(post("/profile")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("zipcode", "000000")
            .param("address1", "akjfhie")
            .param("city", "mmmm")
            .param("state", ""))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/profile"));
    }

    // Fail on state being not 2 letters
    @Test
    public void profileSubmitShouldFailOnTooLongState() throws Exception {
        mockMvc.perform(post("/profile")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("zipcode", "000000")
            .param("address1", "akjfhie")
            .param("city", "mmmm")
            .param("state", "ytafvsd"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/profile"));
    }

    // Fail on fullname being empty
    @Test
    public void profileSubmitShouldFailOnEmptyName() throws Exception {
        mockMvc.perform(post("/profile")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("zipcode", "000000")
            .param("address1", "akjfhie")
            .param("city", "mmmm")
            .param("state", "ty")
            .param("fullName", ""))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/profile"));
    }

    // Fail on the number of letters in fullname being more than 50
    @Test
    public void profileSubmitShouldFailOnNonTooLongName() throws Exception {
        mockMvc.perform(post("/profile")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("zipcode", "000000")
            .param("address1", "akjfhie")
            .param("city", "mmmm")
            .param("state", "ty")
            .param("fullName", "group9mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/profile"));
    }

    // Fail on phone number being non-number
    @Test
    public void profileSubmitShouldFailOnNonNumberPhoneNumber() throws Exception {
        mockMvc.perform(post("/profile")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("zipcode", "000000")
            .param("address1", "akjfhie")
            .param("city", "mmmm")
            .param("state", "ty")
            .param("fullName", "group9")
            .param("phone", "kajdiadfho"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/profile"));
    }

    // Fail on phone number more than 10 digits
    @Test
    public void profileSubmitShouldFailOnTooLongPhoneNumber() throws Exception {
        mockMvc.perform(post("/profile")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("zipcode", "000000")
            .param("address1", "akjfhie")
            .param("city", "mmmm")
            .param("state", "ty")
            .param("fullName", "group9")
            .param("phone", "00234234235154123412512"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/profile"));
    }

    // Fail on phone number less than or equal to 9 digits
    @Test
    public void profileSubmitShouldFailonNOnTooShortPhoneNumber() throws Exception {
        mockMvc.perform(post("/profile")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("zipcode", "000000")
            .param("address1", "akjfhie")
            .param("city", "mmmm")
            .param("state", "ty")
            .param("fullName", "group9")
            .param("phone", "0023"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/profile"));
    }

    // Fail on email being empty
    @Test
    public void profileSubmitShouldFailOnEmptyEmail() throws Exception {
        mockMvc.perform(post("/profile")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("zipcode", "000000")
            .param("address1", "akjfhie")
            .param("city", "mmmm")
            .param("state", "ty")
            .param("fullName", "group9")
            .param("phone", "0000000000")
            .param("email", ""))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/profile"));
    }

    // Pass if everything is input right
    @Test
    public void profileSubmitShouldOpenReservationIfProfileSucceeded() throws Exception {
        Cookie cookie = new Cookie("user-id", "a");
        Cookie cookie1 = new Cookie("user-id", "1");
        UserInfoEntity info = new UserInfoEntity();
        info.setId(1);
        info.setUserId(2);
        info.setFullName("Group9");
        info.setAddress1("Address1");
        info.setAddress2("Address2");
        info.setCity("Houston");
        info.setState("TX");
        info.setZipcode("00000");
        info.setPhone("0000000000");
        info.setEmail("group9@uh.edu");
        List<UserInfoEntity> client = new ArrayList<>();
        client.add(info);

        when(userInfoRepository.findByUserid(anyInt())).thenReturn(client);
    
        mockMvc.perform(post("/profile")
            .cookie(cookie)
            .cookie(cookie1)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("zipcode", "00000")
            .param("address1", "Address1")
            .param("address2", "Address2")
            .param("city", "Houston")
            .param("fullName", "Group9")
            .param("state", "TX")
            .param("phone", "0000000000")
            .param("email", "group9@uh.edu"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/reservation"));
    }

    // Create new info if the test passes
    @Test
    public void profileSubmitShouldCreateNewInfo() throws Exception {
        Cookie cookie = new Cookie("user-id", "a");
        Cookie cookie1 = new Cookie("user-id", "1");
        List<UserInfoEntity> clients = new ArrayList<>();
        when(userInfoRepository.findByUserid(anyInt())).thenReturn(clients);
    
        mockMvc.perform(post("/profile")
            .cookie(cookie)
            .cookie(cookie1)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("zipcode", "00000")
            .param("address1", "Address1")
            .param("address2", "Address2")
            .param("city", "Houston")
            .param("fullName", "Group9")
            .param("state", "TX")
            .param("phone", "0000000000")
            .param("email", "group9@uh.edu"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/reservation"));
    }

    @Test
    public void profileSubmitShouldNotIncludeCookie() throws Exception {
        Cookie cookie = new Cookie("random", "random");
        UserInfoEntity info = new UserInfoEntity();
        info.setId(1);
        info.setUserId(2);
        info.setFullName("Group9");
        info.setAddress1("Address1");
        info.setAddress2("Address2");
        info.setCity("Houston");
        info.setState("TX");
        info.setZipcode("00000");
        info.setPhone("0000000000");
        info.setEmail("group9@uh.edu");


        List<UserInfoEntity> clients = new ArrayList<>();
        clients.add(info);
        when(userInfoRepository.findByUserid(anyInt())).thenReturn(clients);
    
        mockMvc.perform(post("/profile")
            .cookie(cookie)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("zipcode", "00000")
            .param("address1", "Address1")
            .param("address2", "Address2")
            .param("city", "Houston")
            .param("fullName", "Group9")
            .param("state", "TX")
            .param("phone", "0000000000")
            .param("email", "group9@uh.edu"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/login"));
    }
}
