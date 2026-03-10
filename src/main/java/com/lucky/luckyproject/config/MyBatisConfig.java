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
// UserMapper가 위치한 패키지를 스캔하도록 경로를 설정합니다.
@MapperScan("com.concentrix.lgintegratedadmin.mapper")
public class MyBatisConfig {

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);

        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true); // snake_case를 camelCase로 변환함
        sessionFactory.setConfiguration(configuration);

        // DTO 패키지 경로 설정 (XML에서 클래스명만 쓸 수 있게 해줌)
        sessionFactory.setTypeAliasesPackage("com.concentrix.lgintegratedadmin.dto");

        Resource[] res = new PathMatchingResourcePatternResolver().getResources("classpath:mapper/**/*.xml");
        sessionFactory.setMapperLocations(res);
        return sessionFactory.getObject();
    }
}