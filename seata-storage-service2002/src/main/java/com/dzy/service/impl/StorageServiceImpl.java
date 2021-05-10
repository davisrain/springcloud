package com.dzy.service.impl;

import com.dzy.mapper.StorageMapper;
import com.dzy.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class StorageServiceImpl implements StorageService {

    @Resource
    StorageMapper storageMapper;
    @Override
    public int decrease(Long productId, Integer count) {

        return storageMapper.decrease(productId, count);
    }
}
