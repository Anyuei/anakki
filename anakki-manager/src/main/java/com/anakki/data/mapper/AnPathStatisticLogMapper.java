package com.anakki.data.mapper;

import com.anakki.data.entity.AnPathStatisticLog;
import com.anakki.data.entity.response.DailyVisitCountResponse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Anakki
 * @since 2023-12-01
 */
@Mapper
public interface AnPathStatisticLogMapper extends BaseMapper<AnPathStatisticLog> {

    List<DailyVisitCountResponse> findDailyVisitCounts(@Param("startDate") LocalDate startDate,
                                                       @Param("endDate") LocalDate endDate);
}
