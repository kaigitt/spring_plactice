package com.example.demo.service;

import com.example.demo.entity.Engineer;
import com.example.demo.repository.EngineerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EngineerService {
    @Autowired
    private EngineerRepository engineerRepository;

    public List<Engineer> findAll() {
        return engineerRepository.findAll();
    }

    public Optional<Engineer> findById(String id) {
        return engineerRepository.findById(id);
    }

    public List<Engineer> findByWbsNo(String wbsNo) {
        return engineerRepository.findByWbsNo(wbsNo);
    }

    public Engineer save(Engineer engineer) {
        return engineerRepository.save(engineer);
    }

    public void deleteById(String id) {
        engineerRepository.deleteById(id);
    }
}