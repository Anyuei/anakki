package com.anakki.data.bean.common;

import lombok.Data;

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
public class RandomList {
    private List<String> names;
    private LocalDateTime openTime;
    private String name;
}
