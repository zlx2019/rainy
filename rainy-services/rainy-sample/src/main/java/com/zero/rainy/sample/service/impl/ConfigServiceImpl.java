package com.zero.rainy.sample.service.impl;

import com.zero.rainy.core.entity.Config;
import com.zero.rainy.core.entity.Sample;
import com.zero.rainy.db.service.SuperServiceImpl;
import com.zero.rainy.sample.mapper.ConfigMapper;
import com.zero.rainy.sample.mapper.SampleMapper;
import com.zero.rainy.sample.service.IConfigService;
import com.zero.rainy.sample.service.ISampleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Zero.
 * <p> Created on 2024/9/20 10:35 </p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ConfigServiceImpl extends SuperServiceImpl<ConfigMapper, Config> implements IConfigService {

}
