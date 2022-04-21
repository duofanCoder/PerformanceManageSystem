package com.xiaobai.pms.service.business;


import com.xiaobai.pms.controller.v1.condition.business.QuotaCondition;
import com.xiaobai.pms.dto.model.business.QuotaDto;
import com.xiaobai.pms.dto.model.common.PageDto;
import com.xiaobai.pms.exception.type.OwnerException;
import com.xiaobai.pms.model.business.Quota;
import com.xiaobai.pms.repository.business.QuotaRepository;
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
public class QuotaServiceImpl implements QuotaService {
    @Autowired
    private QuotaRepository quotaRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByPrimaryKey(Long[] primaryKey) {
        for (int i = 0; i < primaryKey.length; i++) {
            quotaRepository.deleteById(primaryKey[i]);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    // 新增操作
    public QuotaDto save(QuotaDto dto) {
        Quota model = modelMapper.map(dto, Quota.class);
        model.setCreateTime(new Date()).setUpdateTime(new Date());
        return modelMapper.map(quotaRepository.save(model), QuotaDto.class);
    }

    @Override
    public QuotaDto getByPrimaryKey(Long primaryKey) {
        return modelMapper.map(quotaRepository.findById(primaryKey), QuotaDto.class);
    }

    @Override
    public PageDto<QuotaDto> findPageByCondition(QuotaCondition condition) {
        LinkedList<QuotaDto> list = new LinkedList<>();
        PageRequest pageable = PageRequest.of(condition.getPageNum(), condition.getPageSize());
        Page<Quota> modelPages = quotaRepository.findByNameContaining(condition.getName(), pageable);
        for (Quota campaign : modelPages.getContent()) {
            list.add(modelMapper.map(campaign, QuotaDto.class));
        }
        return new PageDto<QuotaDto>()
                .setCurrentPage(pageable.getPageNumber())
                .setTotalPage(modelPages.getTotalPages())
                .setData(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public QuotaDto update(QuotaDto dto) {
        Quota model = quotaRepository.findById(dto.getId()).orElseThrow(
                () -> new OwnerException("修改的部门不存在！")
        );
        model.setName(dto.getName()).setDescription(dto.getDescription()).setUpdateTime(new Date())
                .setScore(dto.getScore())
                .setWeight(dto.getWeight());
        return modelMapper.map(quotaRepository.save(model), QuotaDto.class);
    }

}
