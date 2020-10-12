package org.example.utitlity.modal;//package org.example.product.productApi.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Component;

@ConditionalOnProperty({
        "dataSourceClassName",
        "dataSource.url",
        "entity.basescan"
})
@Component
public class DBModalConfig {
    @Value("${dataSourceClassName}")
    private String dataSourceClassName;

    @Value("${dataSource.url}")
    private String url;

    @Value("${datasource.username:sa}")
    private String username;

    @Value("${datasource.password:password}")
    private String password;

    @Value("${datasource.cachePrepStmts:true}")
    private String cachePrepStmts;

    @Value("${datasource.prepStmtCacheSize:250}")
    private String prepStmtCacheSize;

    @Value("${datasource.prepStmtCacheSqlLimit:2048}")
    private String prepStmtCacheSqlLimit;

    @Value("${entity.basescan}")
    private String entityScan;

    public String getDataSourceClassName() {
        return dataSourceClassName;
    }

    public void setDataSourceClassName(String dataSourceClassName) {
        this.dataSourceClassName = dataSourceClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCachePrepStmts() {
        return cachePrepStmts;
    }

    public void setCachePrepStmts(String cachePrepStmts) {
        this.cachePrepStmts = cachePrepStmts;
    }

    public String getPrepStmtCacheSize() {
        return prepStmtCacheSize;
    }

    public void setPrepStmtCacheSize(String prepStmtCacheSize) {
        this.prepStmtCacheSize = prepStmtCacheSize;
    }

    public String getPrepStmtCacheSqlLimit() {
        return prepStmtCacheSqlLimit;
    }

    public void setPrepStmtCacheSqlLimit(String prepStmtCacheSqlLimit) {
        this.prepStmtCacheSqlLimit = prepStmtCacheSqlLimit;
    }

    public String getEntityScan() {
        return entityScan;
    }

    public void setEntityScan(String entityScan) {
        this.entityScan = entityScan;
    }

    @Override
    public String toString() {
        return "DBModalConfig{" +
                "dataSourceClassName='" + dataSourceClassName + '\'' +
                ", url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", cachePrepStmts='" + cachePrepStmts + '\'' +
                ", prepStmtCacheSize='" + prepStmtCacheSize + '\'' +
                ", prepStmtCacheSqlLimit='" + prepStmtCacheSqlLimit + '\'' +
                ", entityScan='" + entityScan + '\'' +
                '}';
    }
}
