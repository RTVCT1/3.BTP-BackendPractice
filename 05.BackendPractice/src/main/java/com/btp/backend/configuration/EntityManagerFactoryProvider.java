package com.btp.backend.configuration;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.eclipse.persistence.jpa.PersistenceProvider;
import org.springframework.instrument.classloading.SimpleLoadTimeWeaver;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;

public class EntityManagerFactoryProvider {

	public static LocalContainerEntityManagerFactoryBean get(DataSource dataSource,String packagesToScan) {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setPersistenceProvider(new PersistenceProvider());
		entityManagerFactoryBean.setPackagesToScan(packagesToScan);
		entityManagerFactoryBean.setDataSource(dataSource);
		//need to implement
		//dataSource.getClass().getName(),dataSource.getClass()
		entityManagerFactoryBean.setJpaPropertyMap(getJPAProperties(dataSource.getClass().getClassLoader()));
		entityManagerFactoryBean.setLoadTimeWeaver(new SimpleLoadTimeWeaver());
		entityManagerFactoryBean.setJpaVendorAdapter(new EclipseLinkJpaVendorAdapter());
		
		entityManagerFactoryBean.afterPropertiesSet();
		return entityManagerFactoryBean;
	}
	private static Map<String,Object> getJPAProperties(ClassLoader classLoader){
		Map<String,Object> properties = new HashMap<>();
		
		properties.put(org.eclipse.persistence.config.PersistenceUnitProperties.DDL_GENERATION, org.eclipse.persistence.config.PersistenceUnitProperties.CREATE_OR_EXTEND);
		properties.put(org.eclipse.persistence.config.PersistenceUnitProperties.DDL_GENERATION_MODE, org.eclipse.persistence.config.PersistenceUnitProperties.DDL_DATABASE_GENERATION);
		properties.put(org.eclipse.persistence.config.PersistenceUnitProperties.CLASSLOADER, classLoader);
		properties.put(org.eclipse.persistence.config.PersistenceUnitProperties.LOGGING_LEVEL, "INFO");
		properties.put(org.eclipse.persistence.config.PersistenceUnitProperties.CACHE_SHARED_DEFAULT, "false");
		properties.put(org.eclipse.persistence.config.PersistenceUnitProperties.CONNECTION_POOL_MAX, 50);
		
		return properties; 
	}
}
