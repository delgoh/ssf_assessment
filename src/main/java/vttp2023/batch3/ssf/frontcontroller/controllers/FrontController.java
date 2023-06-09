package vttp2023.batch3.ssf.frontcontroller.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

        // if (captcha.isShown()) {
        //     System.out.println("Captcha received");
        //     System.out.println(captcha.getAnswer());
        // } else {
        //     System.out.println("No captcha here");
        // }
        // if (!login.isCaptchaShown()) {
        //     System.out.println("No captcha exists yet");
        // } else {
        //     System.out.println("Captcha exists!" + login.getCaptcha().getNum1());
        // }

        if (result.hasErrors()) {
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

        // model.addAttribute("authenticatedUser", login.getUsername());
        ra.addFlashAttribute("sessionId", session.getId());

        // if (session.getAttribute("authenticatedList") == null) {
        //     List<String> authenticatedList = new ArrayList<>();
        //     authenticatedList.add(login.getUsername());
        //     session.setAttribute("authenticatedList", authenticatedList);
        // } else {
        //     List<String> authenticatedList = (List<String>) session.getAttribute("authenticatedList");
        //     authenticatedList.add(login.getUsername());
        //     session.setAttribute("authenticatedList", authenticatedList);
        // }

        // for (String authenticatedUser : (List<String>) session.getAttribute("authenticatedList")) {
        //     System.out.println("Authenticated: " + authenticatedUser);
        // }

        // return "view1";
        return "redirect:/protected/view1.html";
    }

	@GetMapping(path = "/logout")
	public String logout(HttpSession session) {
		authenticationService.removeAuthenticatedSession(session.getId());
		return "redirect:/";
	}

}
