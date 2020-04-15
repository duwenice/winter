package com.dw.winter.utils;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

/**
 * @author duwen
 * @date 2020/4/15
 */
public class MybatisPlusGenerator {

    public static void main(String[] args) {
        AutoGenerator autoGenerator = new AutoGenerator();
        // 设置数据源
        DataSourceConfig dateSource = new DataSourceConfig();
        String mysql5DriveName = "com.mysql.jdbc.Driver";
        String mysql6DriveName = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://cdb-7f9cr572.cd.tencentcdb.com:10092/winter?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&tinyInt1isBit=false";
        dateSource.setDriverName(mysql5DriveName);
        dateSource.setUrl(url);
        dateSource.setUsername("root");
        dateSource.setPassword("@12345678");
        autoGenerator.setDataSource(dateSource);

        autoGenerator.setDataSource(dateSource);

        // 策略配置项
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setTablePrefix("winter");
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setSuperEntityColumns("id", "remark", "create_time", "update_time", "is_deleted");
        strategyConfig.setEntityLombokModel(true);
        strategyConfig.setEntitySerialVersionUID(false);
        strategyConfig.setLogicDeleteFieldName("is_deleted");
        strategyConfig.setSuperEntityClass("com.dw.winter.commom.base.BaseEntity");
        strategyConfig.setInclude("winter_order");

        autoGenerator.setStrategy(strategyConfig);

        // 跟包相关的配置项
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.dw.winter");
        packageConfig.setEntity("biz.order.entity");
        packageConfig.setMapper("biz.order.mapper");
        packageConfig.setXml("biz.order.mapper");
        packageConfig.setService("biz.order.service");
        packageConfig.setServiceImpl("biz.order.service.impl");
        packageConfig.setController("biz.order.controller");

        autoGenerator.setPackageInfo(packageConfig);

        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setBaseResultMap(true);
        globalConfig.setBaseColumnList(true);
        globalConfig.setFileOverride(true);
        globalConfig.setAuthor("duwen");

        autoGenerator.setGlobalConfig(globalConfig);

        // 模板引擎
        AbstractTemplateEngine freemarkerTemplateEngine = new FreemarkerTemplateEngine();
        autoGenerator.setTemplateEngine(freemarkerTemplateEngine);

        autoGenerator.execute();
    }
}
