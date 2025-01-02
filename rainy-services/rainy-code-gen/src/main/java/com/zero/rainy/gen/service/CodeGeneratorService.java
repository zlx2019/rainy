package com.zero.rainy.gen.service;

import com.zero.rainy.gen.model.dto.GenerateDTO;
import com.zero.rainy.gen.model.vo.TableVo;

import java.util.List;

/**
 * @author Zero.
 * <p> Created on 2024/12/30 15:08 </p>
 */
public interface CodeGeneratorService {

    List<TableVo> queryAll();

    /**
     * 代码生成
     */
    byte[] generate(GenerateDTO dto);
}
