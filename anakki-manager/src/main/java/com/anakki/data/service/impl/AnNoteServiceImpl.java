package com.anakki.data.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.anakki.data.bean.common.BaseContext;
import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.bean.constant.CosBucketNameConst;
import com.anakki.data.bean.constant.CosPathConst;
import com.anakki.data.bean.constant.RedisKey;
import com.anakki.data.entity.AnNote;
import com.anakki.data.entity.AnUser;
import com.anakki.data.entity.request.*;
import com.anakki.data.entity.response.AnNoteDetailResponse;
import com.anakki.data.entity.response.AnUserDetailForNoteResponse;
import com.anakki.data.mapper.AnNoteMapper;
import com.anakki.data.service.AnIpAddressService;
import com.anakki.data.service.AnNoteService;
import com.anakki.data.service.AnUserService;
import com.anakki.data.utils.IPUtils;
import com.anakki.data.utils.common.COSUtil;
import com.anakki.data.utils.common.HtmlUtil;
import com.anakki.data.utils.common.RedisUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

        String currentNickname = BaseContext.getCurrentNickname();
        AnUser user = anUserService.getByNickname(currentNickname);
        Long currentUserId = user.getId();
        AnNote anNote=null;
        Long noteId = createNoteRequest.getId();
        if (null != noteId) {
            anNote = getById(noteId);
            if (!anNote.getCreateBy().equals(currentUserId)) {
                Set<Long> authorIdSet = getAuthorIdList(anNote.getAuthorIds());
                if (!authorIdSet.contains(currentUserId)) {
                    throw new RuntimeException("无权限修改，请申请成为协作人");
                }
            }
            anNote.setCoverImage(HtmlUtil.getFirstImg(createNoteRequest.getContent()));
            anNote.setTitle(HtmlUtil.getFirstH1(createNoteRequest.getContent()));
            anNote.setDescription(HtmlUtil.getFirstP(createNoteRequest.getContent()));
            anNote.setStatus("COMMON");

            updateById(anNote);
        } else {
            anNote = new AnNote();
            BeanUtils.copyProperties(createNoteRequest, anNote);
            anNote.setAuthor(user.getNickname());
            anNote.setLocation(ipAddr);
            anNote.setCreateBy(currentUserId);
            ArrayList<Long> ids = new ArrayList<>();
            ids.add(currentUserId);
            anNote.setAuthorIds(getAuthorIdListString(ids));
            anNote.setCoverImage(HtmlUtil.getFirstImg(createNoteRequest.getContent()));
            anNote.setTitle(HtmlUtil.getFirstH1(createNoteRequest.getContent()));
            anNote.setDescription(HtmlUtil.getFirstP(createNoteRequest.getContent()));
            anNote.setStatus("COMMON");
            save(anNote);
        }
        return true;
    }

    @Override
    public Boolean addAuthorToNotes(AddNoteOtherAuthorRequest addNoteOtherAuthorRequest) {

        String currentNickname = BaseContext.getCurrentNickname();
        AnUser user = anUserService.getByNickname(currentNickname);
        Long currentUserId = user.getId();
        Long targetNoteId = addNoteOtherAuthorRequest.getTargetNoteId();
        List<Long> newAuthorIds = addNoteOtherAuthorRequest.getAuthorIds();
        AnNote note = getById(targetNoteId);
        if (null==note){
            throw new RuntimeException("笔记不存在");
        }
        Long createBy = note.getCreateBy();
        if (!currentUserId.equals(createBy)){
            throw new RuntimeException("只有作者可以添加协作人");
        }

        String authorIds = note.getAuthorIds();
        Set<Long> oldAuthorIdList = getAuthorIdList(authorIds);

        if (CollectionUtils.isEmpty(newAuthorIds)) {
            throw new RuntimeException("未添加用户");
        }
        HashSet<Long> newAuthorSet = new HashSet<>(newAuthorIds);
        List<AnUser> existUsers = anUserService.listByIds(newAuthorSet);
        if (CollectionUtils.isEmpty(existUsers)){
            throw new RuntimeException("用户不存在");
        }

        existUsers.forEach(existUser->{
            if (newAuthorSet.contains(existUser.getId())){
                oldAuthorIdList.add(existUser.getId());
            }
        });
        note.setAuthorIds(getAuthorIdListString(new ArrayList<>(oldAuthorIdList)));
        return updateById(note);
    }

    @Override
    public Boolean removeAuthorToNotes(RemoveNoteOtherAuthorRequest request) {

        String currentNickname = BaseContext.getCurrentNickname();
        AnUser user = anUserService.getByNickname(currentNickname);
        Long currentUserId = user.getId();
        Long targetNoteId = request.getTargetNoteId();
        List<Long> newAuthorIds = request.getAuthorIds();
        AnNote note = getById(targetNoteId);
        if (null==note){
            throw new RuntimeException("笔记不存在");
        }
        Long createBy = note.getCreateBy();
        if (!currentUserId.equals(createBy)){
            throw new RuntimeException("只有作者可以移除协作人");
        }

        String authorIds = note.getAuthorIds();
        Set<Long> oldAuthorIdList = getAuthorIdList(authorIds);

        if (CollectionUtils.isEmpty(newAuthorIds)) {
            throw new RuntimeException("未添加用户");
        }
        HashSet<Long> newAuthorSet = new HashSet<>(newAuthorIds);
        List<AnUser> existUsers = anUserService.listByIds(newAuthorSet);
        if (CollectionUtils.isEmpty(existUsers)){
            throw new RuntimeException("用户不存在");
        }

        existUsers.forEach(existUser->{
            if (newAuthorSet.contains(existUser.getId())){
                oldAuthorIdList.remove(existUser.getId());
            }
        });
        note.setAuthorIds(getAuthorIdListString(new ArrayList<>(oldAuthorIdList)));
        return updateById(note);
    }
    @Override
    public String getAuthorIdListString(List<Long> ids) {
        if (!CollectionUtils.isEmpty(ids)) {
            return JSONArray.toJSONString(ids);
        } else {
            return "[]";
        }
    }

    @Override
    public Set<Long> getAuthorIdList(String ids) {
        if (StringUtils.isNotBlank(ids)) {
            return new HashSet<>(JSONArray.parseArray(ids, Long.class));
        } else {
            return new HashSet<>();
        }
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
        String currentNickname = BaseContext.getCurrentNickname();
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
    public AnNoteDetailResponse getNoteDetail(Long id) {
        AnNote anNote = getById(id);
        if (null == anNote) {
            throw new RuntimeException("内容不存在！");
        }
        incrNoteViewCount(id);
        anNote.setViewCount(anNote.getViewCount() + 1);
        AnNoteDetailResponse anNoteDetailResponse = new AnNoteDetailResponse();
        BeanUtils.copyProperties(anNote,anNoteDetailResponse);
        String authorIds = anNote.getAuthorIds();
        Set<Long> authorIdList = getAuthorIdList(authorIds);
        anNoteDetailResponse.setAuthorsDetail(getUserDetailForNote(authorIdList));
        return anNoteDetailResponse;
    }
    @Override
    public List<AnUserDetailForNoteResponse> getUserDetailForNote(Set<Long> authorIdList){
        if (CollectionUtils.isEmpty(authorIdList)){
            return new ArrayList<>();
        }
        List<AnUser> users = anUserService.listByIds(authorIdList);
        return com.anakki.data.utils.common.BeanUtils.copyBeanList(users, AnUserDetailForNoteResponse.class);
    }
    @Override
    public void likeNote(Long id, HttpServletRequest request) {
        String ipAddr = IPUtils.getIpAddr(request);
        boolean success = RedisUtil.StringOps.setIfAbsent(RedisKey.USER_NOTE_LIKE_REPEAT_CACHE_KEY + ipAddr + id, "1", 1, TimeUnit.DAYS);
        if (success) {
            anNoteMapper.likeNote(id);
        } else {
            throw new RuntimeException("24小时内只能点赞一次(*￣︶￣)");
        }
    }

    @Async
    public void incrNoteViewCount(Long id) {
        anNoteMapper.incrNoteViewCount(id);
    }
}
