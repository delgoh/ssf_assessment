package vttp2023.batch3.ssf.frontcontroller.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import vttp2023.batch3.ssf.frontcontroller.models.Login;
import vttp2023.batch3.ssf.frontcontroller.services.AuthenticationService;

@Controller
@RequestMapping(path = "/protected")
public class ProtectedController {

	@Autowired
	private AuthenticationService authenticationService;

	// TODO Task 5
	// Write a controller to protect resources rooted under /protected

	@GetMapping(path = "/view1.html")
	public String checkUserAuthentication(@ModelAttribute("sessionId") String sessionId, HttpSession session, Model model) {
		if (sessionId.isEmpty()) {
			if (authenticationService.isSessionAuthenticated(session.getId())) {
				return "view1";
			}
        	model.addAttribute("login", new Login());
			return "view0";
		} else {
			authenticationService.addAuthenticatedSession(sessionId);
			return "view1";
		}
	}

}
