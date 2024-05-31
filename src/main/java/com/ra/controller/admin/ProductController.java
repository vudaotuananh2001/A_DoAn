package com.ra.controller.admin;

import com.ra.models.entity.Category;
import com.ra.models.entity.Product;
import com.ra.service.admin.category.ICategoryService;
import com.ra.service.admin.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.lang.Long;

@Controller
@RequestMapping("/admin/product")
public class ProductController {
    @Value("${path-upload}")
    private String path;
    @Autowired
    private IProductService productService;
    @Autowired
    private ICategoryService categoryService;

    @GetMapping("")
    public String home(Model model,
                       @RequestParam(name = "search", required = false) String search,
                       @RequestParam(name = "pageNo", defaultValue = "1") Integer page) {
        Page<Product> productList = productService.getAllPage(page, search);
        model.addAttribute("totalPage", productList.getTotalPages());
        model.addAttribute("currenPage", page);
        model.addAttribute("productList", productList);
        return "admin/product/index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        List<Category> categoryList = categoryService.getAll();
        model.addAttribute("categoryList", categoryList);
        return "admin/product/add";
    }

    @PostMapping("/add")
    public String create(@ModelAttribute("product") Product product,
                         @RequestParam("img") MultipartFile file
    ) {
        String fileName = file.getOriginalFilename();
        try {
            FileCopyUtils.copy(file.getBytes(), new File(path + fileName));
            product.setImage(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        productService.save(product);
        return "redirect:/admin/product";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        List<Category> categoryList = categoryService.getAll();
        model.addAttribute("categoryList", categoryList);
        return "admin/product/edit";
    }

    @PostMapping("/edit")
    public String update(@ModelAttribute("product") Product product,
                         @RequestParam("img") MultipartFile file) {
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            try {
                FileCopyUtils.copy(file.getBytes(), new File(path + fileName));
                product.setImage(fileName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        productService.save(product);
        return "redirect:/admin/product";
    }

    @GetMapping("delete/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        Product product = productService.findById(id);
        product.setStatus(false);
        productService.save(product);
        return "redirect:/admin/product";
    }
}
