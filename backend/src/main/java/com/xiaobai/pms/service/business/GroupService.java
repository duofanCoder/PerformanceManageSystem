package com.xiaobai.pms.service.business;


import com.xiaobai.pms.controller.v1.api.GroupController;
import com.xiaobai.pms.controller.v1.condition.business.GroupCondition;
import com.xiaobai.pms.dto.model.business.GroupDto;
import com.xiaobai.pms.dto.model.common.PageDto;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2022/3/29
 */

public interface GroupService {

    void removeByPrimaryKey(Long[] primaryKey);

    GroupDto save(GroupDto dto);

    GroupDto getByPrimaryKey(Long primaryKey);

    PageDto<GroupDto> findPageByCondition(GroupCondition condition);

    GroupDto update(GroupDto dto);

    void addUser(GroupController.AddUserRequest request);
}
