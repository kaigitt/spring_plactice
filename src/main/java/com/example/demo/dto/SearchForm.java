package com.example.demo.dto;

import lombok.Data;
import org.springframework.util.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Data
public class SearchForm {
    // プロジェクト情報
    private String projectId;
    private String projectName;
    private String salesUserName;
    private String projectManagerName;

    // WBS情報
    private String wbsNo;
    private String wbsName;

    // 技術者情報
    private String engineerName;

    // 契約日付情報
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate contractDateFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate contractDateTo;

    // ソート用のフィールドを追加
    private String sort;
    private String sortDirection;

    public void setProjectName(String projectName) {
        this.projectName = StringUtils.hasText(projectName) ? projectName : null;
    }

    public void setSalesUserName(String salesUserName) {
        this.salesUserName = StringUtils.hasText(salesUserName) ? salesUserName : null;
    }

    public void setProjectManagerName(String projectManagerName) {
        this.projectManagerName = StringUtils.hasText(projectManagerName) ? projectManagerName : null;
    }

    public void setWbsNo(String wbsNo) {
        this.wbsNo = StringUtils.hasText(wbsNo) ? wbsNo : null;
    }

    public void setWbsName(String wbsName) {
        this.wbsName = StringUtils.hasText(wbsName) ? wbsName : null;
    }

    public void setEngineerName(String engineerName) {
        this.engineerName = StringUtils.hasText(engineerName) ? engineerName : null;
    }

    // 日付のカスタムセッター
    public void setContractDateFrom(LocalDate contractDateFrom) {
        this.contractDateFrom = contractDateFrom;
    }

    public void setContractDateTo(LocalDate contractDateTo) {
        this.contractDateTo = contractDateTo;
    }
} 