package com.suke.czx.common.utils;


import com.suke.czx.config.FileConfig;

/**
 * 系统参数相关Key
 * @author czx
 * @email object_czx@163.com
 * @date 2017-03-26 10:33
 */
public class ConfigConstant {
    /**
     * 云存储配置KEY
     */
    public final static String CLOUD_STORAGE_CONFIG_KEY = "CLOUD_STORAGE_CONFIG_KEY";

    /**
     * 上传图片路径
     */
    public final static String FILE_BASE_PATH = new FileConfig().fileBasePath;
}
