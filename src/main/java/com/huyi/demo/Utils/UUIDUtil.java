package com.huyi.demo.Utils;

import java.util.UUID;

/**
 * <p>
 * UUID生成工具
 */
public abstract class UUIDUtil {
    /**
     * 获得一个32位UUID
     *
     * @return
     */
    public static String getUuidTo32() {
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        return uuid.toString();
    }
}
