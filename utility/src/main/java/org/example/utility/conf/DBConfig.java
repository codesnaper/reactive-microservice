package org.example.utility.conf;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.example.utility.modal.DBModalConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@ConditionalOnBean(DBModalConfig.class)
@Configuration
public class DBConfig {
    private HikariConfig hikariConfig = new HikariConfig();

    @Autowired
    private DBModalConfig dbConfigModel;

    @Autowired
    private DataSource dataSource;

    @Bean
    public DataSource getDataSource(){
        hikariConfig.setDriverClassName( dbConfigModel.getDataSourceClassName() );
        hikariConfig.setJdbcUrl( dbConfigModel.getUrl() );
        hikariConfig.setUsername( dbConfigModel.getUsername() );
        hikariConfig.setPassword( dbConfigModel.getPassword() );
        hikariConfig.addDataSourceProperty( "cachePrepStmts" , dbConfigModel.getCachePrepStmts() );
        hikariConfig.addDataSourceProperty( "prepStmtCacheSize" , dbConfigModel.getPrepStmtCacheSize() );
        hikariConfig.addDataSourceProperty( "prepStmtCacheSqlLimit" , dbConfigModel.getPrepStmtCacheSqlLimit() );
        return new HikariDataSource( hikariConfig );
    }


    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, Environment env) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan(dbConfigModel.getEntityScan());

        Properties jpaProperties = new Properties();

        jpaProperties.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
        jpaProperties.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("hibernate.hbm2ddl.auto"));
        jpaProperties.put("hibernate.ejb.naming_strategy", env.getRequiredProperty("hibernate.ejb.naming_strategy"));
        jpaProperties.put("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));
        jpaProperties.put("hibernate.format_sql", env.getRequiredProperty("hibernate.format_sql"));
        entityManagerFactoryBean.setJpaProperties(jpaProperties);
        return entityManagerFactoryBean;
    }


    @Bean
    JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}
