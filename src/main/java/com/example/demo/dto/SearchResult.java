package com.example.demo.dto;

import com.example.demo.entity.Project;
import lombok.Data;
import java.time.LocalDate;

@Data
public class SearchResult {
    // プロジェクト情報
    private String projectId;
    private String projectName;
    private String salesUserName;
    private String projectManagerName;
    private LocalDate contractDate;

    public static SearchResult from(Project project) {
        SearchResult result = new SearchResult();
        result.setProjectId(project.getProjectId());
        result.setProjectName(project.getProjectName());
        result.setSalesUserName(project.getSalesUserName());
        result.setProjectManagerName(project.getProjectManagerName());
        result.setContractDate(project.getContractDate());
        return result;
    }
} 