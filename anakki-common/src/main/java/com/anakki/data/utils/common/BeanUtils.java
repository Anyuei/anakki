package com.anakki.data.utils.common;

import java.util.LinkedList;
import java.util.List;

/**
 * ClassName: BeanUtils
 * Description:
 *
 * @author Anakki
 * @date 2023/7/2 5:35
 */
public class BeanUtils {
    public static <T> List<T> copyBeanList(List<?> resourceList, Class<T> target){
        List<T> targetList = new LinkedList<>();
        if (null==resourceList||resourceList.isEmpty()){
            return targetList;
        }
        resourceList.forEach(e->{
            T o = null;
            try {
                o = target.newInstance();
            } catch (InstantiationException | IllegalAccessException ex) {
                ex.printStackTrace();
            }
            org.springframework.beans.BeanUtils.copyProperties(e,o);
            targetList.add(o);
        });
        return targetList;
    }
}
