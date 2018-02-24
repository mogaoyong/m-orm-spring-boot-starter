package com.mool.orm.spring.boot.autoconfigure;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mool.orm.dao.EntityManagerConfig;
import com.mool.orm.dao.support.IdGenerator;
import com.mool.orm.dao.support.IdWorker;
import com.mool.orm.dialect.Dialect;
import com.mool.orm.dialect.MySQLDialect;
import com.mool.xsqlbuilder.SafeSqlProcesser;
import com.mool.xsqlbuilder.safesql.EscapeBackslashAndSingleQuotesSafeSqlProcesser;


@Configuration
@EnableConfigurationProperties({OrmProperties.class})
public class OrmAutoConfiguration {
	
	@Autowired
	private DataSource dataSource; 
	
	private OrmProperties ormProperties;
	
	public OrmAutoConfiguration(OrmProperties ormProperties){
		this.ormProperties = ormProperties;
	}
	
	@Bean  
    public EntityManagerConfig entityManagerConfig() {  
		
		SafeSqlProcesser safeSqlProcesser = new EscapeBackslashAndSingleQuotesSafeSqlProcesser();
		
		IdGenerator idGenerator = new IdGenerator();
		idGenerator.setDatacenterId(ormProperties.getDatacenterId());
		idGenerator.setWorkerId(ormProperties.getWorkerId());
		IdWorker idWorker = new IdWorker(ormProperties.getWorkerId(), ormProperties.getDatacenterId());
		idGenerator.setIdWorker(idWorker);
		Dialect dialect = new MySQLDialect();
		dialect.setSqlGeneratorClass(ormProperties.getSqlGeneratorClass());
		dialect.setSafeSqlProcesser(safeSqlProcesser);
		
		EntityManagerConfig entityManagerConfig = new EntityManagerConfig(); 
		entityManagerConfig.setDataSource(dataSource);
		entityManagerConfig.setDialect(dialect);
		entityManagerConfig.setIdGenerator(idGenerator);
        return entityManagerConfig;  
    }  
}
