package com.example.demo.controller;

import com.example.demo.dto.SearchForm;
import com.example.demo.dto.SearchResult;
import com.example.demo.dto.AdvancedSearchForm;
import com.example.demo.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.nio.charset.StandardCharsets;

@Controller
public class SearchController {
    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    private SearchService searchService;

    @GetMapping("/")
    public String index(@ModelAttribute SearchForm form, Model model) {
        model.addAttribute("searchForm", form);
        return "index";
    }

    @GetMapping("/search")
    public String search(
            @RequestParam(required = false) String projectName,
            @RequestParam(required = false) String salesUserName,
            @RequestParam(required = false) String projectManagerName,
            @RequestParam(required = false) String wbsNo,
            @RequestParam(required = false) String wbsName,
            @RequestParam(required = false) String engineerName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate contractDateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate contractDateTo,
            @RequestParam(defaultValue = "0") int page,
            Model model) {
        try {
            SearchForm form = new SearchForm();
            form.setProjectName(projectName);
            form.setSalesUserName(salesUserName);
            form.setProjectManagerName(projectManagerName);
            form.setWbsNo(wbsNo);
            form.setWbsName(wbsName);
            form.setEngineerName(engineerName);
            form.setContractDateFrom(contractDateFrom);
            form.setContractDateTo(contractDateTo);

            Page<SearchResult> results = searchService.search(form, page);

            model.addAttribute("projects", results.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", results.getTotalPages());
            model.addAttribute("totalElements", results.getTotalElements());
            model.addAttribute("searchForm", form);

            return "search-results";
        } catch (Exception e) {
            logger.error("検索処理でエラーが発生しました", e);
            model.addAttribute("error", "検索処理でエラーが発生しました。");
            model.addAttribute("searchForm", new SearchForm());
            return "search-results";
        }
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        logger.error("予期せぬエラーが発生しました", e);
        model.addAttribute("error", "システムエラーが発生しました。");
        model.addAttribute("searchForm", new SearchForm());
        return "search-results";
    }

    @GetMapping("/results")
    public String searchResults(@ModelAttribute SearchForm searchForm, @RequestParam(defaultValue = "0") int page, Model model) {
        try {
            Page<SearchResult> results = searchService.search(searchForm, page);

            model.addAttribute("projects", results.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", results.getTotalPages());
            model.addAttribute("totalElements", results.getTotalElements());
            model.addAttribute("searchForm", searchForm);

            return "search-results";
        } catch (Exception e) {
            logger.error("検索処理でエラーが発生しました", e);
            model.addAttribute("error", "検索処理でエラーが発生しました。");
            model.addAttribute("searchForm", new SearchForm());
            return "search-results";
        }
    }

    @GetMapping("/search/advanced")
    public String advancedSearch(@ModelAttribute AdvancedSearchForm form,
                               @RequestParam(defaultValue = "0") int page,
                               Model model) {
        Page<SearchResult> searchResults = searchService.advancedSearch(form, page);
        
        model.addAttribute("projects", searchResults.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", searchResults.getTotalPages());
        model.addAttribute("totalElements", searchResults.getTotalElements());
        model.addAttribute("searchForm", form);
        
        return "search-results";
    }

    @GetMapping("/search/advanced/csv")
    public ResponseEntity<byte[]> downloadCsv(@ModelAttribute AdvancedSearchForm form) {
        // 検索条件の入力チェック
        List<String> fields = form.getFields();
        List<String> values = form.getValues();
        
        // 検索値の入力チェック
        for (int i = 0; i < fields.size(); i++) {
            if (values.get(i) == null || values.get(i).trim().isEmpty()) {
                throw new IllegalArgumentException("検索条件の入力値が未入力の項目があります。全ての検索条件に値を入力してください。");
            }
        }
        
        // CSVヘッダー
        StringBuilder csv = new StringBuilder();
        csv.append("検索項目,検索値,比較演算子,論理演算子\n");
        
        // 検索条件をCSVに変換
        List<String> comparisons = form.getComparisons();
        List<String> operators = form.getOperators();
        
        for (int i = 0; i < fields.size(); i++) {
            String field = translateFieldName(fields.get(i));
            String value = values.get(i);
            String comparison = translateComparisonOperator(comparisons.get(i));
            String operator = i < operators.size() ? operators.get(i) : "";
            
            csv.append(String.format("%s,%s,%s,%s\n",
                escapeCSV(field),
                escapeCSV(value),
                escapeCSV(comparison),
                escapeCSV(operator)
            ));
        }
        
        // 現在の日付を取得してファイル名に使用
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String filename = String.format("searchProject_%s.csv", today);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        // ファイル名に日本語を含む場合のエンコーディング対応
        try {
            String encodedFilename = new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            headers.setContentDispositionFormData("attachment", encodedFilename);
        } catch (Exception e) {
            headers.setContentDispositionFormData("attachment", filename);
        }
        headers.set("Content-Type", "text/csv; charset=UTF-8");
        
        return ResponseEntity.ok()
                .headers(headers)
                .body(csv.toString().getBytes(StandardCharsets.UTF_8));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity
            .badRequest()
            .contentType(MediaType.TEXT_PLAIN)
            .body(e.getMessage());
    }

    private String translateFieldName(String field) {
        switch (field) {
            case "project_name": return "プロジェクト名";
            case "sales_user_name": return "営業担当者";
            case "project_manager_name": return "プロジェクトマネージャー";
            case "wbs_no": return "WBS番号";
            case "wbs_name": return "WBS名";
            case "engineer_name": return "エンジニア名";
            case "contract_date": return "契約年月日";
            default: return field;
        }
    }

    private String translateComparisonOperator(String comparison) {
        switch (comparison) {
            case "contains": return "を含む";
            case "startsWith": return "から始まる";
            case "endsWith": return "で終わる";
            case "equals": return "と一致する";
            case "greater": return "より後";
            case "less": return "より前";
            case "greaterOrEqual": return "以降";
            case "lessOrEqual": return "以前";
            default: return comparison;
        }
    }

    private String escapeCSV(String value) {
        if (value == null) {
            return "";
        }
        // カンマを含む場合はダブルクォートで囲む
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }

    @GetMapping("/search/count")
    public String getSearchCount(
            @ModelAttribute SearchForm form,
            Model model) {
        try {
            long count = searchService.getSearchCount(form);
            model.addAttribute("count", count);
            return "fragments/search-count :: searchCountModal";
        } catch (Exception e) {
            logger.error("検索件数の取得でエラーが発生しました", e);
            model.addAttribute("error", "検索件数の取得でエラーが発生しました。");
            return "fragments/search-count :: searchCountModal";
        }
    }

    @GetMapping("/search/advanced/count")
    public String getAdvancedSearchCount(
            @ModelAttribute AdvancedSearchForm form,
            Model model) {
        try {
            long count = searchService.getAdvancedSearchCount(form);
            model.addAttribute("count", count);
            return "fragments/search-count :: searchCountModal";
        } catch (Exception e) {
            logger.error("詳細検索の件数取得でエラーが発生しました", e);
            model.addAttribute("error", "検索件数の取得でエラーが発生しました。");
            return "fragments/search-count :: searchCountModal";
        }
    }
}
