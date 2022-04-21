package com.xiaobai.pms.service.business;


import com.xiaobai.pms.controller.v1.condition.business.PositionCondition;
import com.xiaobai.pms.dto.model.business.PositionDto;
import com.xiaobai.pms.dto.model.common.PageDto;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2022/3/29
 */

public interface PositionService {

    void removeByPrimaryKey(Long[] primaryKey);

    PositionDto save(PositionDto dto);

    PositionDto getByPrimaryKey(Long primaryKey);

    PageDto<PositionDto> findPageByCondition(PositionCondition condition);

    PositionDto update(PositionDto dto);
}
