package com.max.dbconnection.config;

import com.max.dbconnection.repository.A.ARepository;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@MapperScan(
        basePackages = "com.max.dbconnection.mapper.A",
//        basePackageClasses = TestAMapper.class,
        sqlSessionFactoryRef = "ssfA", sqlSessionTemplateRef = "sqlSessionTemA")
@EnableJpaRepositories(
        basePackageClasses = {ARepository.class},
        entityManagerFactoryRef = "emA",
        transactionManagerRef = "jpaTxA"
)
//@EntityScan(basePackages={"com.max.dbconnection.entity.jpa.a"})
public class AConfig {

    @Autowired
    private Environment env;

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

    @Bean
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
        return new SqlSessionTemplate(ssf);
    }

    @Bean
    @Qualifier("emA")
    @Primary
    public LocalContainerEntityManagerFactoryBean emA(@Qualifier("datasourceA")DataSource ds,
                                                      EntityManagerFactoryBuilder builder){
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

        em.setDataSource(ds);
        em.setPackagesToScan(com.max.dbconnection.entity.A.Testa.class.getPackage().getName());
        em.setJpaVendorAdapter(adapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto",
                env.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect",
                env.getProperty("hibernate.dialect"));
        em.setJpaPropertyMap(properties);
        return em;
    }

    @Bean
    @Primary
    public PlatformTransactionManager jpaTxA(
            @Qualifier("emA") LocalContainerEntityManagerFactoryBean em){
        return new JpaTransactionManager(em.getObject());

    }
}
