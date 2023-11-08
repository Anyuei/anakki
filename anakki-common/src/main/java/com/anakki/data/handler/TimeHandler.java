package com.anakki.data.handler;


import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 自动填充时间
 */
@Component
public class TimeHandler implements MetaObjectHandler {

        private static final Logger LOGGER = LoggerFactory.getLogger(MetaObjectHandler.class);

        //insert操作时要填充的字段
        @Override
        public void insertFill(MetaObject metaObject) {
            LOGGER.info("start insert fill ...");
            //根据属性名字设置要填充的值
            this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
            this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        }

        //update操作时要填充的字段
        @Override
        public void updateFill(MetaObject metaObject) {
            LOGGER.info("start update fill ...");
            this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        }

    public static void main(String[] args) {
        System.out.println(LocalDateTime.now());
    }

}
