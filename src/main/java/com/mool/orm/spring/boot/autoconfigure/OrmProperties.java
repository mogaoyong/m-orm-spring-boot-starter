package com.mool.orm.spring.boot.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "spring.mool.orm")
public class OrmProperties {
	
    private String sqlGeneratorClass;  
    
    private Long datacenterId; 
    
    private Long workerId;
    
	public String getSqlGeneratorClass() {
		return sqlGeneratorClass;
	}
	public void setSqlGeneratorClass(String sqlGeneratorClass) {
		this.sqlGeneratorClass = sqlGeneratorClass;
	}
	public Long getDatacenterId() {
		return datacenterId;
	}
	public void setDatacenterId(Long datacenterId) {
		this.datacenterId = datacenterId;
	}
	public Long getWorkerId() {
		return workerId;
	}
	public void setWorkerId(Long workerId) {
		this.workerId = workerId;
	} 
	
}
