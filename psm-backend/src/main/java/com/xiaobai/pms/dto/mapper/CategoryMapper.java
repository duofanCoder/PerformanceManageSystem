package com.xiaobai.pms.dto.mapper;

import cn.hutool.core.util.ObjectUtil;
import com.xiaobai.pms.dto.model.business.CategoryDto;
import com.xiaobai.pms.model.business.Category;
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
public class CategoryMapper {
    @Autowired
    private ModelMapper modelMapper;

    public CategoryDto toCategoryDto(Category model) {
        if (ObjectUtil.isEmpty(model)) {
            return null;
        }
        return new CategoryDto().setId(model.getId())
                .setName(model.getName())
                .setDescription(model.getDescription())
                .setCreateTime(model.getCreateTime())
                .setUpdateTime(model.getUpdateTime());
    }
}
