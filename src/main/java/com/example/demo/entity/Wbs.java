package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "wbs")
@Data
public class Wbs {
    @Id
    private String wbsNo;
    private String projectId;
    private String wbsName;
} 