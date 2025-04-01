package com.example.demo.dto;

import java.util.List;
import lombok.Data;

@Data
public class AdvancedSearchForm {
    private List<String> fields;      // 検索対象のフィールド
    private List<String> values;      // 検索値
    private List<String> operators;   // 演算子（AND/OR）
    private List<String> comparisons; // 比較演算子（を含む、から始まる、で終わる、など）
    
    // ソート用のフィールドを追加
    private String sort;
    private String sortDirection;
} 