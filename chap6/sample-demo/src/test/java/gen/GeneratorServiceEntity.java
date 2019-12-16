package gen;
import org.junit.Test;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

/**
 * <p>
 * 测试生成代码
 * </p>
 */
public class GeneratorServiceEntity {

	// 基础包名
	String basePackageName = "biz.demo";
	// 文件名中要去掉的表前缀
	String excludePrefix = "g_";
	// 模块和包名，与数据库中模块一致
	String[] moduleNames = { "ac", "item" };

	// 数据库连接信息
	String dbUrl = "jdbc:mysql://192.168.1.113:3306/mybatis-demo?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false";
	String dbUsername = "root";
	String dbPassword = "tianxia17!";
	String dbDriverName = "com.mysql.jdbc.Driver";
	// 生成文件暂存目录
	String outputDir = "C:\\data";// E:\\codeGen

	@Test
	public void generateCode() {
		boolean serviceNameStartWithI = false;// user -> UserService, 设置成true: user -> IUserService
		for (String mn : moduleNames) {
			generateByTables(mn, serviceNameStartWithI, basePackageName);
		}
	}

	private void generateByTables(String moduleName, boolean serviceNameStartWithI, String packageName,
			String... tableNames) {
		// 要生成的表前缀
		String tablePrefix = excludePrefix + moduleName;

		GlobalConfig config = new GlobalConfig();
		// 数据源连接
		DataSourceConfig dataSourceConfig = new DataSourceConfig();
		dataSourceConfig.setDbType(DbType.MYSQL).setUrl(dbUrl).setUsername(dbUsername).setPassword(dbPassword)
				.setDriverName(dbDriverName);
		// 代码生成策略配置
		StrategyConfig strategyConfig = new StrategyConfig();
		strategyConfig.setCapitalMode(true).setRestControllerStyle(true) // 指定为rest
				.setEntityLombokModel(true) // 生成的实体类是否使用lombok模式
				// .setDbColumnUnderline(true) // 列名下划线
				.setNaming(NamingStrategy.underline_to_camel) // 下划线转驼峰
				.setTablePrefix(excludePrefix); // 表前缀
//                .setInclude(tableNames); //修改替换成你需要的表名，多个表名传数组；此处用上一行的表前缀来过滤，所以不用这个
		config.setActiveRecord(true).setAuthor("轻量级SSM开发").setOutputDir(outputDir).setSwagger2(true).setFileOverride(true);
		if (!serviceNameStartWithI) {
			config.setServiceName("%sService");
		}
		TemplateConfig templateConfig = new TemplateConfig();
//        templateConfig.setController("/gen/controller.java"); 
//        templateConfig.setEntity("/gen/entity.java");
		// 使用自定义的生成器
		new MyGenerator().prefixFilter(tablePrefix) // 过滤，只有表前缀和指定值一致，才会生成
				.setGlobalConfig(config).setDataSource(dataSourceConfig)
				.setTemplateEngine(new FreemarkerTemplateEngine()).setStrategy(strategyConfig)
				.setTemplate(templateConfig).setPackageInfo(new PackageConfig().setParent(packageName) // 包名
						.setModuleName(moduleName) // 模块名
						.setController("controller").setEntity("entity"))
				.execute();
	}
}
