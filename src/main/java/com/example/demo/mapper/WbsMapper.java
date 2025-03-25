package com.example.demo.mapper;

import com.example.demo.entity.Wbs;
import com.example.demo.entity.Engineer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface WbsMapper {
    @Select({
        "<script>",
        "SELECT * FROM wbs",
        "<where>",
        "  <if test='projectId != null and projectId != \"\"'>",
        "    project_id = #{projectId}",
        "  </if>",
        "  <if test='wbsName != null and wbsName != \"\"'>",
        "    AND wbs_name LIKE CONCAT('%', #{wbsName}, '%')",
        "  </if>",
        "</where>",
        "ORDER BY wbs_no",
        "</script>"
    })
    List<Wbs> searchWbs(
        @Param("projectId") String projectId,
        @Param("wbsName") String wbsName
    );

    @Select({
        "<script>",
        "SELECT * FROM wbs",
        "<where>",
        "  project_id IN",
        "  <foreach collection='projectIds' item='projectId' open='(' separator=',' close=')'>",
        "    #{projectId}",
        "  </foreach>",
        "  <if test='wbsName != null and wbsName != \"\"'>",
        "    AND wbs_name LIKE CONCAT('%', #{wbsName}, '%')",
        "  </if>",
        "</where>",
        "ORDER BY project_id, wbs_no",
        "</script>"
    })
    List<Wbs> searchWbsByProjectIds(
        @Param("projectIds") List<String> projectIds,
        @Param("wbsName") String wbsName
    );

    @Select("SELECT * FROM wbs WHERE wbs_no = #{wbsNo}")
    Wbs findByWbsNo(@Param("wbsNo") String wbsNo);

    @Select("SELECT * FROM wbs WHERE project_id = #{projectId}")
    List<Wbs> findByProjectId(@Param("projectId") String projectId);

    @Select("SELECT * FROM engineers WHERE wbs_no = #{wbsNo}")
    List<Engineer> findEngineersForWbs(@Param("wbsNo") String wbsNo);
} 