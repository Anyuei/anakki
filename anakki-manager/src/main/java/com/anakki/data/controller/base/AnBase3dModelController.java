package com.anakki.data.controller.base;


import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.bean.common.ResponseDTO;
import com.anakki.data.entity.request.List3dModelRequest;
import com.anakki.data.entity.response.List3dModelResponse;
import com.anakki.data.service.An3dModelService;
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
@RequestMapping("/base/anakki/3dModel")
public class AnBase3dModelController {

    @Autowired
    private An3dModelService an3dModelService;
    @ApiOperation(value = "获取3d模型列表")
    @GetMapping("/list-3dModels")
    public ResponseDTO<BasePageResult<List3dModelResponse>> listAn3dModel(List3dModelRequest list3dModelRequest) {
        BasePageResult<List3dModelResponse> listAn3dModel = an3dModelService.listAn3dModel(list3dModelRequest);
        return ResponseDTO.succData(listAn3dModel);
    }
}
