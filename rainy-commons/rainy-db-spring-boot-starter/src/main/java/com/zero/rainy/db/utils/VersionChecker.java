package com.zero.rainy.db.utils;

import com.baomidou.mybatisplus.annotation.Version;
import lombok.Getter;

import java.lang.reflect.Field;

/**
 * @author Zero.
 * <p> Created on 2024/9/20 14:13 </p>
 */
public class VersionChecker<T> {
    private final Class<T> type;
    @Getter
    private Field versionField;
    public VersionChecker(Class<T> type){
        this.type = type;
    }
    public boolean hasVersion(){
        Field[] fields = type.getDeclaredFields();
        for(Field field : fields){
            if (field.isAnnotationPresent(Version.class)){
                this.versionField = field;
                return true;
            }
        }
        return false;
    }
}
