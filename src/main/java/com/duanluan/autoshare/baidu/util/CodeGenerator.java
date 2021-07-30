package com.duanluan.autoshare.baidu.util;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.duanluan.autoshare.baidu.common.BaseController;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Scanner;
import java.util.function.BiConsumer;

/**
 * 代码生成器：https://github.com/baomidou/generator/blob/develop/mybatis-plus-generator/src/main/java/com/baomidou/mybatisplus/generator/SimpleAutoGenerator.java
 *
 * @author duanluan
 */
public class CodeGenerator {

  /**
   * 读取控制台输入内容
   */
  private static final Scanner scanner = new Scanner(System.in);

  /**
   * 控制台输入内容读取并打印提示信息
   *
   * @param message 提示信息
   * @return
   */
  public static String scannerNext(String message) {
    System.out.println(message);
    String nextLine = scanner.nextLine();
    if (StringUtils.isBlank(nextLine)) {
      // 如果输入空行继续等待
      return scanner.next();
    }
    return nextLine;
  }

  protected static <T> T configBuilder(IConfigBuilder<T> configBuilder) {
    return null == configBuilder ? null : configBuilder.build();
  }

  public static void main(String[] args) {
    // 代码生成器
    new AutoGenerator(configBuilder(new DataSourceConfig.Builder("jdbc:mariadb://csaf.club:3307/baidu_netdisk_auto_share", "root", "Qwe123!@#$%")))
      // 全局配置
      .global(configBuilder(new GlobalConfig.Builder()
        // 覆盖已生成文件，默认 false
        .fileOverride()
        // 是否打开生成目录，默认 true
        .openDir(false)
        // 输出目录，默认 windows: D://  linux or mac: /tmp
        .outputDir(System.getProperty("user.dir") + "/generator/src/main/java")
        // 作者，默认无
        .author("duanluan")
        // 注释时间（@since），默认 yyyy-MM-dd
        .commentDate("")
      ))
      // 包配置
      .packageInfo(configBuilder(new PackageConfig.Builder()
        // 模块名
        .moduleName("")
        // 父包名
        .parent("com.duanluan.autoshare.baidu")
      ))
      // 自定义配置
      .injection(configBuilder(new InjectionConfig.Builder()
        .beforeOutputFile(new BiConsumer<TableInfo, Map<String, Object>>() {
          @Override
          public void accept(TableInfo tableInfo, Map<String, Object> stringObjectMap) {
            // 自定义 Mapper XML 生成目录
            ConfigBuilder config = (ConfigBuilder)stringObjectMap.get("config");
            Map<String, String> pathInfoMap = config.getPathInfo();
            pathInfoMap.put("xml_path", pathInfoMap.get("xml_path").replaceAll("/java.*","/resources/mapper"));
            stringObjectMap.put("config", config);
          }
        })
      ))
      // 策略配置
      .strategy(configBuilder(new StrategyConfig.Builder()
        // 表名
        .addInclude(scannerNext("请输入表名（英文逗号分隔）：").split(","))

        // Entity 策略配置
        .entityBuilder()
        // 开启 Lombok 模式
        .enableLombok()
        // 开启生成 serialVersionUID
        .enableSerialVersionUID()
        // 数据库表映射到实体的命名策略：下划线转驼峰
        .naming(NamingStrategy.underline_to_camel)
        // 主键策略为自增，默认 IdType.AUTO
        .idType(IdType.ASSIGN_ID)

        // Controller 策略配置
        .controllerBuilder()
        // 生成 @RestController 注解
        .enableRestStyle()
        // 父类
        .superClass(BaseController.class)
      ))
      // 模板配置
      .template(configBuilder(new TemplateConfig.Builder()
        // 自定义模板：https://github.com/baomidou/generator/tree/develop/mybatis-plus-generator/src/main/resources/templates
        .entity("/templates/generator/entity.java")
        .mapper("/templates/generator/mapper.java")
        .service("/templates/generator/service.java", "/templates/generator/serviceImpl.java")
        .controller("/templates/generator/controller.java")
      ))
      // 执行并指定模板引擎
      .execute(new FreemarkerTemplateEngine());
  }
}
