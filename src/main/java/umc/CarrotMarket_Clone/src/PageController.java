package umc.CarrotMarket_Clone.src;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping(value = "/401")
    public String Error401(){
        return "401";
    }

    @RequestMapping(value = "/404")
    public String Error404(){
        return "404";
    }

    @RequestMapping(value = "/500")
    public String Error500(){
        return "500";
    }

    @RequestMapping(value = "/login")
    public String login(){
        return "login";
    }

    @RequestMapping(value = "/")
    public String main(){
        return "main";
    }

    @RequestMapping(value = "/memberShow")
    public String memberShow(){
        return "memberShow";
    }

    @RequestMapping(value = "/password")
    public String password(){
        return "password";
    }

    @RequestMapping(value = "/register")
    public String register(){
        return "register";
    }

    @RequestMapping(value = "/setting")
    public String setting(){
        return "setting";
    }



}
