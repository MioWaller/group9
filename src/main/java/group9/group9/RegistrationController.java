package group9.group9;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
	public String registration(Model model) {
        RegistrationModel registrationModel = new RegistrationModel();
        model.addAttribute("registrationModel", registrationModel);
        return "registration";
	}
    
    @PostMapping("/registration")
    public String registrationSubmit(@ModelAttribute RegistrationModel registrationModel, HttpServletResponse response) {
        if (registrationModel.getUsername().equals("") || registrationModel.getPassword().equals("")) {
            return "redirect:/registration";
        }

        List<UserEntity> clients = userRepository.findByUsername(registrationModel.getUsername());

        if (!clients.isEmpty()) {
            return "redirect:/registration";
        }

        UserEntity n = new UserEntity();
        n.setUsername(registrationModel.getUsername());

        String hash = PasswordEncryption.hash(registrationModel.getPassword());

        n.setPassword(hash);
        n = userRepository.save(n);

        Cookie cookie = new Cookie("user-id", n.getId().toString());
        response.addCookie(cookie);

        return "redirect:/profile";
    }
}