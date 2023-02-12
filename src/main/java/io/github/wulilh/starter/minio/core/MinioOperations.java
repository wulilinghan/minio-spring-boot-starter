package io.github.wulilh.starter.minio.core;

import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
import io.minio.errors.*;
import io.minio.messages.Bucket;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @author wuliling Created By 2023-02-12 12:09
 **/
public interface MinioOperations {

    boolean bucketExists(String bucketName);

    void createBucket(String bucketName);

    List<Bucket> getAllBuckets();

    Bucket getBucket(String bucketName);

    void deleteBucket(String bucketName);


    String getObjectUrl(String bucketName, String objectName, int expires) throws Exception;

    String getObjectUrl(String bucketName, String objectName) throws Exception;

    InputStream getObject(String bucketName, String objectName) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException, ServerException;

    void downloadObject(String bucketName, String objectName, String filename) throws Exception;

    void putObject(String bucketName, String objectName, InputStream stream) throws Exception;

    void putObject(String bucketName, String objectName, InputStream stream, long size, String contextType) throws Exception;

    StatObjectResponse getObjectInfo(String bucketName, String objectName) throws Exception;

    StatObjectResponse getObjectInfo(StatObjectArgs args) throws Exception;

    void removeObject(String bucketName, String objectName) throws Exception;

    String presignedPutObject(String bucketName, String objectName, Integer expires) throws Exception;
}
