package com.example.demo.controller;

import com.example.demo.dto.SearchForm;
import com.example.demo.dto.SearchResult;
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
}
