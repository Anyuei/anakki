package com.anakki.data.service.impl;

import com.anakki.data.bean.common.BaseContext;
import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.bean.constant.CosBucketNameConst;
import com.anakki.data.bean.constant.CosPathConst;
import com.anakki.data.bean.constant.RedisKey;
import com.anakki.data.entity.AnNote;
import com.anakki.data.entity.AnUser;
import com.anakki.data.entity.request.*;
import com.anakki.data.mapper.AnNoteMapper;
import com.anakki.data.service.AnIpAddressService;
import com.anakki.data.service.AnNoteService;
import com.anakki.data.service.AnUserService;
import com.anakki.data.utils.IPUtils;
import com.anakki.data.utils.common.COSUtil;
import com.anakki.data.utils.common.HtmlUtil;
import com.anakki.data.utils.common.RedisUtil;
import com.anakki.data.utils.common.TimeUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Anakki
 * @since 2024-06-06
 */
@Service
public class AnNoteServiceImpl extends ServiceImpl<AnNoteMapper, AnNote> implements AnNoteService {
    @Autowired
    private AnUserService anUserService;
    @Autowired
    private AnIpAddressService anIpAddressService;

    @Autowired
    private AnNoteMapper anNoteMapper;
    @Override
    public Boolean save(CreateNoteRequest createNoteRequest, HttpServletRequest request) {
        String ipAddr = IPUtils.getIpAddr(request);
        String currentNickname = BaseContext.getCurrentNickname(false);
        AnUser user = anUserService.getByNickname(currentNickname);
        AnNote anNote = new AnNote();
        BeanUtils.copyProperties(createNoteRequest, anNote);
        anNote.setAuthor(user.getNickname());
        anNote.setLocation(ipAddr);
        anNote.setCreateBy(user.getId());
        anNote.setCoverImage(HtmlUtil.getFirstImg(createNoteRequest.getContent()));
        anNote.setTitle(HtmlUtil.getFirstH1(createNoteRequest.getContent()));
        anNote.setDescription(HtmlUtil.getFirstP(createNoteRequest.getContent()));
        anNote.setStatus("COMMON");
        if (null != anNote.getId()) {
            if (!anNote.getCreateBy().equals(user.getId())) {
                throw new RuntimeException("无权限修改");
            }
            updateById(anNote);
        } else {
            save(anNote);
        }
        return true;
    }


    @Override
    public BasePageResult<AnNote> listNotes(ListNoteRequest listNoteRequest) {
        IPage<AnNote> listNotePage = new Page<>(
                listNoteRequest.getCurrent(),
                listNoteRequest.getSize());
        String type = listNoteRequest.getType();
        QueryWrapper<AnNote> anFriendsCommentQueryWrapper = new QueryWrapper<>();
        anFriendsCommentQueryWrapper.eq(null != type, "type", type);
        if (StringUtils.isEmpty(listNoteRequest.getStatus())) {
            anFriendsCommentQueryWrapper.eq("status", "COMMON");
        } else {
            anFriendsCommentQueryWrapper.eq("status", listNoteRequest.getStatus());
        }

        anFriendsCommentQueryWrapper.like(null != listNoteRequest.getContent(), "content", listNoteRequest.getContent());
        anFriendsCommentQueryWrapper.ge(
                null != listNoteRequest.getCreateTimeStart(), "create_time", listNoteRequest.getCreateTimeStart());
        anFriendsCommentQueryWrapper.le(
                null != listNoteRequest.getCreateTimeEnd(), "create_time", listNoteRequest.getCreateTimeEnd());
        anFriendsCommentQueryWrapper.orderByDesc("create_time");
        IPage<AnNote> page = page(listNotePage, anFriendsCommentQueryWrapper);
        List<AnNote> records = page.getRecords();
        return new BasePageResult<>(records, page.getTotal());
    }

    @Override
    public String uploadFiles(UploadNoteMediaRequest uploadNoteMediaRequest) throws IOException {
        return COSUtil.uploadImage(
                uploadNoteMediaRequest.getUploadFiles(),
                COSUtil.region,
                CosBucketNameConst.BUCKET_NAME_IMAGES,
                CosPathConst.BUCKET_NAME_NOTE_RESOURCE,
                true);
    }

    @Override
    public Boolean deleteFiles(DeleteNoteMediaRequest deleteNoteMediaRequest) {
        String url = deleteNoteMediaRequest.getUrl();
        COSUtil.deleteObject(CosBucketNameConst.BUCKET_NAME_IMAGES, url);
        return true;
    }

    @Override
    public void remove(IdNotNullRequest createNoteRequest, HttpServletRequest request) {
        String currentNickname = BaseContext.getCurrentNickname(false);
        AnUser user = anUserService.getByNickname(currentNickname);
        AnNote note = getById(createNoteRequest.getId());
        if (null == note) {
            return;
        }
        if (!note.getCreateBy().equals(user.getId())) {
            throw new RuntimeException("作者本人可以删除");
        }
        note.setStatus("INVALID");
        updateById(note);
    }

    @Override
    public BasePageResult<AnNote> listDraftNote(ListNoteRequest listNoteRequest) {
        String currentNickname = BaseContext.getCurrentNickname(false);
        AnUser user = anUserService.getByNickname(currentNickname);

        IPage<AnNote> listNotePage = new Page<>(
                listNoteRequest.getCurrent(),
                listNoteRequest.getSize());
        String type = listNoteRequest.getType();
        QueryWrapper<AnNote> anFriendsCommentQueryWrapper = new QueryWrapper<>();
        anFriendsCommentQueryWrapper.eq(null != type, "type", type);
        anFriendsCommentQueryWrapper.eq("status", "Draft");
        anFriendsCommentQueryWrapper.eq("create_by", user.getId());
        anFriendsCommentQueryWrapper.like(null != listNoteRequest.getContent(), "content", listNoteRequest.getContent());
        anFriendsCommentQueryWrapper.ge(
                null != listNoteRequest.getCreateTimeStart(), "create_time", listNoteRequest.getCreateTimeStart());
        anFriendsCommentQueryWrapper.le(
                null != listNoteRequest.getCreateTimeEnd(), "create_time", listNoteRequest.getCreateTimeEnd());
        anFriendsCommentQueryWrapper.orderByDesc("create_time");
        IPage<AnNote> page = page(listNotePage, anFriendsCommentQueryWrapper);
        List<AnNote> records = page.getRecords();
        return new BasePageResult<>(records, page.getTotal());
    }

    @Override
    public AnNote getNoteDetail(Long id) {
        AnNote anNote = getById(id);
        if (null == anNote) {
            throw new RuntimeException("内容不存在！");
        }
        incrNoteViewCount(id);
        anNote.setViewCount(anNote.getViewCount()+1);
        return anNote;
    }

    @Override
    public void likeNote(Long id, HttpServletRequest request) {
        String ipAddr = IPUtils.getIpAddr(request);
        boolean success = RedisUtil.StringOps.setIfAbsent(RedisKey.USER_NOTE_LIKE_REPEAT_CACHE_KEY + ipAddr+id, "1", 1, TimeUnit.DAYS);
        if (success){
            anNoteMapper.likeNote(id);
        }else {
            throw new RuntimeException("24小时内只能点赞一次(*￣︶￣)");
        }
    }

    @Async
    public void incrNoteViewCount(Long id){
        anNoteMapper.incrNoteViewCount(id);
    }
}
