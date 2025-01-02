package com.zero.rainy.gen.service.impl;

import cn.hutool.core.io.IoUtil;
import com.zero.rainy.core.exception.BusinessException;
import com.zero.rainy.core.utils.AssertUtils;
import com.zero.rainy.core.utils.CloneUtils;
import com.zero.rainy.gen.mapper.CodeGeneratorMapper;
import com.zero.rainy.gen.model.dto.GenerateDTO;
import com.zero.rainy.gen.model.entity.Column;
import com.zero.rainy.gen.model.entity.Table;
import com.zero.rainy.gen.model.vo.TableVo;
import com.zero.rainy.gen.service.CodeGeneratorService;
import com.zero.rainy.gen.utils.GeneratorUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.zip.ZipOutputStream;

/**
 * @author Zero.
 * <p> Created on 2024/12/30 15:08 </p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CodeGeneratorServiceImpl implements CodeGeneratorService {
    private final CodeGeneratorMapper mapper;

    @Override
    public List<TableVo> queryAll() {
        List<Table> tables = mapper.selectAllTables(null);
        return CloneUtils.copyProperties(tables, TableVo.class);
    }

    @Override
    public byte[] generate(GenerateDTO dto) {
        String tableName = dto.getTables().getFirst();
        // 获取表信息
        List<Table> tables = mapper.selectAllTables(dto.getTables());
        AssertUtils.isTrue(tables.isEmpty(), new BusinessException("table does not exist"));

        // 创建Zip压缩包
        ByteArrayOutputStream outputStream = null;
        ZipOutputStream zip = null;
        try {
            outputStream = new ByteArrayOutputStream();
            zip = new ZipOutputStream(outputStream);
            for (Table tableInfo : tables) {
                // 获取列信息
                List<Column> columns = mapper.selectColumnsByTable(tableName);
                Table table = new Table();
                table.setTableName(tableInfo.getTableName());
                table.setColumns(columns);
                table.setComment(tableInfo.getComment());
                // 生成代码
                GeneratorUtils.generate(table, dto.getPackageName(), dto.getModuleName(), dto.getAuthor(), zip);
            }
            zip.finish();
            return outputStream.toByteArray();
        }catch (Exception e){
            throw new BusinessException("代码生成异常.", e);
        }finally {
            IoUtil.close(zip);
            IoUtil.close(outputStream);
        }
    }
}
