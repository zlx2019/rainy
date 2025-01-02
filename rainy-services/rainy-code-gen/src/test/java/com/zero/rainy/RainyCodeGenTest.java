package com.zero.rainy;

import com.zero.rainy.gen.mapper.CodeGeneratorMapper;
import com.zero.rainy.gen.model.entity.Column;
import com.zero.rainy.gen.model.entity.Table;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author Zero.
 * <p> Created on 2024/12/30 15:49 </p>
 */
@SpringBootTest(classes = RainyCodeGenApplication.class)
public class RainyCodeGenTest {
    @Autowired
    private CodeGeneratorMapper mapper;

    @Test
    public void test(){
        List<Table> tableInfos = mapper.selectAllTables(List.of("config"));
        tableInfos.forEach(System.out::println);
        Assertions.assertFalse(tableInfos.isEmpty());

        List<Column> columnBos = mapper.selectColumnsByTable("config");
        columnBos.forEach(System.out::println);
        Table table = tableInfos.getFirst();
        table.setColumns(columnBos);
//        GeneratorUtils.generate(table, "system", "Zero.");
    }
}
