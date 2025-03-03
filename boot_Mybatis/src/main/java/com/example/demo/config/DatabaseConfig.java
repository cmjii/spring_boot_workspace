package com.example.demo.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
// 리소스 안에있는 properties 사용 할게요
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
@PropertySource("classpath:/application.properties") 
@Configuration
public class DatabaseConfig {
	@Autowired
	private ApplicationContext applicationcontext;
//	properties소스에서 가져오는 value값
	@Value("${mybatis.mapper-locations}")
	private String mapperLocations;
	
//	properties에 있는 데이터소스
	@ConfigurationProperties(prefix="spring.datasource")
	@Bean
	 HikariConfig hikariconfig() {
		return new HikariConfig();
	}
	
	@Bean
	 org.apache.ibatis.session.Configuration mybatisConfig(){
		return new org.apache.ibatis.session.Configuration();
	}
	
	@Bean
	 DataSource dataSource() throws Exception{
		DataSource dataSource = new HikariDataSource(hikariconfig());
		return dataSource;
	}
	
	@Bean
	 SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception{
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean(); //sqlSession객체 생성
		sqlSessionFactoryBean.setDataSource(dataSource); //datasource 정보추가
		
		sqlSessionFactoryBean.setConfigLocation(applicationcontext.getResource("classpath:/mappers/mybatis-config.xml"));
		sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
		
		return sqlSessionFactoryBean.getObject();
	}
	
	@Bean
	 SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
}
