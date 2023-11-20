package com.anakki.data.mapper;

import com.anakki.data.entity.AnRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Anakki
 * @since 2023-11-08
 */
@Mapper
public interface AnRecordMapper extends BaseMapper<AnRecord> {
    @Select("SELECT type FROM an_record group by type")
    List<String> getRecordTypes();
}
