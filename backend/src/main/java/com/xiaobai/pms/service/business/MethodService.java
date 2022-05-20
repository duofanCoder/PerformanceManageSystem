package com.xiaobai.pms.service.business;


import com.xiaobai.pms.controller.v1.condition.business.MethodCondition;
import com.xiaobai.pms.dto.model.business.MethodDto;
import com.xiaobai.pms.dto.model.common.PageDto;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2022/3/29
 */

public interface MethodService {

    void removeByPrimaryKey(Long[] primaryKey);

    MethodDto save(MethodDto dto);

    MethodDto getByPrimaryKey(Long primaryKey);

    PageDto<MethodDto> findPageByCondition(MethodCondition condition);

    MethodDto update(MethodDto dto);
}
