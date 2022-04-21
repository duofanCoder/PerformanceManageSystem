package com.xiaobai.pms.service.business;


import com.xiaobai.pms.controller.v1.condition.business.MethodCondition;
import com.xiaobai.pms.dto.model.business.MethodDto;
import com.xiaobai.pms.dto.model.common.PageDto;
import com.xiaobai.pms.exception.type.OwnerException;
import com.xiaobai.pms.model.business.Method;
import com.xiaobai.pms.repository.business.MethodRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedList;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2022/3/29
 */
@Service
public class MethodServiceImpl implements MethodService {
    @Autowired
    private MethodRepository methodRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByPrimaryKey(Long[] primaryKey) {
        for (int i = 0; i < primaryKey.length; i++) {
            methodRepository.deleteById(primaryKey[i]);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    // 新增操作
    public MethodDto save(MethodDto dto) {
        Method model = modelMapper.map(dto, Method.class);
        model.setCreateTime(new Date()).setUpdateTime(new Date());
        return modelMapper.map(methodRepository.save(model), MethodDto.class);
    }

    @Override
    public MethodDto getByPrimaryKey(Long primaryKey) {
        return modelMapper.map(methodRepository.findById(primaryKey), MethodDto.class);
    }

    @Override
    public PageDto<MethodDto> findPageByCondition(MethodCondition condition) {
        LinkedList<MethodDto> list = new LinkedList<>();
        PageRequest pageable = PageRequest.of(condition.getPageNum(), condition.getPageSize());
        Page<Method> modelPages = methodRepository.findByNameContaining(condition.getName(), pageable);
        for (Method campaign : modelPages.getContent()) {
            list.add(modelMapper.map(campaign, MethodDto.class));
        }
        return new PageDto<MethodDto>()
                .setCurrentPage(pageable.getPageNumber())
                .setTotalPage(modelPages.getTotalPages())
                .setData(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MethodDto update(MethodDto dto) {
        Method model = methodRepository.findById(dto.getId()).orElseThrow(
                 () -> new OwnerException("方式不存在！")
        );
        model.setName(dto.getName()).setDescription(dto.getDescription()).setUpdateTime(new Date());
        return modelMapper.map(methodRepository.save(model), MethodDto.class);
    }

}
