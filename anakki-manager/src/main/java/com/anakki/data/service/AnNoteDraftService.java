package com.anakki.data.service;

import com.anakki.data.entity.AnNoteDraft;
import com.anakki.data.entity.request.SaveNoteDraftRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Anakki
 * @since 2024-06-06
 */
public interface AnNoteDraftService extends IService<AnNoteDraft> {

    Long saveDraft(SaveNoteDraftRequest saveNoteDraftRequest, HttpServletRequest request);
}
