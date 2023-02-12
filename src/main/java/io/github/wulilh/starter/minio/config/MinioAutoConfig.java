package io.github.wulilh.starter.minio.config;

import io.github.wulilh.starter.minio.core.*;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

/**
 * @author wuliling Created By 2023-02-12 10:57
 **/
@ConditionalOnProperty(prefix = "minio", name = "enabled", havingValue = "true", matchIfMissing = false)
@EnableConfigurationProperties(MinioProperties.class)
@Slf4j
public class MinioAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    OkHttpClientProvider okHttpClientProvider(MinioProperties properties) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(
                properties.getOkHttpConnectTimeout().getSeconds(), TimeUnit.SECONDS).build();
        return () -> okHttpClient;
    }

    @Bean(name = "minioClient")
    @ConditionalOnMissingBean({MinioClient.class})
    MinioClientFactory minioClientFactory(OkHttpClientProvider httpClientProvider, MinioProperties properties) {
        return new MinioClientFactory(httpClientProvider, properties);
    }

    @Bean
    @ConditionalOnMissingBean
    MinioOperations bucketOperators(MinioClient client, MinioProperties properties) {
        return new MinioTemplate(client);
    }
}
