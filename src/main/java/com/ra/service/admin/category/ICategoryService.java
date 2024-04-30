package com.ra.service.admin.category;

import com.ra.models.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICategoryService {
    List<Category> getAll();
    Category save(Category category);
    Category findById(Long id);
    void deleteById(Long id);
    Page<Category> getAllPage(String search, Integer pageNo);
}
