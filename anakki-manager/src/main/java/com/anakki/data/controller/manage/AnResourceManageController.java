package com.anakki.data.controller.manage;


import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.bean.common.ResponseDTO;
import com.anakki.data.bean.common.request.IdListRequest;
import com.anakki.data.entity.request.ListResourceManageRequest;
import com.anakki.data.entity.request.ListResourceRequest;
import com.anakki.data.entity.response.ListResourceManageResponse;
import com.anakki.data.entity.response.ListResourceResponse;
import com.anakki.data.service.AnResourceService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "批量删除资源")
    @DeleteMapping("/delete-batch")
    public ResponseDTO<Boolean> deleteResource(@RequestBody IdListRequest idListRequest) {
        anResourceService.removeResourcesByManage(idListRequest);
        return ResponseDTO.succData(true);
    }

    @ApiOperation(value = "批量失效资源")
    @PutMapping("/disable-batch")
    public ResponseDTO<Boolean> disableResource(@RequestBody IdListRequest idListRequest) {
        anResourceService.disableResourcesByManage(idListRequest);
        return ResponseDTO.succData(true);
    }

    @ApiOperation(value = "批量生效资源")
    @PutMapping("/enable-batch")
    public ResponseDTO<Boolean> enableResource(@RequestBody IdListRequest idListRequest) {
        anResourceService.enableResourcesByManage(idListRequest);
        return ResponseDTO.succData(true);
    }
}
