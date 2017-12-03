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
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(
        basePackages = "com.max.dbconnection.mapper.B",
//        basePackageClasses = TestBMapper.class,
        sqlSessionFactoryRef = "ssfB",
        sqlSessionTemplateRef = "sqlSessionTemB")
public class BConfig {

    @Bean("datasourceB")
    @Qualifier("datasourceB")
    @ConfigurationProperties(prefix = "spring.datasource.other")
    public DataSource datasourceB(){

        return DataSourceBuilder.create().driverClassName("com.mysql.jdbc.Driver").build();
    }

    @Bean("txB")
    @Qualifier("txB")
    public DataSourceTransactionManager txB(@Qualifier("datasourceB")DataSource ds){
        return new DataSourceTransactionManager(ds);
    }

    @Bean("jdbcTemB")
    public JdbcTemplate jdbcTemB(@Qualifier("datasourceB")DataSource ds){
        return new JdbcTemplate(ds);
    }


    @Bean("ssfB")
    @Qualifier("ssfB")
    public SqlSessionFactory ssfB(@Qualifier("datasourceB") DataSource ds) throws Exception{
        SqlSessionFactoryBean ssf = new SqlSessionFactoryBean();
        ssf.setDataSource(ds);
        ssf.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:mapper/testB.xml"));
        return ssf.getObject();
    }

    @Bean
    @Qualifier("sqlSessionTemB")
    public SqlSessionTemplate sqlSessionTemB(@Qualifier("ssfB")SqlSessionFactory ssf){

        System.out.println("sqlSessionTemB");
        System.out.println(ssf);
        return new SqlSessionTemplate(ssf);
    }

}
