package com.example.demo.service;

import com.example.demo.dto.SearchForm;
import com.example.demo.dto.SearchResult;
import com.example.demo.mapper.ProjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SearchService {
    private static final Logger logger = LoggerFactory.getLogger(SearchService.class);
    private static final int PAGE_SIZE = 5;

    @Autowired
    private ProjectMapper projectMapper;

    public Page<SearchResult> search(SearchForm form, int page) {

        try {
            List<SearchResult> results = projectMapper.searchProjects(form);

            int start = page * PAGE_SIZE;
            int end = Math.min((start + PAGE_SIZE), results.size());

            List<SearchResult> pageContent = results.subList(start, end);

            return new PageImpl<>(pageContent, PageRequest.of(page, PAGE_SIZE), results.size());
        } catch (Exception e) {
            logger.error("検索処理で予期せぬエラーが発生しました", e);
            throw new RuntimeException("検索処理でエラーが発生しました", e);
        }
    }
} 