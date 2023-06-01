package springsecurity.controller;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springsecurity.entity.User;

@Controller
public class HomeController {

    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String getIndexPage(Model model) {
        // 认证成功后，结果会通过SecurityContextHolder存入SecurityContext对象中 (Authentication的实现类中含有信息)
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // 如果登录失败，这里返回的就不是user对象了
        if(obj instanceof User){
            User user = (User) obj;
            model.addAttribute("loginUser",user);
        }
        return "/index";
    }

    @RequestMapping(path = "/discuss", method = RequestMethod.GET)
    public String getDiscussPage() {
        return "/site/discuss";
    }

    @RequestMapping(path = "/letter", method = RequestMethod.GET)
    public String getLetterPage() {
        return "/site/letter";
    }

    @RequestMapping(path = "/admin", method = RequestMethod.GET)
    public String getAdminPage() {
        return "/site/admin";
    }

    @RequestMapping(path = "/loginpage", method = {RequestMethod.GET, RequestMethod.POST})
    public String getLoginPage() {
        return "/site/login";
    }

    @GetMapping("/denied")
    public String getDeniedPage(){
        return "/error/404";
    }
}
