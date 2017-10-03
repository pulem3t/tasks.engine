package org.pulem3t.tasks.config;

import java.beans.PropertyVetoException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.mchange.v2.c3p0.AbstractComboPooledDataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@PropertySource(value = { "classpath:config.properties" })
public class CrateDBConfig {
	
	@Autowired
	private Environment env;
	private Logger logger = Logger.getLogger(CrateDBConfig.class);
	private NamedParameterJdbcTemplate npjt;
	
	private AbstractComboPooledDataSource getDataSource() throws IllegalStateException, PropertyVetoException {
		AbstractComboPooledDataSource dataSource = new ComboPooledDataSource();
		//TODO nulls everywhere
//		dataSource.setDriverClass(env.getProperty("crateDB.datasource.driver"));
//		dataSource.setJdbcUrl(env.getProperty("crateDB.datasource.url"));
//		dataSource.setUser(env.getProperty("crateDB.datasource.username"));
//		dataSource.setPassword(env.getProperty("crateDB.datasource.password"));
		//TODO temp code
		dataSource.setDriverClass("io.crate.client.jdbc.CrateDriver");
		dataSource.setJdbcUrl("crate://localhost:5432/");
		dataSource.setUser("aaa");
		dataSource.setPassword("aaa");
		dataSource.setMaxPoolSize(50);
		dataSource.setMinPoolSize(10);
		dataSource.setMaxStatements(100);
		dataSource.setTestConnectionOnCheckout(true);
		return dataSource;
	}
	
	public NamedParameterJdbcTemplate getNpjt() {
		try {
			if(npjt == null) {
				npjt = new NamedParameterJdbcTemplate(this.getDataSource());
			}
		} catch (Exception e) {
			logger.error(null, e);
			return null;
		}
		return npjt;
	}
}
