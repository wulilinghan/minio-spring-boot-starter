package io.github.wulilh.starter.minio.core;

import okhttp3.OkHttpClient;

import java.util.function.Supplier;

/**
 * @author wuliling Created By 2023-02-12 15:01
 **/
@FunctionalInterface
public interface OkHttpClientProvider extends Supplier<OkHttpClient> {

    @Override
    OkHttpClient get();

}
