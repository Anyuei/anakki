package com.anakki.data.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.anakki.data.bean.common.BaseContext;
import com.anakki.data.bean.common.BasePageResult;
import com.anakki.data.bean.constant.CosBucketNameConst;
import com.anakki.data.bean.constant.CosPathConst;
import com.anakki.data.bean.constant.RedisKey;
import com.anakki.data.entity.AnNote;
import com.anakki.data.entity.AnNoteDraft;
import com.anakki.data.entity.AnNoteGroupRel;
import com.anakki.data.entity.AnUser;
import com.anakki.data.entity.request.*;
import com.anakki.data.entity.response.AnNoteDetailResponse;
import com.anakki.data.entity.response.AnNoteResponse;
import com.anakki.data.entity.response.AnUserDetailForNoteResponse;
import com.anakki.data.mapper.AnNoteDraftMapper;
import com.anakki.data.mapper.AnNoteMapper;
import com.anakki.data.service.*;
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
import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    private AnNoteGroupService anNoteGroupService;
    @Autowired
    private AnNoteGroupRelService anNoteGroupRelService;

    @Autowired
    private  AnNoteDraftMapper anNoteDraftMapper;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(CreateNoteRequest createNoteRequest, HttpServletRequest request) {
        String ipAddr = IPUtils.getIpAddr(request);

        String currentNickname = BaseContext.getCurrentNickname();
        AnUser user = anUserService.getByNickname(currentNickname);
        List<Long> authorIds = createNoteRequest.getAuthorIds();
        Long currentUserId = user.getId();
        AnNote anNote = new AnNote();
        BeanUtils.copyProperties(createNoteRequest, anNote);
        anNote.setAuthor(user.getNickname());
        anNote.setLocation(ipAddr);
        anNote.setCreateBy(currentUserId);

        authorIds.add(currentUserId);
        anNote.setAuthorIds(getAuthorIdListString(authorIds));
        anNote.setCoverImage(HtmlUtil.getFirstImg(createNoteRequest.getContent()));
        anNote.setTitle(HtmlUtil.getFirstH1(createNoteRequest.getContent()));
        anNote.setDescription(HtmlUtil.getFirstP(createNoteRequest.getContent()));
        anNote.setStatus("COMMON");
        save(anNote);
        anNoteGroupRelService.saveNoteToGroup(createNoteRequest.getNoteGroupIds(),anNote.getId());
        return anNote.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long update(UpdateNoteRequest updateNoteRequest, HttpServletRequest request) {
        String ipAddr = IPUtils.getIpAddr(request);

        String currentNickname = BaseContext.getCurrentNickname();
        AnUser user = anUserService.getByNickname(currentNickname);
        Long currentUserId = user.getId();
        String content = updateNoteRequest.getContent();
        AnNote anNote = new AnNote();
        BeanUtils.copyProperties(updateNoteRequest, anNote);
        anNote.setAuthor(user.getNickname());
        anNote.setLocation(ipAddr);
        anNote.setCoverImage(HtmlUtil.getFirstImg(content));
        anNote.setTitle(HtmlUtil.getFirstH1(content));
        anNote.setDescription(HtmlUtil.getFirstP(content));
        anNote.setStatus("COMMON");
        updateById(anNote);

        //每次修改保存版本信息
        AnNoteDraft anNoteDraft = new AnNoteDraft();
        anNoteDraft.setNoteId(anNote.getId());
        anNoteDraft.setContent(content);
        anNoteDraft.setCreateBy(currentUserId);
        anNoteDraftMapper.insert(anNoteDraft);

        return anNote.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
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
    @Transactional(rollbackFor = Exception.class)
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
    public BasePageResult<AnNoteResponse> listNotes(ListNoteRequest listNoteRequest) {
        // 创建分页对象，current 表示当前页，size 表示每页大小
        IPage<AnNoteResponse> page = new Page<>(listNoteRequest.getCurrent(), listNoteRequest.getSize());

        listNoteRequest.setStatus("COMMON");
        // 调用mapper方法获取分页结果
        IPage<AnNoteResponse> notePage = anNoteMapper.listNotes(page, listNoteRequest);

        // 获取分页记录和总数
        List<AnNoteResponse> records = notePage.getRecords();
        long total = notePage.getTotal();

        return new BasePageResult<>(records, total);
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
    @Transactional(rollbackFor = Exception.class)
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


        QueryWrapper<AnNoteGroupRel> groupRelQueryWrapper = new QueryWrapper<>();
        groupRelQueryWrapper.eq("note_id",note.getId());
        List<AnNoteGroupRel> anNoteGroupRels = anNoteGroupRelService.list(groupRelQueryWrapper);
        for (AnNoteGroupRel anNoteGroupRel : anNoteGroupRels) {
            anNoteGroupService.existAndDecrNoteCount(anNoteGroupRel.getNoteGroupId());
        }
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

    @Override
    public BasePageResult<AnNote> listNotesForManage(ListNoteForManageRequest request) {
        // 构建分页对象，传入当前页和每页大小
        Page<AnNote> page = new Page<>(request.getCurrent(), request.getSize());
        // 构建查询条件
        IPage<AnNote> notePage = anNoteMapper.listNotesForManage(page, request);

        // 构建分页结果
        return new BasePageResult<>(notePage.getRecords(),notePage.getTotal());
    }

    @Async
    public void incrNoteViewCount(Long id) {
        anNoteMapper.incrNoteViewCount(id);
    }
}
