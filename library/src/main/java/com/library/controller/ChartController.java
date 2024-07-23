package com.library.controller;

import com.library.service.ChartService;
import com.library.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("chart")
public class ChartController {

    @Autowired
    private ChartService chartService;
    @GetMapping("/{id}/warn")
    public Result warn(@PathVariable Integer id){
        return chartService.warn(id);
    }
    @GetMapping("/total/income")
    public Result income(){
        return chartService.income();
    }

    @GetMapping("/total/orders")
    public Result orders(){
        return chartService.orders();
    }

    @GetMapping("/total/warehouses")
    public Result warehouses(){
        return chartService.warehouses();
    }

    @GetMapping("/total/users")
    public Result users(){
        return chartService.users();
    }

    @GetMapping("/ranking/user")
    public Result user(){
        return chartService.user();
    }

    @GetMapping("/ranking/book")
    public Result book(){
        return chartService.book();
    }

}
