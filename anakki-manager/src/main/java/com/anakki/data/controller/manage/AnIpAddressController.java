package com.anakki.data.controller.manage;

import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.bean.common.ResponseDTO;
import com.anakki.data.bean.common.request.IdListRequest;
import com.anakki.data.entity.AnIpAddress;
import com.anakki.data.entity.request.ListIpAddressRequest;
import com.anakki.data.entity.request.ListManagerRequest;
import com.anakki.data.entity.response.ListManagerResponse;
import com.anakki.data.service.AnIpAddressService;
import com.anakki.data.service.AnManagerService;
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
@RequestMapping("/manage/ip-address")
public class AnIpAddressController {
    @Autowired
    private AnIpAddressService anIpAddressService;

    @ApiOperation(value = "获取访问ip列表")
    @GetMapping("/list")
    public ResponseDTO<BasePageResult<AnIpAddress>> listIpAddress(ListIpAddressRequest listIpAddressRequest) {
        BasePageResult<AnIpAddress> anIpAddressBasePageResult = anIpAddressService.listIpAddress(listIpAddressRequest);

        return ResponseDTO.succData(anIpAddressBasePageResult);
    }
    @ApiOperation(value = "批量删除访问")
    @DeleteMapping("/delete-batch")
    public ResponseDTO<Boolean> deleteIpAddress(@RequestBody IdListRequest idListRequest) {
        boolean delete = anIpAddressService.removeByIds(idListRequest.getIdList());
        return ResponseDTO.succData(delete);
    }
}
