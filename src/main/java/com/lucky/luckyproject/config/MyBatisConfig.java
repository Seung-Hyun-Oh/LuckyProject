package com.lucky.luckyproject.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
// UserMapperÍ∞Ä ?ÑÏπò???®ÌÇ§ÏßÄÎ•??§Ï∫î?òÎèÑÎ°?Í≤ΩÎ°úÎ•??§Ï†ï?©Îãà??
@MapperScan("com.lucky.luckyproject.mapper")
public class MyBatisConfig {

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);

        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true); // snake_caseÎ•?camelCaseÎ°?Î≥Ä?òÌï®
        sessionFactory.setConfiguration(configuration);

        // DTO ?®ÌÇ§ÏßÄ Í≤ΩÎ°ú ?§Ï†ï (XML?êÏÑú ?¥Îûò?§Î™ÖÎß??????àÍ≤å ?¥Ï§å)
        sessionFactory.setTypeAliasesPackage("com.lucky.luckyproject.dto");

        Resource[] res = new PathMatchingResourcePatternResolver().getResources("classpath:mapper/**/*.xml");
        sessionFactory.setMapperLocations(res);
        return sessionFactory.getObject();
    }
}
