package com.xiaobai.pms.service.business;


import com.xiaobai.pms.controller.v1.condition.SchemeCondition;
import com.xiaobai.pms.dto.model.business.SchemeDto;
import com.xiaobai.pms.dto.model.common.PageDto;
import com.xiaobai.pms.model.business.Scheme;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2022/3/29
 */

public interface SchemeService {

    void removeByPrimaryKey(Long[] primaryKey);

    SchemeDto save(Scheme dto);

    SchemeDto getByPrimaryKey(Long primaryKey);

    PageDto<SchemeDto> findPageByCondition(SchemeCondition condition);

    SchemeDto update(Scheme model);
}
