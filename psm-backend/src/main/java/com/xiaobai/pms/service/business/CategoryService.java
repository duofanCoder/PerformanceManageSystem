package com.xiaobai.pms.service.business;


import com.xiaobai.pms.controller.v1.condition.business.CategoryCondition;
import com.xiaobai.pms.dto.model.business.CategoryDto;
import com.xiaobai.pms.dto.model.common.PageDto;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2022/3/29
 */

public interface CategoryService {

    void removeByPrimaryKey(Long[] primaryKey);

    CategoryDto save(CategoryDto dto);

    CategoryDto getByPrimaryKey(Long primaryKey);

    PageDto<CategoryDto> findPageByCondition(CategoryCondition condition);

    CategoryDto update(CategoryDto dto);
}
