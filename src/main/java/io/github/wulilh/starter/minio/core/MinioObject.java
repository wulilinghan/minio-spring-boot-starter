package io.github.wulilh.starter.minio.core;

import io.github.wulilh.starter.minio.util.ComUtils;
import io.minio.messages.Item;
import io.minio.messages.Owner;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;

/**
 * @author wuliling Created By 2023-02-12 12:14
 **/
@Data
@Accessors(chain = true)
public class MinioObject {

    private String etag;
    private Owner owner;
    /**
     * 对象名称
     **/
    private String objectName;
    /**
     * 最后操作时间
     **/
    private ZonedDateTime lastModified;
    /**
     * 对象大小
     **/
    private String size;
    private String storageClass;
    private boolean isLatest;
    private String versionId;
    /**
     * 对象类型：directory（目录）或file（文件）
     **/
    private String type;

    public MinioObject(Item item) {
        this.objectName = item.objectName();
        this.type = item.isDir() ? "directory" : "file";
        this.etag = item.etag();
        long sizeNum = item.size();
        this.size = sizeNum > 0 ? ComUtils.convertFileSize(sizeNum) : "0";
        this.storageClass = item.storageClass();
        this.owner = item.owner();
        this.isLatest = item.isLatest();
        this.versionId = item.versionId();
        try {
            this.lastModified = item.lastModified();
        } catch (NullPointerException ignored) {
        }
    }
}
