package com.xiaobai.pms.service.business;


import com.xiaobai.pms.controller.v1.condition.business.RecordCondition;
import com.xiaobai.pms.dto.model.business.RecordDto;
import com.xiaobai.pms.dto.model.common.PageDto;
import com.xiaobai.pms.model.business.Record;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2022/3/29
 */

public interface RecordService {

    void removeByPrimaryKey(Long[] primaryKey);

    void save(Record dto);

    RecordDto getByPrimaryKey(Long primaryKey);

    PageDto<RecordDto> findPageByCondition(RecordCondition condition);

}
