package com.btp.backend.configuration;


import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.cloud.service.relational.DataSourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;


import com.btp.backend.entities.Vendor;

import javax.persistence.EntityManagerFactory;

@Configuration
public class DatabaseConfig extends AbstractCloudConfig{
	
	Logger cloudFoundrtDataSourceConfigLogger = LoggerFactory.getLogger(this.getClass());
	
	@Value("{$vcap.services.mysql.credentials.username}")
	private String username;
	
	@Value("{$vcap.services.mysql.credentials.password}")
	private String password;
	
	@Value("{$vcap.services.mysql.credentials.hostname}")
	private String hostname;
	
	@Value("{$vcap.services.mysql.credentials.port}")
	private String port;
	
	@Value("{$vcap.services.mysql.credentials.dbname}")
	private String dbname;
	
	@Bean
	public DataSource dataSource() {
		
		List<String> dataSourceNames = Arrays.asList("BasicDbcpPooledDataSourceCreator",
													 "TomcatJdbcPooledDataSourceCreator",
													 "HikariCpPooledDataSourceCreator",
													 "TomcatDbcpPooledDataSourceCreator");
		DataSourceConfig dbConfig = new DataSourceConfig(dataSourceNames);
		DataSource hikariDataSource = connectionFactory().dataSource(dbConfig);
		
		cloudFoundrtDataSourceConfigLogger.info("Detected Host name is : " + this.hostname);
		cloudFoundrtDataSourceConfigLogger.info("Detected Port name is : " + this.port);
		cloudFoundrtDataSourceConfigLogger.info("Detected DB name is : " + this.dbname);
		cloudFoundrtDataSourceConfigLogger.info("Detected User name is : " + this.username);
		
		return hikariDataSource;
	}
	
	@Bean(name = "entityManagerFactory" )
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
		
//		return EntityManagerFactoryProvider.get
		return EntityManagerFactoryProvider.get(dataSource, Vendor.class.getPackage().getName());
	}
	
	@Bean(name= "transactionManager")
	public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}
