package com.library.controller;

import com.library.service.ChartService;
import com.library.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("chart")
public class ChartController {

    @Autowired
    private ChartService chartService;

    @GetMapping("/total/income")
    public Result income(){
        return chartService.income();
    }

    @GetMapping("/total/sales")
    public Result sales(){
        return chartService.sales();
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

    @GetMapping("/ranking/car")
    public Result car(){
        return chartService.car();
    }

}
