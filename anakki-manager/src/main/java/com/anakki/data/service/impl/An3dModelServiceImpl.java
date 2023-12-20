package com.anakki.data.service.impl;

import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.entity.An3dModel;
import com.anakki.data.entity.request.List3dModelRequest;
import com.anakki.data.entity.response.List3dModelResponse;
import com.anakki.data.mapper.An3dModelMapper;
import com.anakki.data.service.An3dModelService;
import com.anakki.data.utils.common.BeanUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Anakki
 * @since 2023-12-16
 */
@Service
public class An3dModelServiceImpl extends ServiceImpl<An3dModelMapper, An3dModel> implements An3dModelService {
    @Override
    public BasePageResult<List3dModelResponse> listAn3dModel(List3dModelRequest list3dModelRequest){
        IPage<An3dModel> anFriendsCommentPage= new Page<>(
                list3dModelRequest.getCurrent(),
                list3dModelRequest.getSize());
        QueryWrapper<An3dModel> list3dModelResponseQueryWrapper = new QueryWrapper<>();
        list3dModelResponseQueryWrapper.eq("status","true");
        IPage<An3dModel> page = page(anFriendsCommentPage, list3dModelResponseQueryWrapper);
        List<List3dModelResponse> list3dModelResponses = BeanUtils.copyBeanList(page.getRecords(), List3dModelResponse.class);
        return new BasePageResult<>(list3dModelResponses, page.getTotal());
    }

    @Override
    public String downloadModel(Long id) {
        An3dModel byId = getById(id);
        if (null!=byId){
            return byId.getFileUrl();
        }
        throw new RuntimeException("资源不存在");
    }
}
