package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "engineers")
@Data
public class Engineer {
    @Id
    private String engineerId;
    private String wbsNo;
    private String engineerName;
} 