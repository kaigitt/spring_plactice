package com.example.demo.form;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Data
public class SearchForm {
    private String projectName;
    private String salesUserName;
    private String projectManagerName;
    private String wbsNo;
    private String wbsName;
    private String engineerName;
    
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate contractDateFrom;
    
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate contractDateTo;
    
    // ソート用のフィールド
    private String sort;
    private String sortDirection;
} 