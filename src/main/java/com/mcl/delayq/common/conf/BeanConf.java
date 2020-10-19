package com.mcl.delayq.common.conf;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * @auth caiguowei
 * @date 2020/10/19
 */
@Configuration
public class BeanConf {

    /**
     * 对数据库公共字段填写
     * @return
     */
    @Bean
    public MetaObjectHandler metaObjectHandler(){

        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                Object createTime = getFieldValByName("createTime", metaObject);
                if (createTime == null){
                    setFieldValByName("createTime",new Date(),metaObject);
                }
                updateFill(metaObject);
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                Object updateTime = getFieldValByName("updateTime", metaObject);
                if (updateTime == null){
                    setFieldValByName("updateTime",new Date(),metaObject);
                }
            }
        };
    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor page = new PaginationInterceptor();
        page.setCountSqlParser(new JsqlParserCountOptimize(true));
        page.setDialectType("mysql");
        return page;
    }
}
