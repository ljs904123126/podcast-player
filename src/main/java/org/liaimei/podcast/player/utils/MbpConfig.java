package org.liaimei.podcast.player.utils;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.Controller;
import com.baomidou.mybatisplus.generator.config.builder.Entity;
import com.baomidou.mybatisplus.generator.config.builder.Mapper;
import com.baomidou.mybatisplus.generator.config.builder.Service;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;

import javax.sql.DataSource;

public class MbpConfig {

    private String dbuser;
    private String dbpass;
    private String dburl;

    private DataSource dataSource;

    private DataSourceConfig.Builder dataSourceConfigBuilder;

    private GlobalConfig.Builder globalConfigBuilder;

    private PackageConfig.Builder packageConfigBuilder;

    private TemplateConfig.Builder templateConfigBuilder;

    private InjectionConfig.Builder injectionConfigBuilder;

    private StrategyConfig.Builder strategyConfigBuilder;


    public MbpConfig(String dburl, String dbuser, String dbpass) {
        this.dbuser = dbuser;
        this.dbpass = dbpass;
        this.dburl = dburl;
        dataSourceConfigBuilder = new DataSourceConfig.Builder(dburl, dbuser, dbpass);
        strategyConfigBuilder = new StrategyConfig.Builder();
        init();
    }

    public MbpConfig(DataSource dataSource) {
        this.dataSource = dataSource;
        dataSourceConfigBuilder = new DataSourceConfig.Builder(dataSource);
        strategyConfigBuilder = new StrategyConfig.Builder();
        init();
    }


    private void init() {
        dataSourceConfigBuilder
                .dbQuery(new MySqlQuery())
//                .schema("mybatis-plus")
                .typeConvert(new MySqlTypeConvert())
                .keyWordsHandler(new MySqlKeyWordsHandler());

        globalConfigBuilder = new GlobalConfig.Builder()
                .fileOverride()
                .disableOpenDir()
                .outputDir("D://mbp-gen")
                .author("lijiashu")
//                .enableKotlin()
                .enableSwagger()
                .dateType(DateType.ONLY_DATE)
//                .commentDate("yyyy-MM-dd")
        ;


        packageConfigBuilder = new PackageConfig.Builder()
                .parent("com.lawyee.deyice")
//                .moduleName("genTest")
                .entity("domain")
                .service("service")
                .serviceImpl("service.impl")
                .mapper("mapper")
                .xml("xml")
                .controller("controller")
//                .pathInfo(Collections.singletonMap(OutputFile.xml, "D://mbp-gen"))
        ;


//        templateConfigBuilder = new TemplateConfig.Builder()
//                .disable(TemplateType.ENTITY)
//                .entity("/templates/entity.java")
//                .service("/templates/service.java")
//                .serviceImpl("/templates/serviceImpl.java")
//                .mapper("/templates/mapper.java")
//                .xml("/templates/mapper.xml")
////                .controller("/templates/controller.java")
//                .controller("/vm/java/controller.java.ext")
        ;


        injectionConfigBuilder = new InjectionConfig.Builder()
                .beforeOutputFile((tableInfo, objectMap) -> {
                    System.out.println("tableInfo: " + tableInfo.getEntityName() + " objectMap: " + objectMap.size());
                    objectMap.put("extControllerName", "extControllerName");
                    objectMap.put("extControllerMoudleName", "extControllerMoudleName");
                })
//                .customMap(Collections.singletonMap("test", "baomidou"))
//                .customFile(Collections.singletonMap("test.txt", "/templates/test.vm"))
        ;


        strategyConfigBuilder.enableCapitalMode()
                .enableSkipView()
                .disableSqlFilter()
//                .likeTable(new LikeTable("USER"))
//                .addInclude("sys_oper_log")
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
                .formatServiceFileName("I%sService")
                .formatServiceImplFileName("%sServiceImpl")

                //mapper
                .mapperBuilder()
//                .superClass(BaseMapper.class)
                .enableMapperAnnotation()
                .enableBaseResultMap()
                .enableBaseColumnList()
//                .cache(MyMapperCache.class)
                .formatMapperFileName("%sMapper")
                .formatXmlFileName("%sMapper")
        ;
    }

    public DataSourceConfig.Builder getDataSourceConfigBuilder() {
        return dataSourceConfigBuilder;
    }

    public GlobalConfig.Builder getGlobalConfigBuilder() {
        return globalConfigBuilder;
    }

    public PackageConfig.Builder getPackageConfigBuilder() {
        return packageConfigBuilder;
    }

    public TemplateConfig.Builder getTemplateConfigBuilder() {
        return templateConfigBuilder;
    }

    public InjectionConfig.Builder getInjectionConfigBuilder() {
        return injectionConfigBuilder;
    }

    public StrategyConfig.Builder getStrategyConfigBuilder() {
        return strategyConfigBuilder;
    }

    //entityConfig
    public Entity.Builder entityBuilder() {
        return strategyConfigBuilder.entityBuilder();
    }

    //controller
    public Controller.Builder controllerBuilder() {
        return strategyConfigBuilder.controllerBuilder();
    }

    //service
    public Service.Builder serviceBuilder() {
        return strategyConfigBuilder.serviceBuilder();
    }

    //mapper
    public Mapper.Builder mapperBuilder() {
        return strategyConfigBuilder.mapperBuilder();
    }

    public AutoGenerator build() {
        return new AutoGenerator(dataSourceConfigBuilder.build())
                // 全局配置
                .global(globalConfigBuilder.build())
                // 包配置
                .packageInfo(packageConfigBuilder.build())
                // 策略配置
                .strategy(strategyConfigBuilder.build())
                // 注入配置
                .injection(injectionConfigBuilder.build())
                // 模板配置
                .template(templateConfigBuilder.build());
        // 执行
//                .execute(new FreemarkerTemplateEngine());

    }

}
