package com.roilka.www.datasource.druid;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;

@Configuration
@EnableConfigurationProperties({ DruidDataSourceProperties.class })
@MapperScan(value = { "com.roilka.www.*.dao" }, sqlSessionFactoryRef = "SqlSessionFactory")
@ConditionalOnProperty(name = "druid.datasource.url", matchIfMissing = false)
public class DruidDataSourceConfiguration {

    static final String                   MAPPER_LOCATION = "classpath*:sqlmap/**/*Mapper.xml";

    @Autowired
    private DruidDataSourceProperties druidDataSourceProperties;

    @Bean(name = "DruidDataSource", initMethod = "init", destroyMethod = "close")
    @ConditionalOnMissingBean(name = "DruidDataSource")
    public DruidDataSource DruidDataSourc() throws Exception {
        DruidDataSource result = new DruidDataSource();
        result.setName(druidDataSourceProperties.getName());
        result.setUrl(druidDataSourceProperties.getUrl());
        result.setUsername(druidDataSourceProperties.getUsername());
        result.setPassword(druidDataSourceProperties.getPassword());
        result.setConnectionProperties(
                "config.decrypt=true;config.decrypt.key=" + druidDataSourceProperties.getPwdPublicKey());
        result.setFilters("config");
        result.setMaxActive(druidDataSourceProperties.getMaxActive());
        result.setInitialSize(druidDataSourceProperties.getInitialSize());
        result.setMaxWait(druidDataSourceProperties.getMaxWait());
        result.setMinIdle(druidDataSourceProperties.getMinIdle());
        result.setTimeBetweenEvictionRunsMillis(druidDataSourceProperties.getTimeBetweenEvictionRunsMillis());
        result.setMinEvictableIdleTimeMillis(druidDataSourceProperties.getMinEvictableIdleTimeMillis());
        result.setValidationQuery(druidDataSourceProperties.getValidationQuery());
        result.setValidationQueryTimeout(druidDataSourceProperties.getValidationQueryTimeout());
        result.setTestWhileIdle(druidDataSourceProperties.isTestWhileIdle());
        result.setTestOnBorrow(druidDataSourceProperties.isTestOnBorrow());
        result.setTestOnReturn(druidDataSourceProperties.isTestOnReturn());
        result.setPoolPreparedStatements(druidDataSourceProperties.isPoolPreparedStatements());
        result.setMaxOpenPreparedStatements(druidDataSourceProperties.getMaxOpenPreparedStatements());

        if (druidDataSourceProperties.isEnableMonitor()) {
            StatFilter filter = new StatFilter();
            filter.setLogSlowSql(druidDataSourceProperties.isLogSlowSql());
            filter.setMergeSql(druidDataSourceProperties.isMergeSql());
            filter.setSlowSqlMillis(druidDataSourceProperties.getSlowSqlMillis());
            List<Filter> list = new ArrayList<Filter>();
            list.add(filter);
            result.setProxyFilters(list);
        }
        return result;
    }

    @Primary
    @Bean(name = "TransactionManager")
    @ConditionalOnMissingBean(name = "TransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("DruidDataSource") DruidDataSource druidDataSource) {
        return new DataSourceTransactionManager(druidDataSource);
    }

    @Bean(name = "TransactionTemplate")
    @ConditionalOnMissingBean(name = "TransactionTemplate")
    public TransactionTemplate transactionTemplate(@Qualifier("TransactionManager") PlatformTransactionManager platformTransactionManager) {
        return new TransactionTemplate(platformTransactionManager);
    }

    @Primary
    @Bean(name = "SqlSessionFactory")
    @ConditionalOnMissingBean(name = "SqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("DruidDataSource") DruidDataSource druidDataSource)
            throws Exception {
        final MybatisSqlSessionFactoryBean sessionFactory = new MybatisSqlSessionFactoryBean();
        sessionFactory.setDataSource(druidDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
        return sessionFactory.getObject();

    }
}
