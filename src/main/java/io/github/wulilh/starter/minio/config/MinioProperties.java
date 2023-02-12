package io.github.wulilh.starter.minio.config;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.Duration;

/**
 * @author wuliling Created By 2023-02-12 1:53
 **/
@Data
@ConfigurationProperties(prefix = "minio")
public class MinioProperties implements Serializable, InitializingBean {
    private static final long serialVersionUID = 4478651702809745328L;

    /**
     * Is this starter enabled?
     */
    private boolean enabled = true;

    /**
     * URL for Minio instance
     */
    private String endpoint;

    /**
     * Minio Access key
     */
    private String accessKey;

    /**
     * Minio Secret key
     */
    private String secretKey;

    /**
     * Bucket name for the application. The bucket must already exists on Minio.
     */
    private String bucket = "default";

    /**
     * Define the connect timeout for the Minio Client.
     */
    private Duration connectTimeout = Duration.ofSeconds(10);
    /**
     * Define the connect timeout for the okhttp Client.
     */
    private Duration okHttpConnectTimeout = Duration.ofSeconds(30);

    /**
     * Define the write timeout for the Minio Client.
     */
    private Duration writeTimeout = Duration.ofSeconds(60);

    /**
     * Define the read timeout for the Minio Client.
     */
    private Duration readTimeout = Duration.ofSeconds(10);

    /**
     * Will create the bucket if it do not exists on the Minio instance.
     */
    private boolean createBucketIfAbsent = true;

    @Override
    public void afterPropertiesSet() {
        if (enabled) {
            Assert.hasText(this.endpoint, "'minio.endpoint' must not be blank.");
            Assert.hasText(this.accessKey, "'minio.access-key' must not be blank.");
            Assert.hasText(this.secretKey, "'minio.secret-key' must not be blank.");
            Assert.hasText(this.bucket, "'minio.bucket' must not be blank.");
        }
    }
}
