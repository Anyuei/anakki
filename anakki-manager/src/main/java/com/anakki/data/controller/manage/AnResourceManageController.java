package com.anakki.data.controller.manage;


import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.bean.common.ResponseDTO;
import com.anakki.data.entity.request.ListResourceManageRequest;
import com.anakki.data.entity.request.ListResourceRequest;
import com.anakki.data.entity.response.ListResourceManageResponse;
import com.anakki.data.entity.response.ListResourceResponse;
import com.anakki.data.service.AnResourceService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Anakki
 * @since 2023-12-16
 */
@RestController
@RequestMapping("/manage/resource")
public class AnResourceManageController {

    @Autowired
    private AnResourceService anResourceService;

    @ApiOperation(value = "获取资源列表")
    @GetMapping("/list")
    public ResponseDTO<BasePageResult<ListResourceManageResponse>> listResource(ListResourceManageRequest listResourceManageRequest) {
        BasePageResult<ListResourceManageResponse> listResource = anResourceService.listResource(listResourceManageRequest);
        return ResponseDTO.succData(listResource);
    }
}
