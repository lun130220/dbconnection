package com.max.dbconnection.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(
        basePackages = "com.max.dbconnection.mapper.A",
//        basePackageClasses = TestAMapper.class,
        sqlSessionFactoryRef = "ssfA", sqlSessionTemplateRef = "sqlSessionTemA")
public class AConfig {
    @Bean("datasourceA")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource datasourceA(){
        return DataSourceBuilder.create().build();
    }

    @Bean("txA")
    @Primary
    public DataSourceTransactionManager txA(@Qualifier("datasourceA")DataSource ds){
        return new DataSourceTransactionManager(ds);
    }


    @Bean("jdbcTemA")
    @Primary
    public JdbcTemplate jdbcTemA(@Qualifier("datasourceA")DataSource ds){
        return new JdbcTemplate(ds);
    }

    @Bean("ssfA")
    @Qualifier("ssfA")
    @Primary
    public SqlSessionFactory ssfA(@Qualifier("datasourceA")DataSource ds) throws Exception {
        SqlSessionFactoryBean ssf = new SqlSessionFactoryBean();
        ssf.setDataSource(ds);
        return ssf.getObject();
    }

    @Bean("sqlSessionTemA")
    @Primary
    public SqlSessionTemplate sqlSessionTemA(@Qualifier("ssfA")SqlSessionFactory ssf){
        System.out.println("sqlSessionTemA");
        System.out.println(ssf);
        return new SqlSessionTemplate(ssf);
    }
}
