package com.zero.rainy.gen.constant;

/**
 * @author Zero.
 * <p> Created on 2024/12/30 18:28 </p>
 */
public interface ModelConstant {
    String PACKAGE = "package";    // 基础包名
    String MODULE = "moduleName";  // 模块名
    String AUTHOR = "author";      // 创建人
    String CREATED_AT = "createdAt"; // 创建时间

    // 模板
    String TEMPLATE_PATH = "templates/";
    String FILE_NAME_MODEL = "Model.java.vm";
    String FILE_NAME_MAPPER = "Mapper.java.vm";
    String FILE_NAME_MAPPER_XML = "Mapper.xml.vm";
    String FILE_NAME_SERVICE = "Service.java.vm";
    String FILE_NAME_SERVICE_IMPL = "ServiceImpl.java.vm";
    String FILE_NAME_CONTROLLER = "Controller.java.vm";


    String TABLE_PREFIX = "tablePrefix";
    String UNKNOWN_TYPE = "unknownType";
    String PRIMARY_KEY = "PRI";

}
