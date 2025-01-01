package com.zero.rainy.sample.service;

import com.zero.rainy.core.model.entity.Sample;
import com.zero.rainy.db.ext.service.ISuperService;
import com.zero.rainy.sample.model.dto.SampleDTO;
import com.zero.rainy.sample.model.vo.SampleVo;

/**
 * @author Zero.
 * <p> Created on 2024/9/20 10:34 </p>
 */
public interface ISampleService extends ISuperService<Sample> {

    /**
     * 根据ID修改记录
     */
    SampleVo updateById(SampleDTO dto);
}
