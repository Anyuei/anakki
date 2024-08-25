package com.anakki.data.controller.manage;

import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.bean.common.ResponseDTO;
import com.anakki.data.bean.common.request.IdListRequest;
import com.anakki.data.entity.AnIpAddress;
import com.anakki.data.entity.AnPathStatisticLog;
import com.anakki.data.entity.request.ListIpAddressRequest;
import com.anakki.data.entity.request.ListPathStatisticLogRequest;
import com.anakki.data.service.AnIpAddressService;
import com.anakki.data.service.AnPathStatisticLogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Anakki
 * @since 2023-11-29
 */
@RestController
@RequestMapping("/manage/path-statistic-log")
public class AnPathStatisticLogController {
    @Autowired
    private AnPathStatisticLogService anPathStatisticLogService;

    @ApiOperation(value = "获取访问路径列表")
    @GetMapping("/list")
    public ResponseDTO<BasePageResult<AnPathStatisticLog>> listPathStatisticLog(ListPathStatisticLogRequest listIpAddressRequest) {
        BasePageResult<AnPathStatisticLog> anIpAddressBasePageResult = anPathStatisticLogService.listPathStatisticLog(listIpAddressRequest);

        return ResponseDTO.succData(anIpAddressBasePageResult);
    }
    @ApiOperation(value = "批量删除访问")
    @DeleteMapping("/delete-batch")
    public ResponseDTO<Boolean> deletePathStatisticLog(@RequestBody IdListRequest idListRequest) {
        boolean delete = anPathStatisticLogService.removeByIds(idListRequest.getIdList());
        return ResponseDTO.succData(delete);
    }
}
