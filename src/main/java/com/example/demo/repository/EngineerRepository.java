package com.example.demo.repository;

import com.example.demo.entity.Engineer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EngineerRepository extends JpaRepository<Engineer, String> {
    List<Engineer> findByWbsNo(String wbsNo);
} 