package com.btp.backend.configuration;


import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.cloud.service.relational.DataSourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;


import com.btp.backend.entities.Vendor;
import com.zaxxer.hikari.HikariDataSource;
import javax.persistence.EntityManagerFactory;
@Configuration
public class DatabaseConfig extends AbstractCloudConfig{
	
	Logger cloudFoundrtDataSourceConfigLogger = LoggerFactory.getLogger(this.getClass());
	
	@Value("{$vcap.services.Hana_Schema.credentials.user}")
	private String username;
	
	@Value("{$vcap.services.Hana_Schema.credentials.password}")
	private String password;
	
	@Value("{$vcap.services.Hana_Schema.credentials.url}")
	private String hostname;
	
	@Value("{$vcap.services.Hana_Schema.credentials.port}")
	private String port;
	
	@Value("{$vcap.services.Hana_Schema.credentials.schema}")
	private String schemaName;
	
	@Bean
	public DataSource dataSource() {
		
		List<String> dataSourceNames = Arrays.asList("BasicDbcpPooledDataSourceCreator",
													 "TomcatJdbcPooledDataSourceCreator",
													 "HikariCpPooledDataSourceCreator",
													 "TomcatDbcpPooledDataSourceCreator");
		DataSourceConfig dbConfig = new DataSourceConfig(dataSourceNames);
		//DataSource hikariDataSource = connectionFactory().dataSource(dbConfig);
		DataSource myConnection = DataSourceBuilder.create()
								  .type(HikariDataSource.class)
								  .driverClassName(com.sap.db.jdbc.Driver.class.getName())
								  .url(hostname)
								  .username(username)
								  .password(password)
								  .build();
		try {
			myConnection.getConnection().setSchema(schemaName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cloudFoundrtDataSourceConfigLogger.info("Detected Host name is : " + this.hostname);
		cloudFoundrtDataSourceConfigLogger.info("Detected Port name is : " + this.port);
		cloudFoundrtDataSourceConfigLogger.info("Detected DB name is : " + this.schemaName);
		cloudFoundrtDataSourceConfigLogger.info("Detected User name is : " + this.username);
		
		return myConnection;
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
