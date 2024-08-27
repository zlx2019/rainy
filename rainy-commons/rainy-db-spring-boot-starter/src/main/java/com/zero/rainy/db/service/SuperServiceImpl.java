package com.zero.rainy.db.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * 通用 Service {@link ISuperService} 实现类. 实现通用扩展API
 *
 * @author Zero.
 * <p> Created on 2024/8/27 18:54 </p>
 */
@Slf4j
public class SuperServiceImpl<M extends BaseMapper<T>,T> extends ServiceImpl<M,T> implements ISuperService<T> {

}
