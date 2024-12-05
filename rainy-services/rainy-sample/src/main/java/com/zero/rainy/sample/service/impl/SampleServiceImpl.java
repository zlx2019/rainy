package com.zero.rainy.sample.service.impl;

import com.zero.rainy.core.entity.Sample;
import com.zero.rainy.core.pojo.Result;
import com.zero.rainy.db.ext.service.SuperServiceImpl;
import com.zero.rainy.sample.mapper.SampleMapper;
import com.zero.rainy.sample.service.ISampleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Zero.
 * <p> Created on 2024/9/20 10:35 </p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SampleServiceImpl extends SuperServiceImpl<SampleMapper, Sample> implements ISampleService {
    private final SampleMapper sampleMapper;
}
