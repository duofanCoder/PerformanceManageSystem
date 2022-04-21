package com.xiaobai.pms.service.business;


import com.xiaobai.pms.controller.v1.api.GroupController;
import com.xiaobai.pms.controller.v1.condition.business.GroupCondition;
import com.xiaobai.pms.dto.model.business.GroupDto;
import com.xiaobai.pms.dto.model.common.PageDto;
import com.xiaobai.pms.exception.type.OwnerException;
import com.xiaobai.pms.model.business.Group;
import com.xiaobai.pms.model.common.User;
import com.xiaobai.pms.repository.business.GroupRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.acl.Owner;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.LinkedList;
import java.util.Optional;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2022/3/29
 */
@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByPrimaryKey(Long[] primaryKey) {
        for (int i = 0; i < primaryKey.length; i++) {
            groupRepository.deleteById(primaryKey[i]);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    // 新增操作
    public GroupDto save(GroupDto dto) {
        Group model = modelMapper.map(dto, Group.class);
        model.setCreateTime(new Date()).setUpdateTime(new Date());
        return modelMapper.map(groupRepository.save(model), GroupDto.class);
    }

    @Override
    public GroupDto getByPrimaryKey(Long primaryKey) {
        return modelMapper.map(groupRepository.findById(primaryKey), GroupDto.class);
    }

    @Override
    public PageDto<GroupDto> findPageByCondition(GroupCondition condition) {
        LinkedList<GroupDto> list = new LinkedList<>();
        PageRequest pageable = PageRequest.of(condition.getPageNum(), condition.getPageSize());
        Page<Group> modelPages = groupRepository.findByNameContaining(condition.getName(), pageable);
        for (Group campaign : modelPages.getContent()) {
            list.add(modelMapper.map(campaign, GroupDto.class));
        }
        return new PageDto<GroupDto>()
                .setCurrentPage(pageable.getPageNumber())
                .setTotalPage(modelPages.getTotalPages())
                .setData(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GroupDto update(GroupDto dto) {
        Group model = groupRepository.findById(dto.getId()).orElseThrow(
                () -> new OwnerException("分组不存在！")
        );
        model.setName(dto.getName()).setDescription(dto.getDescription()).setUpdateTime(new Date());
        return modelMapper.map(groupRepository.save(model), GroupDto.class);
    }

    @Override
    public void addUser(GroupController.AddUserRequest request) {
        Group group = groupRepository.findById(request.getGroupId()).orElseThrow(
                () -> new OwnerException("分组不存在！")
        );
        for (Long userId : request.getUserList()) {
            if (group.getUserList() == null) {
                group.setUserList(new LinkedList<>());
            }
            group.getUserList().add(new User().setId(userId));
        }
        try {
            groupRepository.save(group);
        } catch (Exception e) {
            throw new OwnerException("成员已添加请勿重复添加！");
        }
    }
}
