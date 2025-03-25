package com.example.demo.controller;

import com.example.demo.entity.Wbs;
import com.example.demo.entity.Engineer;
import com.example.demo.service.WbsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/wbs")
public class WbsController {
    private static final Logger log = LoggerFactory.getLogger(WbsController.class);

    private final WbsService wbsService;

    public WbsController(WbsService wbsService) {
        this.wbsService = wbsService;
    }

    // @GetMapping
    // public List<Wbs> findAll() {
    //     return wbsService.findAll();
    // }

    // @PostMapping
    // public Wbs create(@RequestBody Wbs wbs) {
    //     return wbsService.save(wbs);
    // }

    // @PutMapping("/{id}")
    // public ResponseEntity<Wbs> update(@PathVariable String id, @RequestBody Wbs wbs) {
    //     return wbsService.findById(id)
    //             .map(existingWbs -> {
    //                 wbs.setWbsNo(id);
    //                 return ResponseEntity.ok(wbsService.save(wbs));
    //             })
    //             .orElse(ResponseEntity.notFound().build());
    // }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> delete(@PathVariable String id) {
    //     return wbsService.findById(id)
    //             .map(wbs -> {
    //                 wbsService.deleteById(id);
    //                 return ResponseEntity.ok().<Void>build();
    //             })
    //             .orElse(ResponseEntity.notFound().build());
    // }

    @GetMapping("/project/{projectId}")
    public List<Wbs> getWbsByProjectId(@PathVariable String projectId) {
        return wbsService.findByProjectId(projectId);
    }

    @GetMapping("/{wbsNo}/engineers")
    public ResponseEntity<List<Engineer>> getEngineersForWbs(@PathVariable String wbsNo) {
        try {
            List<Engineer> engineers = wbsService.findEngineersForWbs(wbsNo);
            log.debug("WBS番号: {} の技術者情報: {}", wbsNo, engineers);
            return ResponseEntity.ok(engineers);
        } catch (Exception e) {
            log.error("技術者情報の取得に失敗しました。WBS番号: " + wbsNo, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
} 