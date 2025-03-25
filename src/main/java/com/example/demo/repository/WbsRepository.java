package com.example.demo.repository;

import com.example.demo.entity.Wbs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WbsRepository extends JpaRepository<Wbs, String> {
    List<Wbs> findByProjectId(String projectId);
} 