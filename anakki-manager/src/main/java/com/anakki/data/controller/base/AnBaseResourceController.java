package com.anakki.data.controller.base;


import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.bean.common.ResponseDTO;
import com.anakki.data.entity.request.List3dModelRequest;
import com.anakki.data.entity.request.ListResourceRequest;
import com.anakki.data.entity.response.List3dModelResponse;
import com.anakki.data.entity.response.ListResourceResponse;
import com.anakki.data.service.An3dModelService;
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
@RequestMapping("/base/anakki/resource")
public class AnBaseResourceController {

    @Autowired
    private AnResourceService anResourceService;
    @ApiOperation(value = "获取资源列表")
    @GetMapping("/list")
    public ResponseDTO<BasePageResult<ListResourceResponse>> listResource(ListResourceRequest listResourceRequest) {
        BasePageResult<ListResourceResponse> listResourceResponseBasePageResult = anResourceService.listResource(listResourceRequest);
        return ResponseDTO.succData(listResourceResponseBasePageResult);
    }
}
