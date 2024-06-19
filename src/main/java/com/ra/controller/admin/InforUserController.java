package com.ra.controller.admin;

import com.ra.models.entity.User;
import com.ra.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/user")
public class InforUserController {
    @Autowired
    private UserService userService;
    @GetMapping("")
    public String homeAccount(Model model,
                              @RequestParam(name = "search",required = false) String search,
                              @RequestParam(name = "pageNo" ,defaultValue = "1")Integer page
    ){
        System.out.println(search);
        Page<User> listUser =userService.getAllUser(search,page);
        model.addAttribute("listUser",listUser);
        model.addAttribute("totalPage", listUser.getTotalPages());
        model.addAttribute("currenPage", page);
        return  "admin/account/account";
    }
}
