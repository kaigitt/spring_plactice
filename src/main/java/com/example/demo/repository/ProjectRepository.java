package com.example.demo.repository;

import com.example.demo.entity.Project;
import com.example.demo.model.SearchSpecifications;
import com.example.demo.form.SearchForm;
import com.example.demo.form.AdvancedSearchForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {
    
    default Page<Project> findBySearchCriteria(SearchForm form, Pageable pageable) {
        return findAll((root, query, cb) -> {
            return SearchSpecifications.buildSearchSpecification(form, root, query, cb);
        }, pageable);
    }

    default Page<Project> findByAdvancedSearchCriteria(AdvancedSearchForm form, Pageable pageable) {
        return findAll((root, query, cb) -> {
            return SearchSpecifications.buildAdvancedSearchSpecification(form, root, query, cb);
        }, pageable);
    }
} 