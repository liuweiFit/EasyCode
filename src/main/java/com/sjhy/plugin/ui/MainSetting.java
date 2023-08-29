package com.sjhy.plugin.ui;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.UnnamedConfigurable;
import com.sjhy.plugin.comm.AbstractService;
import com.sjhy.plugin.tool.CollectionUtil;
import com.sjhy.plugin.tool.ConfigInfo;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 主设置面板
 *
 * @author makejava
 * @version 1.0.0
 * @since 2018/07/17 13:10
 */
public class MainSetting extends AbstractService implements Configurable, Configurable.Composite {
    /**
     * 主面板
     */
    private JPanel mainPanel;
    /**
     * 编码选择下拉框
     */
    private JComboBox encodeComboBox;
    /**
     * 作者编辑框
     */
    private JTextField authorTextField;
    /**
     * 重置默认设置按钮
     */
    private JButton resetBtn;

    /**
     * 重置列表
     */
    private List<Configurable> resetList;

    /**
     * 需要保存的列表
     */
    private List<Configurable> saveList;

    /**
     * 默认构造方法
     */
    public MainSetting() {
        init();

        //初始化事件
        ConfigInfo configInfo = ConfigInfo.getInstance();
        //重置配置信息
        resetBtn.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(null, "确认重置默认配置?\n重置默认配置只会还原插件自带分组配置信息，不会删除用户新增分组信息。", "Title Info", JOptionPane.OK_CANCEL_OPTION);
            if (JOptionPane.YES_OPTION == result) {
                if (CollectionUtil.isEmpty(resetList)) {
                    return;
                }
                // 初始化默认配置
                configInfo.initDefault();
                resetList.forEach(UnnamedConfigurable::reset);
                if (CollectionUtil.isEmpty(saveList)) {
                    return;
                }
                saveList.forEach(configurable -> {
                    try {
                        configurable.apply();
                    } catch (ConfigurationException e1) {
                        e1.printStackTrace();
                    }
                });
            }
        });
    }

    /**
     * 初始化方法
     */
    private void init() {
        //初始化数据
        authorTextField.setText(configInfo.getAuthor());
        encodeComboBox.setSelectedItem(configInfo.getEncode());
    }

    /**
     * 设置显示名称
     *
     * @return 显示名称
     */
    @Nls
    @Override
    public String getDisplayName() {
        return "Easy Code";
    }

    /**
     * 更多配置
     *
     * @return 配置选项
     */
    @NotNull
    @Override
    public Configurable[] getConfigurables() {
        Configurable[] result = new Configurable[4];
        result[0] = new TypeMapperSetting(configInfo);
        result[1] = new TemplateSettingPanel();
        result[2] = new TableSettingPanel();
        result[3] = new GlobalConfigSettingPanel();
        // 需要重置的列表
        resetList = new ArrayList<>();
        resetList.add(result[0]);
        resetList.add(result[1]);
        resetList.add(result[3]);
        // 不需要重置的列表
        saveList = new ArrayList<>();
        saveList.add(this);
        saveList.add(result[2]);
        return result;
    }

    /**
     * 获取主面板信息
     *
     * @return 主面板
     */
    @Nullable
    @Override
    public JComponent createComponent() {
        return mainPanel;
    }

    /**
     * 判断是否修改
     *
     * @return 是否修改
     */
    @Override
    public boolean isModified() {
        return !configInfo.getEncode().equals(encodeComboBox.getSelectedItem()) || !configInfo.getAuthor().equals(authorTextField.getText());
    }

    /**
     * 应用修改
     */
    @Override
    public void apply() {
        //保存数据
        configInfo.setAuthor(authorTextField.getText());
        configInfo.setEncode((String) encodeComboBox.getSelectedItem());
    }

    /**
     * 重置
     */
    @Override
    public void reset() {
        init();
    }
}
