package com.library.controller;

import com.library.pojo.Type;
import com.library.service.TypeService;
import com.library.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("type")
public class TypeController {
    @Autowired
    private TypeService typeService;

    @PreAuthorize("hasAnyAuthority('ROOT')")
    @PostMapping("")
    public Result createType(@RequestBody Type type){
        return typeService.createType(type);
    }

    @PreAuthorize("hasAnyAuthority('ROOT','USER')")
    @GetMapping("")
    public Result typePageSelect(Integer page, Integer pageSize){

        if (page == null && pageSize == null) {
            return typeService.getAllType();
        }else {
            return typeService.TypePageSelect(page,pageSize);
        }
    }

    @PreAuthorize("hasAnyAuthority('ROOT')")
    @GetMapping("{id}")
    public Result selectTypeById(@PathVariable Integer id){
        return typeService.selectTypeById(id);
    }

    @PreAuthorize("hasAnyAuthority('ROOT')")
    @PatchMapping("{id}")
    public Result UpdateTypeById(@PathVariable Integer id, @RequestBody Type type){
        return typeService.UpdateTypeById(id,type);
    }

    @PreAuthorize("hasAnyAuthority('ROOT')")
    @DeleteMapping("{id}")
    public Result deleteTypeById(@PathVariable Integer id){
        return typeService.deleteTypeById(id);
    }

}
