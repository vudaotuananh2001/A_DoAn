package com.ra.controller.admin;

import com.ra.models.entity.Category;
import com.ra.models.entity.Product;
import com.ra.service.admin.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    @GetMapping("")
    public String home(Model model,
                       @RequestParam(name = "search",required = false) String search,
                       @RequestParam(name = "pageNo" ,defaultValue = "1")Integer page ){
        System.out.println("nhảy vao day chua");
        Page<Category> categoryList =categoryService.getAllPage(search,page);
        model.addAttribute("totalPage",categoryList.getTotalPages());
        model.addAttribute("currenPage",page);
        model.addAttribute("categoryList", categoryList);
        return "admin/category/index";
    }

    @GetMapping("/add")
    public String add(Model model){
        Category category = new Category();
        model.addAttribute("category",category);
        return "admin/category/add";
    }

    @PostMapping("/add")
    public  String create(@ModelAttribute("category") Category category){
        categoryService.save(category);
        return  "redirect:/admin/category";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable ("id") Long id,
                       Model model
    ){
        System.out.println("đây là gì");
        Category category =categoryService.findById(id);
        model.addAttribute("category",category);
        return "/admin/category/edit";
    }

    @PostMapping("/edit")
    public String update_category(@ModelAttribute("category") Category category){
        categoryService.save(category);
        return "redirect:/admin/category";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable ("id") Long id){
        Category   category= categoryService.findById(id);
        category.setStatus(false);
        categoryService.save(category);
        return "redirect:/admin/category";
    }
}
