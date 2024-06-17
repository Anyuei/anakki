package com.anakki.data.service.impl;

import com.anakki.data.bean.common.BaseContext;
import com.anakki.data.entity.AnNote;
import com.anakki.data.entity.AnNoteDraft;
import com.anakki.data.entity.AnUser;
import com.anakki.data.entity.request.SaveNoteDraftRequest;
import com.anakki.data.mapper.AnNoteDraftMapper;
import com.anakki.data.service.AnIpAddressService;
import com.anakki.data.service.AnNoteDraftService;
import com.anakki.data.service.AnNoteService;
import com.anakki.data.service.AnUserService;
import com.anakki.data.utils.IPUtils;
import com.anakki.data.utils.common.HtmlUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Anakki
 * @since 2024-06-06
 */
@Service
public class AnNoteDraftServiceImpl extends ServiceImpl<AnNoteDraftMapper, AnNoteDraft> implements AnNoteDraftService {

    @Autowired
    private AnNoteService anNoteService;
    @Autowired
    private AnUserService anUserService;
    @Autowired
    private AnIpAddressService anIpAddressService;

    @Override
    public Long saveDraft(SaveNoteDraftRequest saveNoteDraftRequest, HttpServletRequest request) {

        String currentNickname = BaseContext.getCurrentNickname(false);
        AnUser user = anUserService.getByNickname(currentNickname);

        Long noteId = saveNoteDraftRequest.getNoteId();

        AnNote byId = anNoteService.getById(noteId);
        if (null!=noteId&&null==byId){
            throw new RuntimeException("文章不存在");
        }
        //第一次保存
        if (null==noteId){
            String ipAddr = IPUtils.getIpAddr(request);
            AnNote anNote = new AnNote();
            anNote.setContent(saveNoteDraftRequest.getContent());
            anNote.setCoverImage(HtmlUtil.getFirstImg(saveNoteDraftRequest.getContent()));
            anNote.setTitle(HtmlUtil.getFirstH1(saveNoteDraftRequest.getContent()));
            anNote.setDescription(HtmlUtil.getFirstP(saveNoteDraftRequest.getContent()));
            anNote.setAuthor(user.getNickname());
            anNote.setLocation(ipAddr);
            anNote.setType(ipAddr);
            anNote.setCreateBy(user.getId());
            anNote.setStatus("DRAFT");
            boolean save = anNoteService.save(anNote);
            if (!save){
                throw new RuntimeException("保存失败");
            }
            noteId=anNote.getId();
        }
        AnNoteDraft anNoteDraft = new AnNoteDraft();
        anNoteDraft.setContent(saveNoteDraftRequest.getContent());
        anNoteDraft.setNoteId(noteId);
         save(anNoteDraft);
        return noteId;
    }
}
