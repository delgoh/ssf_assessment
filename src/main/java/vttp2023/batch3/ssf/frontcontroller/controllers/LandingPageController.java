package vttp2023.batch3.ssf.frontcontroller.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vttp2023.batch3.ssf.frontcontroller.models.Login;

@Controller
@RequestMapping(path = "/")
public class LandingPageController {
    
    @GetMapping
    public String goToLandingPage(Model model) {
        model.addAttribute("login", new Login());
        return "view0";
    }

}
