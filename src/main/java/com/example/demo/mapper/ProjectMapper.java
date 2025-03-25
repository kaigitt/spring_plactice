package com.example.demo.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.example.demo.entity.Project;
import com.example.demo.dto.SearchForm;
import com.example.demo.dto.SearchResult;
import com.example.demo.dto.AdvancedSearchForm;

@Mapper
public interface ProjectMapper {
    @Select("SELECT * FROM projects WHERE project_id = #{projectId}")
    Project findById(@Param("projectId") String projectId);

    // XMLで定義された検索メソッド
    List<SearchResult> searchProjects(SearchForm form);

    List<SearchResult> advancedSearchProjects(AdvancedSearchForm form);
} 