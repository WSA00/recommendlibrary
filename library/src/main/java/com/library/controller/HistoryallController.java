package com.library.controller;

import com.library.pojo.History;
import com.library.service.HistoryService;
import com.library.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("historyall")
public class HistoryallController {

    @Autowired
    private HistoryService historyService;

    @PreAuthorize("hasAnyAuthority('ROOT','USER')")
    @GetMapping("")
    public Result historyPageSelect(Integer page, Integer pageSize){
        return historyService.historyPageSelect(page,pageSize);
    }

    @PreAuthorize("hasAnyAuthority('ROOT','USER')")
    @GetMapping("{id}")
    public Result selectHistoryById(@PathVariable Integer id){
        return historyService.selectHistoryById(id);
    }

    @PreAuthorize("hasAnyAuthority('ROOT','USER')")
    @PatchMapping("{id}")
    public Result UpdateHistoryById(@PathVariable Integer id){
        return historyService.UpdateHistoryById(id);
    }

    @PreAuthorize("hasAnyAuthority('ROOT','USER')")
    @PostMapping("")
    public Result createHistory(@RequestBody History history){
        return historyService.createHistory(history);
    }

    @PreAuthorize("hasAnyAuthority('ROOT','USER')")
    @PatchMapping("/{id}/continue")
    public Result continueHistoryById(@PathVariable Integer id){
        return historyService.continueHistoryById(id);
    }
    @PreAuthorize("hasAnyAuthority('ROOT','USER')")
    @PatchMapping("/{id}/deal")
    public Result dealHistoryById(@PathVariable Integer id){
        return historyService.dealHistoryById(id);
    }

    @PreAuthorize("hasAnyAuthority('ROOT')")
    @DeleteMapping("{id}")
    public Result deleteHistoryById(@PathVariable Integer id){
        return historyService.deleteHistoryById(id);
    }

}
