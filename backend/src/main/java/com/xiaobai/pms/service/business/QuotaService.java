package com.xiaobai.pms.service.business;


import com.xiaobai.pms.controller.v1.condition.business.QuotaCondition;
import com.xiaobai.pms.dto.model.business.QuotaDto;
import com.xiaobai.pms.dto.model.common.PageDto;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2022/3/29
 */

public interface QuotaService {

    void removeByPrimaryKey(Long[] primaryKey);

    QuotaDto save(QuotaDto dto);

    QuotaDto getByPrimaryKey(Long primaryKey);

    PageDto<QuotaDto> findPageByCondition(QuotaCondition condition);

    QuotaDto update(QuotaDto dto);
}
