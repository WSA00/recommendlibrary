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
    public Result historyallPageSelect(Integer page, Integer pageSize){
        return historyService.historyallPageSelect(page,pageSize);
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

}
