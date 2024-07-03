package com.ra.service.admin.category;

import com.ra.models.entity.Category;
import com.ra.repository.admin.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    private ICategoryRepository categoryRepository;
    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Category> getAllPage(String search, Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo -1, 7);
        if(search!=null && !search.isEmpty()){
            return categoryRepository.findCategoriesByCategoryNameLike("%"+search+"%",pageable);
        }
            return categoryRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
