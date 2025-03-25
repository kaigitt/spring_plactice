package com.example.demo.specification;

import com.example.demo.dto.SearchForm;
import com.example.demo.entity.Project;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class SearchSpecification {
    
    public static Specification<Project> withSearchForm(SearchForm form) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            if (form.getProjectName() != null && !form.getProjectName().isEmpty()) {
                predicates.add(cb.like(root.get("projectName"), "%" + form.getProjectName() + "%"));
            }
            
            if (form.getSalesUserName() != null && !form.getSalesUserName().isEmpty()) {
                predicates.add(cb.like(root.get("salesUserName"), "%" + form.getSalesUserName() + "%"));
            }
            
            if (form.getProjectManagerName() != null && !form.getProjectManagerName().isEmpty()) {
                predicates.add(cb.like(root.get("projectManagerName"), "%" + form.getProjectManagerName() + "%"));
            }
            
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
} 