package com.example.demo.controller;

import com.example.demo.entity.Engineer;
import com.example.demo.service.EngineerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/engineers")
public class EngineerController {
    @Autowired
    private EngineerService engineerService;

    // @GetMapping
    // public List<Engineer> findAll() {
    //     return engineerService.findAll();
    // }

    // @GetMapping("/{id}")
    // public ResponseEntity<Engineer> findById(@PathVariable String id) {
    //     return engineerService.findById(id)
    //             .map(ResponseEntity::ok)
    //             .orElse(ResponseEntity.notFound().build());
    // }

    @GetMapping("/wbs/{wbsNo}")
    public List<Engineer> findByWbsNo(@PathVariable String wbsNo) {
        return engineerService.findByWbsNo(wbsNo);
    }

    // @PostMapping
    // public Engineer create(@RequestBody Engineer engineer) {
    //     return engineerService.save(engineer);
    // }

    // @PutMapping("/{id}")
    // public ResponseEntity<Engineer> update(@PathVariable String id, @RequestBody Engineer engineer) {
    //     return engineerService.findById(id)
    //             .map(existingEngineer -> {
    //                 engineer.setEngineerId(id);
    //                 return ResponseEntity.ok(engineerService.save(engineer));
    //             })
    //             .orElse(ResponseEntity.notFound().build());
    // }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> delete(@PathVariable String id) {
    //     return engineerService.findById(id)
    //             .map(engineer -> {
    //                 engineerService.deleteById(id);
    //                 return ResponseEntity.ok().<Void>build();
    //             })
    //             .orElse(ResponseEntity.notFound().build());
    // }
} 