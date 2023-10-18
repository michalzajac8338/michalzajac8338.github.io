package com.michal.socialnetworkingsite.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ErrorHandler implements ErrorController {

    @RequestMapping("/error")
    public String handleError(RedirectAttributes attributes) {

        attributes.addFlashAttribute("errorOccurred", true);
        return "redirect:/Z";
    }
}
