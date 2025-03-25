package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "projects")
@Data
public class Project {
    // プロジェクト基本情報
    @Id
    @Column(name = "project_id")
    private String projectId;          // プロジェクトID

    @Column(name = "project_name")
    private String projectName;        // 業務名称
    
    // 営業担当者情報
    @Column(name = "sales_user_id")
    private String salesUserId;        // 営業担当者ID

    @Column(name = "sales_user_name")
    private String salesUserName;      // 営業担当者名
    
    // 管理技術者情報
    @Column(name = "project_manager_id")
    private String projectManagerId;

    @Column(name = "project_manager_name")
    private String projectManagerName;

    @Column(name = "contract_date")
    private LocalDate contractDate;
} 