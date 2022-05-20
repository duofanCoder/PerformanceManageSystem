package com.xiaobai.pms.service.business;

import com.xiaobai.pms.controller.v1.condition.business.DepartmentCondition;
import com.xiaobai.pms.dto.model.business.DepartmentDto;
import com.xiaobai.pms.dto.model.common.PageDto;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2022/3/29
 */

public interface DepartmentService {

    void removeByPrimaryKey(Long[] primaryKey);

    DepartmentDto save(DepartmentDto dto);

    DepartmentDto getByPrimaryKey(Long primaryKey);

    PageDto<DepartmentDto> findPageByCondition(DepartmentCondition condition);

    DepartmentDto update(DepartmentDto dto);
}
