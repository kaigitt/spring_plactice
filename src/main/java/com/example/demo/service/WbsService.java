package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.example.demo.entity.Wbs;
import com.example.demo.entity.Engineer;
import com.example.demo.mapper.WbsMapper;

@Service
public class WbsService {

    private final WbsMapper wbsMapper;

    public WbsService(WbsMapper wbsMapper) {
        this.wbsMapper = wbsMapper;
    }

    // public List<Wbs> findAll() {
    //     return wbsMapper.findAll();
    // }

    public Optional<Wbs> findById(String id) {
        return Optional.ofNullable(wbsMapper.findByWbsNo(id));
    }

    // public Wbs save(Wbs wbs) {
    //     return wbsMapper.insert(wbs);
    // }

    // public void deleteById(String id) {
    //     wbsMapper.deleteByWbsNo(id);
    // }

    public List<Wbs> findByProjectId(String projectId) {
        return wbsMapper.findByProjectId(projectId);
    }

    public List<Engineer> findEngineersForWbs(String wbsNo) {
        return wbsMapper.findEngineersForWbs(wbsNo);
    }
} 