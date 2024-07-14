package com.library.controller;

import com.library.pojo.Stockin;
import com.library.service.StockinService;
import com.library.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("stockin")
public class StockinController {

    @Autowired
    private StockinService stockinService;

    @PreAuthorize("hasAnyAuthority('ROOT')")
    @PostMapping("")
    public Result createStockin(@RequestBody Stockin stockin){
        return stockinService.createStockin(stockin);
    }

    @PreAuthorize("hasAnyAuthority('ROOT')")
    @GetMapping("")
    public Result stockinPageSelect(Integer page, Integer pageSize){
        return stockinService.stockinPageSelect(page,pageSize);
    }

    @PreAuthorize("hasAnyAuthority('ROOT')")
    @DeleteMapping("{id}")
    public Result deleteStockinById(@PathVariable Integer id){
        return stockinService.deleteStockinById(id);
    }
}
