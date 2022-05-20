package com.xiaobai.pms.dto.mapper;

import com.xiaobai.pms.dto.model.business.CategoryDto;
import com.xiaobai.pms.dto.model.business.NewsDto;
import com.xiaobai.pms.model.business.Category;
import com.xiaobai.pms.model.business.News;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2022/4/4
 */
@Component
public class NewsMapper {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    public NewsDto toNewsDto(News model) {
        CategoryDto categoryDto = categoryMapper.toCategoryDto(model.getCategory());
        return new NewsDto()
                .setId(model.getId())
                .setName(model.getName())
                .setDescription(model.getDescription())
                .setCreateTime(model.getCreateTime())
                .setUpdateTime(model.getUpdateTime())
                .setCategory(categoryDto);
    }
}
