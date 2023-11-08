package com.anakki.data.utils.common;

import cn.hutool.core.util.DesensitizedUtil;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * ClassName: RegularUtil
 * Description:
 *
 * @author Anakki
 * @date 2023/5/28 22:38
 */
public class CoverUtil {




    /**
     * 电话号通用遮盖脱敏，默认*遮盖
     * @param strings
     * @return
     */
    public List<String> coverTelephone(List<String> strings) {
        CopyOnWriteArrayList<String> objects = new CopyOnWriteArrayList<>();
        strings.parallelStream().forEach(s -> {
            objects.add(DesensitizedUtil.mobilePhone(s));
        });
        return objects;
    }

    /**
     * 姓名通用遮盖脱敏，默认*遮盖
     * @param strings
     * @return
     */
    public List<String> coverChineseName(List<String> strings) {
        CopyOnWriteArrayList<String> objects = new CopyOnWriteArrayList<>();
        strings.parallelStream().forEach(s -> {
            objects.add(DesensitizedUtil.chineseName(s));
        });
        return objects;
    }


    /**
     * 银行卡通用遮盖脱敏，默认*遮盖
     * @param strings
     * @return
     */
    public List<String> coverBankCard(List<String> strings) {
        CopyOnWriteArrayList<String> objects = new CopyOnWriteArrayList<>();
        strings.parallelStream().forEach(s -> {
            objects.add(DesensitizedUtil.bankCard(s));
        });
        return objects;
    }
}
