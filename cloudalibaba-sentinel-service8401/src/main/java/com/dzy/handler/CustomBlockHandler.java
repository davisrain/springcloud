package com.dzy.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.dzy.pojo.CommonResult;

public class CustomBlockHandler {

    public static CommonResult globalBlockHandler1(BlockException ex) {
        return new CommonResult(444, "global Block Handler 1");
    }

    public static CommonResult globalBlockHandler2(BlockException ex) {
        return new CommonResult(444, "global Block Handler 2");
    }
}
