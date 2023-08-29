package org.liaimei.podcast.player.utils;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;


public class GeneratorUtils {





    public static void main(String[] args) {

        DataSourceConfig dataSourceConfig = new DataSourceConfig.Builder("jdbc:sqlite:D:/podcast/mydatabase.db", "", "")
                .dbQuery(new MySqlQuery())
//                .schema("mybatis-plus")
                .typeConvert(new MySqlTypeConvert())
                .keyWordsHandler(new MySqlKeyWordsHandler())
                .build();

        GlobalConfig globalConfig = new GlobalConfig.Builder()
                .fileOverride()
                .disableOpenDir()
                .outputDir("D://mbp-gen")
                .author("lijiashu")
//                .enableKotlin()
                .enableSwagger()
                .dateType(DateType.ONLY_DATE)
//                .commentDate("yyyy-MM-dd")
                .build();


        PackageConfig packageConfig = new PackageConfig.Builder()
                .parent("org.liaimei.podcast")
                .moduleName("player")
                .entity("domain")
                .service("service")
                .serviceImpl("service.impl")
                .mapper("mapper")
                .xml("xml")
                .controller("controller")
//                .pathInfo(Collections.singletonMap(OutputFile.xml, "D://mbp-gen"))
                .build();


        TemplateConfig templateConfig = new TemplateConfig.Builder()
                .disable(TemplateType.ENTITY)
                .entity("/templates/entity.java")
                .service("/templates/service.java")
                .serviceImpl("/templates/serviceImpl.java")
                .mapper("/templates/mapper.java")
                .xml("/templates/mapper.xml")
//                .controller("/templates/controller.java")
                .controller("/vm/java/controller.java.ext")
                .build();


        InjectionConfig injectionConfig = new InjectionConfig.Builder()
                .beforeOutputFile((tableInfo, objectMap) -> {
                    System.out.println("tableInfo: " + tableInfo.getEntityName() + " objectMap: " + objectMap.size());
                    objectMap.put("extControllerName","extControllerName");
                    objectMap.put("extControllerMoudleName","extControllerMoudleName");
                })
//                .customMap(Collections.singletonMap("test", "baomidou"))
//                .customFile(Collections.singletonMap("test.txt", "/templates/test.vm"))
                .build();


        StrategyConfig strategyConfig = new StrategyConfig.Builder()
                .enableCapitalMode()
                .enableSkipView()
                .disableSqlFilter()
//                .likeTable(new LikeTable("USER"))
                .addInclude("sys_user",
                        "sys_podcats",
                        "sys_episodes",
                        "sys_episodes_record",
                        "sys_user_statment",
                        "sys_user_vocabulary"
                        )
//                .addTablePrefix("t_", "c_")
//                .addFieldSuffix("_flag")

                //entityConfig
                .entityBuilder()
//                .superClass(BaseEntity.class)
                .disableSerialVersionUID()
                .enableChainModel()
                .enableLombok()
                .enableRemoveIsPrefix()
                .enableTableFieldAnnotation()
//                .enableActiveRecord()
//                .versionColumnName("version")
//                .versionPropertyName("version")
//                .logicDeleteColumnName("deleted")
//                .logicDeletePropertyName("deleteFlag")
                .naming(NamingStrategy.underline_to_camel)
                .columnNaming(NamingStrategy.underline_to_camel)
//                .addSuperEntityColumns("id", "created_by", "created_time", "updated_by", "updated_time")
//                .addIgnoreColumns("age")
//                .addTableFills(new Column("create_time", FieldFill.INSERT))
//                .addTableFills(new Property("updateTime", FieldFill.INSERT_UPDATE))
                .idType(IdType.AUTO)
//                .formatFileName("%sEntity")

                //controller
                .controllerBuilder()
//                .superClass(BaseController.class)
//                .enableHyphenStyle()
                .enableRestStyle()
                .formatFileName("%sController")

                //service
                .serviceBuilder()
//                .superServiceClass(BaseService.class)
//                .superServiceImplClass(BaseServiceImpl.class)
                .formatServiceFileName("%sService")
                .formatServiceImplFileName("%sServiceImp")

                //mapper
                .mapperBuilder()
//                .superClass(BaseMapper.class)
                .enableMapperAnnotation()
                .enableBaseResultMap()
                .enableBaseColumnList()
//                .cache(MyMapperCache.class)
                .formatMapperFileName("%sMapper")
                .formatXmlFileName("%sMapper")
                .build();


        new AutoGenerator(dataSourceConfig)
                // 全局配置
                .global(globalConfig)
                // 包配置
                .packageInfo(packageConfig)
                // 策略配置
                .strategy(strategyConfig)
                // 注入配置
                .injection(injectionConfig)
                // 模板配置
//                .template(templateConfig)
                // 执行
                .execute(new FreemarkerTemplateEngine());


    }
}
