package com.zero.rainy.gen.service.impl;

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
        String tableName = dto.getName();
        List<Table> tables = mapper.selectAllTables(tableName);
        AssertUtils.isTrue(tables.isEmpty(), new BusinessException("table does not exist"));
        Table tableInfo = tables.getFirst();
        List<Column> columns = mapper.selectColumnsByTable(tableName);
        Table table = new Table();
        table.setTableName(tableInfo.getTableName());
        table.setColumns(columns);
        table.setComment(tableInfo.getComment());

        // 创建Zip
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        GeneratorUtils.generate(table, dto.getPackageName(), dto.getModule(), dto.getAuthor(), zip);
        return outputStream.toByteArray();
    }
}
