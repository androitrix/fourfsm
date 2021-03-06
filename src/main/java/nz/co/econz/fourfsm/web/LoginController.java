package nz.co.econz.fourfsm.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap model) {
       return "login";
    }
    
    @RequestMapping(value = "/login/process", method = RequestMethod.POST)
    public String loginProcess(ModelMap model) {
       return "login";
    }
}
