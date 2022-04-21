package com.xiaobai.pms.service.business;


import com.xiaobai.pms.controller.v1.condition.business.NewsCondition;
import com.xiaobai.pms.dto.model.business.NewsDto;
import com.xiaobai.pms.dto.model.common.PageDto;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2022/3/29
 */

public interface NewsService {

    void removeByPrimaryKey(Long[] primaryKey);

    NewsDto save(NewsDto dto);

    NewsDto getByPrimaryKey(Long primaryKey);

    PageDto<NewsDto> findPageByCondition(NewsCondition condition);

    NewsDto update(NewsDto dto);
}
