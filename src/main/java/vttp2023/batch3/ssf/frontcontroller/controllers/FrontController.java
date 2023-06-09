package vttp2023.batch3.ssf.frontcontroller.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String login(@Valid Login login, BindingResult result, Model model) {
        System.out.println("Username keyed in: " + login.getUsername());

        if (result.hasErrors()) {
            return "view0";
        }

		try {
			authenticationService.authenticate(login.getUsername(), login.getPassword());
		} catch (Exception e) {
			return "view0";
		}
        

        return "view1";
    }

}
