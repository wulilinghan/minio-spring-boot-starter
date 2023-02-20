package io.github.wulilh.starter.minio.core;

import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @author wuliling Created By 2023-02-12 12:30
 **/
@Slf4j
public class MinioTemplate implements MinioOperations {

    private final MinioClient minioClient;

    public MinioTemplate(MinioClient client) {
        this.minioClient = client;
    }

    @Override
    public boolean bucketExists(String bucketName) {
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            log.error("check bucket [{}] if exists error", bucketName);
            throw new RuntimeException("check bucket if exists error", e);
        }
    }

    @Override
    public boolean createBucket(String bucketName) {
        if (this.bucketExists(bucketName)) {
            log.warn("bucket [{}] already exists", bucketName);
            return false;
        }
        try {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            log.error("create bucket [{}] error", bucketName);
            return false;
        }
        return true;
    }

    @Override
    public List<Bucket> getAllBuckets() {
        try {
            return minioClient.listBuckets();
        } catch (Exception e) {
            log.error("getAllBuckets error", e);
        }
        return null;
    }

    @Override
    public Bucket getBucket(String bucketName) {
        return getAllBuckets().stream().filter(bucket -> bucket.name().equals(bucketName)).findFirst().orElse(null);
    }

    @Override
    public void deleteBucket(String bucketName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            log.error("deleteBucket error", e);
        }
    }

    /**
     * 获取文件外链
     *
     * @param bucketName     bucket名称
     * @param objectName     文件名称
     * @param expiresSeconds 过期时间 默认单位 秒
     * @return url
     */
    @Override
    public String getObjectUrl(String bucketName, String objectName, int expiresSeconds) {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.GET).bucket(bucketName).object(objectName).expiry(expiresSeconds).build());
        } catch (Exception e) {
            log.error("getObjectUrl bucketName[{}] objectName[{}] expiresSeconds[{}] error", bucketName, objectName, expiresSeconds);
            throw new RuntimeException("getObjectUrl  error", e);
        }
    }

    /**
     * 获取文件外链
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @return url
     */
    @Override
    public String getObjectUrl(String bucketName, String objectName) {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.GET).bucket(bucketName).object(objectName).build());
        } catch (Exception e) {
            log.error("getObjectUrl bucketName[{}] objectName[{}] ", bucketName, objectName);
            throw new RuntimeException("getObjectUrl error", e);
        }
    }

    /**
     * 获取文件二进制流
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @return 二进制流
     */
    @Override
    public InputStream getObject(String bucketName, String objectName) {
        try {
            return minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (Exception e) {
            log.error("getObject bucketName[{}] objectName[{}] error", bucketName, objectName);
            throw new RuntimeException("getObject error", e);
        }
    }

    /**
     * 下载文件
     *
     * @param bucketName bucket name
     * @param objectName object name
     * @param filename   输出文件名
     */
    @Override
    public void downloadObject(String bucketName, String objectName, String filename) {
        try {
            minioClient.downloadObject(DownloadObjectArgs.builder().bucket(bucketName).object(objectName).filename(filename).build());
        } catch (Exception e) {
            log.error("downloadObject bucketName[{}] objectName[{}] filename[{}] error", bucketName, objectName, filename);
            throw new RuntimeException("downloadObject error", e);
        }
    }

    /**
     * 上传文件
     * <p>
     * https://docs.minio.io/cn/java-client-api-reference.html#putObject
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param stream     文件流
     */
    @Override
    public void putObject(String bucketName, String objectName, InputStream stream) {
        try {
            minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(objectName)
                    .stream(stream, stream.available(), -1)
                    .contentType("application/octet-stream").build());
        } catch (Exception e) {
            log.error("putObject bucketName[{}] objectName[{}]  error", bucketName, objectName);
            throw new RuntimeException("putObject error", e);
        }
    }

    /**
     * 上传文件
     * <p>
     * https://docs.minio.io/cn/java-client-api-reference.html#putObject
     *
     * @param bucketName  bucket名称
     * @param objectName  文件名称
     * @param stream      文件流
     * @param contextType 类型
     */
    @Override
    public void putObject(String bucketName, String objectName, String contextType, InputStream stream) {
        try {
            minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(objectName)
                    .stream(stream, stream.available(), -1).contentType(contextType).build());
        } catch (Exception e) {
            log.error("putObject bucketName[{}] objectName[{}]  error", bucketName, objectName);
            throw new RuntimeException("putObject error", e);
        }
    }


    /**
     * 获取文件信息和元数据
     * https://docs.minio.io/cn/java-client-api-reference.html#statObject
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     */
    @Override
    public StatObjectResponse getObjectInfo(String bucketName, String objectName) {
        try {
            StatObjectArgs statObjectArgs = StatObjectArgs.builder().bucket(bucketName).object(objectName).build();
            return getObjectInfo(statObjectArgs);
        } catch (Exception e) {
            log.error("getObjectInfo bucketName[{}] objectName[{}]  error", bucketName, objectName);
            throw new RuntimeException("getObjectInfo error", e);
        }
    }

    /**
     * 获取文件信息和元数据
     *
     * @param args args
     * @return StatObjectResponse
     */
    @Override
    public StatObjectResponse getObjectInfo(StatObjectArgs args) {
        try {
            return minioClient.statObject(args);
        } catch (Exception e) {
            throw new RuntimeException("getObjectInfo error", e);
        }
    }

    /**
     * 删除文件
     * <p>
     * https://docs.minio.io/cn/java-client-api-reference.html#removeObject
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     */
    @Override
    public void removeObject(String bucketName, String objectName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (Exception e) {
            log.error("removeObject bucketName[{}] objectName[{}]  error", bucketName, objectName);
            throw new RuntimeException("removeObject error", e);
        }
    }

}
