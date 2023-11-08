package com.anakki.data.bean.common;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * ClassName: BasePageResult
 * Description:
 *
 * @author Anakki
 * @date 2023/4/26 4:44
 */
public class BasePageResult<T> {
    private List<T> data;
    private Long totalNum;

    public BasePageResult(List<T> data, Long totalNum) {
        this.data = data;
        this.totalNum = totalNum;
    }

    public static <E> BasePageResult<E> newInstance(IPage<E> page) {
        return new BasePageResult<E>(page.getRecords(), page.getTotal());
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Long totalNum) {
        this.totalNum = totalNum;
    }

    @Override
    public String toString() {
        return "BasePageResult{" +
                "data=" + data +
                ", totalNum=" + totalNum +
                '}';
    }
}
