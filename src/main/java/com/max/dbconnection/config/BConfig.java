package com.max.dbconnection.config;

import com.max.dbconnection.repository.B.BRepository;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@MapperScan(
        basePackages = "com.max.dbconnection.mapper.B",
//        basePackageClasses = TestBMapper.class,
        sqlSessionFactoryRef = "ssfB",
        sqlSessionTemplateRef = "sqlSessionTemB")
@EnableJpaRepositories(
        basePackageClasses = {BRepository.class},
        entityManagerFactoryRef = "entityManagerB",
        transactionManagerRef = "jpaTxB"
)
//@EntityScan(basePackageClasses = {com.max.dbconnection.entity.B.Testb.class})
public class BConfig {

    @Autowired
    private Environment env;


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
        return new SqlSessionTemplate(ssf);
    }

    @Bean
    @Qualifier("entityManagerB")
    public LocalContainerEntityManagerFactoryBean entityManagerB(@Qualifier("datasourceB")DataSource ds,
                                                                 EntityManagerFactoryBuilder builder){
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto",
                env.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect",
                env.getProperty("hibernate.dialect"));
        return builder.dataSource(ds)
                .packages(com.max.dbconnection.entity.B.Testb.class)
                .properties(properties)
                .persistenceUnit("default")
                .build();

    }

    @Bean
    @Autowired
    public PlatformTransactionManager jpaTxB(
            @Qualifier("entityManagerB")LocalContainerEntityManagerFactoryBean em){
        return new JpaTransactionManager(em.getObject());

    }

}
