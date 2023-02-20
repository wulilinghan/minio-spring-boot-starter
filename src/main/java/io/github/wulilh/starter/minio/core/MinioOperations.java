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

    boolean createBucket(String bucketName);

    List<Bucket> getAllBuckets();

    Bucket getBucket(String bucketName);

    void deleteBucket(String bucketName);


    String getObjectUrl(String bucketName, String objectName, int expires);

    String getObjectUrl(String bucketName, String objectName);

    InputStream getObject(String bucketName, String objectName);

    void downloadObject(String bucketName, String objectName, String filename);

    void putObject(String bucketName, String objectName, InputStream stream);

    void putObject(String bucketName, String objectName, String contextType, InputStream stream);

    StatObjectResponse getObjectInfo(String bucketName, String objectName);

    StatObjectResponse getObjectInfo(StatObjectArgs args);

    void removeObject(String bucketName, String objectName);

}
