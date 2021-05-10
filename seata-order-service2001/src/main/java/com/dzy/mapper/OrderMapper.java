package com.dzy.mapper;

import com.dzy.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderMapper {
    int create(Order order);
    int updateStatus(@Param("id") Long id, @Param("status")Integer status);
}
