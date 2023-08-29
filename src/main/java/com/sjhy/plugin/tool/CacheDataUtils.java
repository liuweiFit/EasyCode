package com.sjhy.plugin.tool;

import com.intellij.database.psi.DbTable;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.sjhy.plugin.entity.Template;
import lombok.Data;

import java.util.List;

/**
 * 缓存数据工具类
 *
 * @author makejava
 * @version 1.0.0
 * @since 2018/07/17 13:10
 */
@Data
public class CacheDataUtils {
    private volatile static CacheDataUtils cacheDataUtils;

    /**
     * 单例模式
     */
    public static CacheDataUtils getInstance() {
        if (cacheDataUtils == null) {
            synchronized (CacheDataUtils.class) {
                if (cacheDataUtils == null) {
                    cacheDataUtils = new CacheDataUtils();
                }
            }
        }
        return cacheDataUtils;
    }

    private CacheDataUtils() {
    }

    /**
     * 所有选中的表
     */
    private List<DbTable> dbTableList;
    /**
     * 项目中所有的modules
     */
    private Module[] modules;
    /**
     * 当前项目
     */
    private Project project;
    /**
     * 当前选中的表
     */
    private DbTable selectDbTable;
    /**
     * 保存路径
     */
    private String savePath;
    /**
     * 选中的所有模板
     */
    private List<Template> selectTemplate;
    /**
     * 设置的包名称
     */
    private String packageName;
    /**
     * 选中的model
     */
    private Module selectModule;
    /**
     * 是否统一配置
     */
    private boolean unifiedConfig;
}
