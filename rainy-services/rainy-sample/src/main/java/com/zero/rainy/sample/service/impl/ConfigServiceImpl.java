package com.zero.rainy.sample.service.impl;

import com.zero.rainy.core.model.entity.Config;
import com.zero.rainy.db.ext.service.SuperServiceImpl;
import com.zero.rainy.sample.mapper.ConfigMapper;
import com.zero.rainy.sample.service.IConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Zero.
 * <p> Created on 2024/9/20 10:35 </p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ConfigServiceImpl extends SuperServiceImpl<ConfigMapper, Config> implements IConfigService {

}
