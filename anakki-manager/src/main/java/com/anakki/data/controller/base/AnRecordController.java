package com.anakki.data.controller.base;

import com.anakki.data.bean.common.BaseContext;
import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.bean.common.ResponseDTO;
import com.anakki.data.bean.common.request.IdNotNullRequest;
import com.anakki.data.entity.AnIpAddress;
import com.anakki.data.entity.AnRecord;
import com.anakki.data.entity.request.GetContentRequest;
import com.anakki.data.service.AnIpAddressService;
import com.anakki.data.service.AnRecordService;
import com.anakki.data.utils.IPUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Anakki
 * @since 2023-11-08
 */
@RestController
@RequestMapping("/base/anakki/record")
public class AnRecordController {


    @Autowired
    private AnRecordService anRecordService;
    @Autowired
    private  AnIpAddressService anIpAddressService;
    @ApiOperation(value = "获取图文")
    @GetMapping("/flow")
    public ResponseDTO<BasePageResult<AnRecord>> flow(GetContentRequest getContentRequest, HttpServletRequest request) {
        String ipAddr = IPUtils.getIpAddr(request);

        return ResponseDTO.succData(anRecordService.flow(getContentRequest,ipAddr));
    }

    @ApiOperation(value = "根据id获取图文")
    @GetMapping("/flowById")
    public ResponseDTO<AnRecord> flowById(Long id) {
        AnRecord byId = anRecordService.getById(id);
        if (null==byId){
            byId=new AnRecord();
            return ResponseDTO.succData(byId);
        }
        anRecordService.increaseViewCount(id);
        byId.setImgUrl(byId.getImgUrl().split("\\?")[0]);
        return ResponseDTO.succData(byId);
    }
    @ApiOperation(value = "增加浏览量")
    @GetMapping("/increaseViewCount")
    public ResponseDTO<Boolean> increaseViewCount(Long id) {
        AnRecord byId = anRecordService.getById(id);
        if (null!=byId){
           anRecordService.increaseViewCount(id);
            return ResponseDTO.succData(true);
        }
        return ResponseDTO.succData(false);
    }
    @ApiOperation(value = "修改错误的地址")
    @PostMapping("/updateFaultAddress")
    public ResponseDTO<Boolean> updateAddress() {
        anIpAddressService.updateAddressByIp();
        return ResponseDTO.succData(true);
    }
    @ApiOperation(value = "点赞")
    @PostMapping("/like")
    public ResponseDTO<Object> like(@RequestBody IdNotNullRequest id, HttpServletRequest request) {
        String ipAddr = IPUtils.getIpAddr(request);
        Object like = anRecordService.userOperate(id.getId(), ipAddr, "LIKE");
        return ResponseDTO.succData(like);
    }

    @ApiOperation(value = "不喜欢")
    @PostMapping("/unLike")
    public ResponseDTO<Object> unLike(@RequestBody IdNotNullRequest id,HttpServletRequest request) {
        String ipAddr = IPUtils.getIpAddr(request);
        Object unlike = anRecordService.userOperate(id.getId(), ipAddr, "UNLIKE");
        return ResponseDTO.succData(unlike);
    }
}
