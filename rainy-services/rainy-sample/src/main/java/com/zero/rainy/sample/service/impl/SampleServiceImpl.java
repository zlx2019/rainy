package com.zero.rainy.sample.service.impl;

import com.zero.rainy.core.exception.BusinessException;
import com.zero.rainy.core.exception.RecordNotFoundException;
import com.zero.rainy.core.model.entity.Sample;
import com.zero.rainy.db.ext.service.SuperServiceImpl;
import com.zero.rainy.sample.mapper.SampleMapper;
import com.zero.rainy.sample.model.converts.SampleConvert;
import com.zero.rainy.sample.model.dto.SampleDTO;
import com.zero.rainy.sample.model.vo.SampleVo;
import com.zero.rainy.sample.service.ISampleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author Zero.
 * <p> Created on 2024/9/20 10:35 </p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SampleServiceImpl extends SuperServiceImpl<SampleMapper, Sample> implements ISampleService {
    private final SampleMapper sampleMapper;
    private final SampleConvert convert;

    @Override
    public SampleVo updateById(SampleDTO dto) {
        if (Objects.isNull(super.getById(dto.getId()))){
            throw new RecordNotFoundException(String.valueOf(dto.getId()));
        }
        Sample entity = convert.toEntity(dto);
        if (super.updateById(entity)){
            return convert.toVo(dto);
        }
        throw new BusinessException();
    }
}
