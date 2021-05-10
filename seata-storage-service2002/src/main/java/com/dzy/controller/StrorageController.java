package com.dzy.controller;

import com.dzy.pojo.CommonResult;
import com.dzy.service.StorageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class StrorageController {

    @Resource
    StorageService storageService;

    @PostMapping("/storage/decrease")
    public CommonResult decrease (@RequestParam("productId") Long productId, @RequestParam("count") Integer count) {
        storageService.decrease(productId, count);
        return new CommonResult(200, "扣减库存成功");
    }
}
