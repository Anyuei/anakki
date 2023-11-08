package com.anakki.data.utils.common;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;
import java.util.Scanner;
public class CodeGenerator {
    /**
     * <p>
     * MySQL 生成演示
     * </p>
     */
    public static void main(String[] args) {
        String PROJECT_DIR = "C:\\Users\\1\\Desktop\\gencode\\";
        // 数据库设置
        String URL = "jdbc:mysql://49.232.11.30:3306/securex?autoReconnect=true&useServerPreparedStmts=false&rewriteBatchedStatements=true&characterEncoding=UTF-8&useSSL=false&allowMultiQueries=true&serverTimezone=UTC";
        String USERNAME = "root";
        String PASSWORD = "FengXiang_123";
        FastAutoGenerator.create(URL, USERNAME, PASSWORD)
                // 全局配置
                .globalConfig(builder -> {
                    builder.author("Anakki") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .disableOpenDir()   // 不打开目录
                            .outputDir(PROJECT_DIR + "src/main/java"); // 指定输出目录
                })
                // 包配置
                .packageConfig(builder -> {
                    builder.parent("com.securex") // 设置父包名
                            .moduleName("securex-manager") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, PROJECT_DIR + "src/main/resources/mapper/")); // 设置mapperXml生成路径
                })
                // 策略配置
                .strategyConfig(builder -> {
                    builder.addInclude("sx_user_data_source_token") // 设置需要生成的表名
                            .addTablePrefix("") // 设置过滤表前缀
                            // Entity策略配置
                            .entityBuilder()
                            .enableLombok() // 开启 lombok 模型
//                            .logicDeleteColumnName("")    //  逻辑删除字段名(数据库)
//                            .logicDeletePropertyName("")  //  逻辑删除属性名(实体)
                            .enableTableFieldAnnotation() //  开启生成实体时生成字段注解
                            // Controller策略配置
                            .controllerBuilder()
                            .enableRestStyle() // 开启生成 @RestController 控制器
                            // Service策略配置
                            .serviceBuilder()
                            .formatServiceFileName("%sService")
                            .formatServiceImplFileName("%sServiceImpl")
                            // Mapper策略配置
                            .mapperBuilder()
                            .enableMapperAnnotation()   // 开启 @Mapper 注解
                    ;
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

    /**
     * 读取控制台内容
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "（多个表用 , 分割）：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

}

