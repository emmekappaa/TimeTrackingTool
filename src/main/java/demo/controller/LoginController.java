package demo.controller;

import demo.model.Researcher;
import demo.repository.Repository;
import demo.model.Person;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@Controller
public class LoginController {

    @Autowired
    private Repository repository;
    @RequestMapping("/")
    public String loginPage(){
        return "login";
    }

    @RequestMapping(value = "/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session, Model model) {

        Person person = repository.findByUsername(username);

        if (person == null || !person.getPassword().equals(calculateMD5(password))) {
            model.addAttribute("errorMessage", "Invalid username or password.");
            return "login";
        }

        session.setAttribute("loggedInUser", person);

        if (person instanceof Researcher) {
            session.setAttribute("role", "Researcher");
            return "redirect:/homeResearcher";
        } else {
            session.setAttribute("role", "Manager");
            return "redirect:/homeManager";
        }

    }

    // Metodo per calcolare l'hash MD5
    private String calculateMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            // Converti il byte array in formato esadecimale
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}