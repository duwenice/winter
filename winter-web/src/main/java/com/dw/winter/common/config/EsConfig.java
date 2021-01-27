package com.dw.winter.common.config;

import com.dw.winter.common.properties.EsProperties;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author duwen
 */
@Configuration
@EnableConfigurationProperties(EsProperties.class)
public class EsConfig {

    private final EsProperties esProperties;

    public EsConfig(EsProperties esProperties) {
        this.esProperties = esProperties;
    }

    @Bean(destroyMethod = "close")
    public RestHighLevelClient restHighLevelClient() {
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(esProperties.getHost(), esProperties.getPort())));
    }
}
