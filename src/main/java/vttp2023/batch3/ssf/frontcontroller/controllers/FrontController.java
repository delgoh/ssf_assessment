package vttp2023.batch3.ssf.frontcontroller.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vttp2023.batch3.ssf.frontcontroller.models.Login;
import vttp2023.batch3.ssf.frontcontroller.services.AuthenticationService;

@Controller
public class FrontController {

	// TODO: Task 2, Task 3, Task 4, Task 6
	@Autowired
	private AuthenticationService authenticationService;
	
    @PostMapping(
        path = "/login",
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
        produces = MediaType.TEXT_HTML_VALUE)
    public String login(@Valid Login login, BindingResult result, Model model, HttpSession session, RedirectAttributes ra) {
        System.out.println("Username keyed in: " + login.getUsername());

        if (login.isCaptchaShown()) {
            System.out.println("Captcha is visible");
            if (!login.isCaptchaCorrect()) {
                System.out.println("Captcha is wrong");
                FieldError err = new FieldError("login", "captcha", "Captcha is incorrect");
                // result.rejectValue("captcha", "", "Captcha is incorrect");
                result.addError(err);
                login.generateNewCaptcha();
                model.addAttribute("login", login);
                return "view0";
            }
        }
        
        System.out.println("Captcha not shown yet");

        if (result.hasErrors()) {
            model.addAttribute("login", login);
            return "view0";
        }

        if (authenticationService.isLocked(login.getUsername())) {
            model.addAttribute("username", login.getUsername());
            return "view2";
        }

        authenticationService.increaseLoginAttempt(login.getUsername());

		try {
			authenticationService.authenticate(login.getUsername(), login.getPassword());
		} catch (Exception e) {
            
            if (authenticationService.isAttemptsExceeded(login.getUsername())) {
                authenticationService.disableUser(login.getUsername());
                authenticationService.resetLoginAttempt(login.getUsername());
                model.addAttribute("username", login.getUsername());
                return "view2";
            }

            login.setCaptchaShown(true);
            model.addAttribute("login", login);
			return "view0";
		}
        
        authenticationService.resetLoginAttempt(login.getUsername());

        ra.addFlashAttribute("sessionId", session.getId());
        return "redirect:/protected/view1.html";
    }

	@GetMapping(path = "/logout")
	public String logout(HttpSession session) {
		authenticationService.removeAuthenticatedSession(session.getId());
		return "redirect:/";
	}

}
