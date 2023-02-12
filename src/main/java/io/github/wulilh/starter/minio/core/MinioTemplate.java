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
            log.error("check bucket [{}] if exists error", bucketName, e);
            throw new RuntimeException("check bucket if exists error", e);
        }
    }

    @Override
    public void createBucket(String bucketName) {
        if (!this.bucketExists(bucketName)) {
            try {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            } catch (Exception e) {
                log.error("create bucket [{}] error", bucketName, e);
                throw new RuntimeException("create bucket error", e);
            }
        }
    }

    @Override
    public List<Bucket> getAllBuckets() {
        return null;
    }

    @Override
    public Bucket getBucket(String bucketName) {
        return null;
    }

    @Override
    public void deleteBucket(String bucketName) {
    }

    /**
     * 获取文件外链
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param expires    过期时间 默认单位 秒
     * @return url
     */
    @Override
    public String getObjectUrl(String bucketName, String objectName, int expires) throws Exception {
        return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.GET).bucket(bucketName).object(objectName).expiry(expires).build());
    }

    /**
     * 获取文件外链
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @return url
     */
    @Override
    public String getObjectUrl(String bucketName, String objectName) throws Exception {
        return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.GET).bucket(bucketName).object(objectName).build());
    }

    /**
     * 获取文件二进制流
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @return 二进制流
     */
    @Override
    public InputStream getObject(String bucketName, String objectName) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException, ServerException {
        return minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
    }

    /**
     * 下载文件
     *
     * @param bucketName bucket name
     * @param objectName object name
     * @param filename   输出文件名
     */
    @Override
    public void downloadObject(String bucketName, String objectName, String filename) throws Exception {
        minioClient.downloadObject(DownloadObjectArgs.builder().bucket(bucketName).object(objectName).filename(filename).build());
    }

    /**
     * 上传文件
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param stream     文件流
     * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#putObject
     */
    @Override
    public void putObject(String bucketName, String objectName, InputStream stream) throws Exception {
        minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(objectName)
                .stream(stream, stream.available(), -1)
                .contentType("application/octet-stream").build());
    }

    /**
     * 上传文件
     *
     * @param bucketName  bucket名称
     * @param objectName  文件名称
     * @param stream      文件流
     * @param size        大小
     * @param contextType 类型
     * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#putObject
     */
    @Override
    public void putObject(String bucketName, String objectName, InputStream stream, long size, String contextType) throws Exception {
        minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(stream, stream.available(), -1).contentType(contextType).build());
    }


    /**
     * 获取文件信息和元数据
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#statObject
     */
    @Override
    public StatObjectResponse getObjectInfo(String bucketName, String objectName) throws Exception {
        return minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());

    }

    /**
     * 获取文件信息和元数据
     *
     * @param args args
     * @return StatObjectResponse
     * @throws Exception
     */
    @Override
    public StatObjectResponse getObjectInfo(StatObjectArgs args) throws Exception {
        return minioClient.statObject(args);
    }

    /**
     * 删除文件
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#removeObject
     */
    @Override
    public void removeObject(String bucketName, String objectName) throws Exception {
        minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
    }

    /**
     * 获取上传URL
     *
     * @param bucketName bucket name
     * @param objectName object name
     * @param expires    过期时间 秒
     * @return
     * @throws Exception
     */
    @Override
    public String presignedPutObject(String bucketName, String objectName, Integer expires) throws Exception {
        return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .method(Method.PUT)
                .bucket(bucketName)
                .object(objectName)
                .expiry(expires)
                .build());
    }

}
