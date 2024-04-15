package com.anakki.data.bean.common;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * ClassName: RandomList
 * Description:
 *
 * @author Anakki
 * @date 2024/4/15 7:37
 */
@Data
public class RandomList implements Serializable {
    private List<String> names;
    private LocalDateTime openTime;
    private String name;
    private String gift;
}
