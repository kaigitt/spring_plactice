package com.example.demo.specification;

import com.example.demo.entity.Project;
import com.example.demo.form.SearchForm;
import com.example.demo.form.AdvancedSearchForm;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class SearchSpecifications {

    public static Specification<Project> buildSearchSpecification(SearchForm form, Root<Project> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
        if (form.getWbsNo() != null && !form.getWbsNo().isEmpty()) {
            predicates.add(cb.like(root.get("wbsNo"), "%" + form.getWbsNo() + "%"));
        }
        if (form.getWbsName() != null && !form.getWbsName().isEmpty()) {
            predicates.add(cb.like(root.get("wbsName"), "%" + form.getWbsName() + "%"));
        }
        if (form.getEngineerName() != null && !form.getEngineerName().isEmpty()) {
            predicates.add(cb.like(root.get("engineerName"), "%" + form.getEngineerName() + "%"));
        }
        if (form.getContractDateFrom() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("contractDate"), form.getContractDateFrom()));
        }
        if (form.getContractDateTo() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("contractDate"), form.getContractDateTo()));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }

    public static Specification<Project> buildAdvancedSearchSpecification(AdvancedSearchForm form, Root<Project> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        for (int i = 0; i < form.getFields().size(); i++) {
            String field = form.getFields().get(i);
            String value = form.getValues().get(i);
            String comparison = form.getComparisons().get(i);
            
            if (value != null && !value.isEmpty()) {
                Path<String> path = root.get(field);
                switch (comparison) {
                    case "equals":
                        predicates.add(cb.equal(path, value));
                        break;
                    case "contains":
                        predicates.add(cb.like(path, "%" + value + "%"));
                        break;
                    case "startsWith":
                        predicates.add(cb.like(path, value + "%"));
                        break;
                    case "endsWith":
                        predicates.add(cb.like(path, "%" + value));
                        break;
                }
            }
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
} 