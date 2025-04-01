package com.example.demo.form;

import lombok.Data;
import java.util.List;

@Data
public class AdvancedSearchForm {
    private List<String> fields;
    private List<String> values;
    private List<String> comparisons;
    private List<String> operators;
    
    // ソート用のフィールド
    private String sort;
    private String sortDirection;
} 