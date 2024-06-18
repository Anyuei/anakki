package com.anakki.data.mapper;

import com.anakki.data.entity.AnNote;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Anakki
 * @since 2024-06-06
 */
@Mapper
public interface AnNoteMapper extends BaseMapper<AnNote> {

   void incrNoteViewCount(Long id);

    void likeNote(Long id);

}
