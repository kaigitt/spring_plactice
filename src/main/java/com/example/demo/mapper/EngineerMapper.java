package com.example.demo.mapper;

import com.example.demo.entity.Engineer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface EngineerMapper {
    @Select({
        "<script>",
        "SELECT * FROM engineers",
        "<where>",
        "  <if test='wbsNo != null and wbsNo != \"\"'>",
        "    wbs_no = #{wbsNo}",
        "  </if>",
        "  <if test='engineerName != null and engineerName != \"\"'>",
        "    AND engineer_name LIKE CONCAT('%', #{engineerName}, '%')",
        "  </if>",
        "</where>",
        "ORDER BY engineer_id",
        "</script>"
    })
    List<Engineer> searchEngineers(
        @Param("wbsNo") String wbsNo,
        @Param("engineerName") String engineerName
    );

    @Select({
        "<script>",
        "SELECT * FROM engineers",
        "<where>",
        "  wbs_no IN",
        "  <foreach collection='wbsNos' item='wbsNo' open='(' separator=',' close=')'>",
        "    #{wbsNo}",
        "  </foreach>",
        "  <if test='engineerName != null and engineerName != \"\"'>",
        "    AND engineer_name LIKE CONCAT('%', #{engineerName}, '%')",
        "  </if>",
        "</where>",
        "ORDER BY wbs_no, engineer_id",
        "</script>"
    })
    List<Engineer> searchEngineersByWbsNos(
        @Param("wbsNos") List<String> wbsNos,
        @Param("engineerName") String engineerName
    );

    @Select("SELECT * FROM engineers WHERE engineer_id = #{engineerId}")
    Engineer findById(@Param("engineerId") String engineerId);
} 