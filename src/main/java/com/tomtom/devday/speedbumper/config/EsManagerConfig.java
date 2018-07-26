package com.tomtom.devday.speedbumper.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EsManagerConfig {

    @Value("${esHost}")
    private String host;

    @Value("${esPort}")
    private Integer port;

    @Bean
    public RestHighLevelClient getClient() {
        final RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost(host, port, "http"));
        return new RestHighLevelClient(restClientBuilder);

    }

}
