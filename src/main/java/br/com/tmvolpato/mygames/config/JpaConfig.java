package br.com.tmvolpato.mygames.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * JPA configuration class.
 *
 * @author Thiago Michel Volpato
 * @sice 2017
 * @version 1.0.0
 */
@Profile("dev")
@EnableTransactionManagement
@PropertySource({ "classpath:persistence-dev.properties" })
@EnableJpaRepositories({ "br.com.tmvolpato.mygames.repository" })
@Configuration
public class JpaConfig {

    public JpaConfig() {
        super();
    }

    @Autowired
    private Environment env;

    @Value("classpath:schema-oauth.sql")
    private Resource schemaOAuth2Script;

    @Value("classpath:schema-data.sql")
    private Resource schemaDataScript;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(this.dataSource());
        em.setPackagesToScan("br.com.tmvolpato.mygames.model");
        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(this.additionalProperties());
        return em;
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer() {
        final DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(this.dataSource());
        initializer.setDatabasePopulator(this.databasePopulator());
        return initializer;
    }

    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(this.env.getProperty("jdbc.driverClassName"));
        dataSource.setUrl(this.env.getProperty("jdbc.url"));
        dataSource.setUsername(this.env.getProperty("jdbc.username"));
        dataSource.setPassword(this.env.getProperty("jdbc.password"));
        return dataSource;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(this.entityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    private Properties additionalProperties() {
        final Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.show_sql", this.env.getProperty("hibernate.show-sql"));
        hibernateProperties.setProperty("hibernate.format_sql", this.env.getProperty("hibernate.format-sql"));
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", this.env.getProperty("hibernate.hbm2ddl.auto", "validate"));
        hibernateProperties.setProperty("hibernate.dialect", this.env.getProperty("hibernate.dialect"));
        hibernateProperties.setProperty("hibernate.jdbc.lob.non_contextual_creation", "true");
        return hibernateProperties;
    }

    private DatabasePopulator databasePopulator() {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(this.schemaOAuth2Script, this.schemaDataScript);
        return populator;
    }

}
