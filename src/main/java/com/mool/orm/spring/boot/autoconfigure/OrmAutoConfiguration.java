package com.mool.orm.spring.boot.autoconfigure;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.moolbuy.orm.dao.EntityManagerConfig;
import com.moolbuy.orm.dao.support.IdGenerator;
import com.moolbuy.orm.dao.support.IdWorker;
import com.moolbuy.orm.dialect.Dialect;
import com.moolbuy.orm.dialect.MySQLDialect;
import com.moolbuy.xsqlbuilder.SafeSqlProcesser;
import com.moolbuy.xsqlbuilder.safesql.EscapeBackslashAndSingleQuotesSafeSqlProcesser;


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
