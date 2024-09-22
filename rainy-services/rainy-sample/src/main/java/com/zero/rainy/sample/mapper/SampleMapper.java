package com.zero.rainy.sample.mapper;

import com.zero.rainy.core.entity.Sample;
import com.zero.rainy.db.mapper.SuperMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Zero.
 * <p> Created on 2024/9/20 10:31 </p>
 */
@Mapper
public interface SampleMapper extends SuperMapper<Sample> {

}
