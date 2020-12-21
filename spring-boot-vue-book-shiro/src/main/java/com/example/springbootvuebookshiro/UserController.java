package com.example.springbootvuebookshiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class UserController {
    @PostMapping("/doLogin")
    public String doLogin(String username, String password, Model model) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (AuthenticationException ex) {
            model.addAttribute("error", "用户名或密码错误");
            return "login";
        }
        return "redirect:/index";
    }

    // @RequiresRoles("admin")
    @RequiresPermissions("write")
    @GetMapping("/adminPage")
    public String adminPage() {
        return "admin";
    }

    // 通过role直接限制权限时，角色与权限的关联关系是硬编码的。In other words, when we want
    // to grant or revoke access to a resource, we'll have to change the source
    // code. Of course, this also means rebuilding and redeploying.
    @RequiresRoles(value = {"admin", "user"}, logical = Logical.OR)
    @GetMapping("/userPage")
    public String userPage() {
        return "user";
    }

}
