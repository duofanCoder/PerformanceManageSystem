package com.xiaobai.pms.service.business;


import com.xiaobai.pms.controller.v1.condition.business.PositionCondition;
import com.xiaobai.pms.dto.model.business.PositionDto;
import com.xiaobai.pms.dto.model.common.PageDto;
import com.xiaobai.pms.exception.type.OwnerException;
import com.xiaobai.pms.model.business.Position;
import com.xiaobai.pms.repository.business.PositionRepository;
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
public class PositionServiceImpl implements PositionService {
    @Autowired
    private PositionRepository PositionRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByPrimaryKey(Long[] primaryKey) {
        for (int i = 0; i < primaryKey.length; i++) {
            PositionRepository.deleteById(primaryKey[i]);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    // 新增操作
    public PositionDto save(PositionDto dto) {
        Position model = modelMapper.map(dto, Position.class);
        model.setCreateTime(new Date()).setUpdateTime(new Date());
        return modelMapper.map(PositionRepository.save(model), PositionDto.class);
    }

    @Override
    public PositionDto getByPrimaryKey(Long primaryKey) {
        return modelMapper.map(PositionRepository.findById(primaryKey), PositionDto.class);
    }

    @Override
    public PageDto<PositionDto> findPageByCondition(PositionCondition condition) {
        LinkedList<PositionDto> list = new LinkedList<>();
        PageRequest pageable = PageRequest.of(condition.getPageNum(), condition.getPageSize());
        Page<Position> modelPages = PositionRepository.findByNameContaining(condition.getName(), pageable);
        for (Position campaign : modelPages.getContent()) {
            list.add(modelMapper.map(campaign, PositionDto.class));
        }
        return new PageDto<PositionDto>()
                .setCurrentPage(pageable.getPageNumber())
                .setTotalPage(modelPages.getTotalPages())
                .setData(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PositionDto update(PositionDto dto) {
        Position model = PositionRepository.findById(dto.getId()).orElseThrow(
                () -> new OwnerException("修改的部门不存在！")
        );
        model.setName(dto.getName()).setDescription(dto.getDescription()).setUpdateTime(new Date());
        return modelMapper.map(PositionRepository.save(model), PositionDto.class);
    }

}
