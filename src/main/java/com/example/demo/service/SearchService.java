package com.example.demo.service;

import com.example.demo.dto.SearchForm;
import com.example.demo.dto.SearchResult;
import com.example.demo.dto.AdvancedSearchForm;
import com.example.demo.mapper.ProjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SearchService {
    private static final Logger logger = LoggerFactory.getLogger(SearchService.class);
    private static final int PAGE_SIZE = 10;

    @Autowired
    private ProjectMapper projectMapper;

    public Page<SearchResult> search(SearchForm form, int page, String sort, String direction) {
        try {
            List<SearchResult> results = projectMapper.searchProjects(form, sort, direction);
            return createPage(results, page);
        } catch (Exception e) {
            logger.error("検索処理で予期せぬエラーが発生しました", e);
            throw new RuntimeException("検索処理でエラーが発生しました", e);
        }
    }

    public Page<SearchResult> advancedSearch(AdvancedSearchForm form, int page, String sort, String direction) {
        try {
            List<SearchResult> results = projectMapper.advancedSearchProjects(form, sort, direction);
            return createPage(results, page);
        } catch (Exception e) {
            logger.error("詳細検索処理で予期せぬエラーが発生しました", e);
            throw new RuntimeException("詳細検索処理でエラーが発生しました", e);
        }
    }

    private Page<SearchResult> createPage(List<SearchResult> results, int page) {
        int start = page * PAGE_SIZE;
        int end = Math.min((start + PAGE_SIZE), results.size());
        List<SearchResult> pageContent = results.subList(start, end);
        return new PageImpl<>(pageContent, PageRequest.of(page, PAGE_SIZE), results.size());
    }

    public List<SearchResult> getAllAdvancedSearchResults(AdvancedSearchForm form) {
        try {
            return projectMapper.advancedSearchProjects(form, "project_id", "asc");
        } catch (Exception e) {
            logger.error("CSV出力用の検索処理で予期せぬエラーが発生しました", e);
            throw new RuntimeException("CSV出力用の検索処理でエラーが発生しました", e);
        }
    }

    public long getSearchCount(SearchForm form) {
        try {
            List<SearchResult> results = projectMapper.searchProjects(form, "project_id", "asc");
            return results.size();
        } catch (Exception e) {
            logger.error("検索件数の取得で予期せぬエラーが発生しました", e);
            throw new RuntimeException("検索件数の取得でエラーが発生しました", e);
        }
    }

    public long getAdvancedSearchCount(AdvancedSearchForm form) {
        try {
            List<SearchResult> results = projectMapper.advancedSearchProjects(form, "project_id", "asc");
            return results.size();
        } catch (Exception e) {
            logger.error("詳細検索の件数取得で予期せぬエラーが発生しました", e);
            throw new RuntimeException("詳細検索の件数取得でエラーが発生しました", e);
        }
    }

    private Pageable createPageable(int page, String sort, String direction) {
        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        Sort sorting = Sort.by(sortDirection, sort);
        return PageRequest.of(page, 10, sorting);
    }
} 