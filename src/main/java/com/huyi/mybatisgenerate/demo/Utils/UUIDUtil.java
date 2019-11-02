package com.huyi.mybatisgenerate.demo.Utils;

import java.util.UUID;

/**
 * 辽宁捷畅物流有限公司 -信息技术中心
 * <p>
 * UUID生成工具
 *
 * @author 臧英明
 * @create 2017-11-19
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
