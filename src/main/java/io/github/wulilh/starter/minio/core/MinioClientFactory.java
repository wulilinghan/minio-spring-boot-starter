package io.github.wulilh.starter.minio.core;

import io.github.wulilh.starter.minio.config.MinioProperties;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.errors.MinioException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartFactoryBean;

import java.util.Objects;

/**
 * @author wuliling Created By 2023-02-12 13:09
 **/
@Slf4j
public class MinioClientFactory implements SmartFactoryBean<MinioClient> {

    private final MinioProperties properties;
    private final OkHttpClientProvider okHttpClientProvider;

    public MinioClientFactory(OkHttpClientProvider okHttpClientProvider, MinioProperties properties) {
        this.okHttpClientProvider = Objects.requireNonNull(okHttpClientProvider);
        this.properties = Objects.requireNonNull(properties);
    }

    @Override
    public Class<?> getObjectType() {
        return MinioClient.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public MinioClient getObject() throws MinioException {
        MinioClient client = MinioClient.builder()
                .endpoint(properties.getEndpoint())
                .credentials(properties.getAccessKey(), properties.getSecretKey())
                .build();

        client.setTimeout(
                properties.getConnectTimeout().toMillis(),
                properties.getWriteTimeout().toMillis(),
                properties.getReadTimeout().toMillis()
        );

        try {
            log.debug("Checking if bucket {} exists", properties.getBucket());
            BucketExistsArgs existsArgs = BucketExistsArgs.builder().bucket(properties.getBucket()).build();
            boolean bucketExists = client.bucketExists(existsArgs);
            if (!bucketExists) {
                if (!properties.isCreateBucketIfAbsent()) {
                    throw new IllegalStateException("Bucket does not exist: " + properties.getBucket());
                } else {
                    try {
                        MakeBucketArgs makeBucketArgs = MakeBucketArgs.builder().bucket(properties.getBucket()).build();
                        client.makeBucket(makeBucketArgs);
                    } catch (Exception e) {
                        throw new MinioException("Cannot create bucket" + e);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error while checking bucket", e);
            throw new MinioException(e.toString());
        }
        return client;
    }

}
