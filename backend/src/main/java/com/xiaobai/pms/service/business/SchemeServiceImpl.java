package com.xiaobai.pms.service.business;


import com.xiaobai.pms.controller.v1.condition.SchemeCondition;
import com.xiaobai.pms.dto.mapper.SchemeMapper;
import com.xiaobai.pms.dto.model.business.SchemeDto;
import com.xiaobai.pms.dto.model.common.PageDto;
import com.xiaobai.pms.exception.type.OwnerException;
import com.xiaobai.pms.model.business.*;
import com.xiaobai.pms.repository.business.SchemeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2022/3/29
 */
@Service
public class SchemeServiceImpl implements SchemeService {
    @Autowired
    private SchemeRepository SchemeRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private SchemeMapper schemeMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByPrimaryKey(Long[] primaryKey) {
        for (int i = 0; i < primaryKey.length; i++) {
            SchemeRepository.deleteById(primaryKey[i]);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    // 新增操作
    public SchemeDto save(Scheme model) {
        model.setCreateTime(new Date()).setUpdateTime(new Date());
        return modelMapper.map(SchemeRepository.save(model), SchemeDto.class);
    }

    @Override
    public SchemeDto getByPrimaryKey(Long primaryKey) {
        return modelMapper.map(SchemeRepository.findById(primaryKey), SchemeDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PageDto<SchemeDto> findPageByCondition(SchemeCondition condition) {
        LinkedList<SchemeDto> list = new LinkedList<>();
        PageRequest pageable = PageRequest.of(condition.getPageNum(), condition.getPageSize(), Sort.by("createTime"));
        Specification<Scheme> spec = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (!condition.getName().isEmpty()) {
                predicateList.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + condition.getName() + "%"));
            }
            return criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()])).getRestriction();
        };
        Page<Scheme> modelPages = SchemeRepository.findAll(spec, pageable);
        for (Scheme campaign : modelPages.getContent()) {
            list.add(schemeMapper.toSchemeDto(campaign));
        }
        return new PageDto<SchemeDto>()
                .setCurrentPage(pageable.getPageNumber())
                .setTotalPage(modelPages.getTotalPages())
                .setData(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SchemeDto update(Scheme model) {
        Scheme inModel = SchemeRepository.findById(model.getId()).orElseThrow(
                () -> new OwnerException("修改的方案不存在！")
        );
        modelMapper.map(model, inModel);
        inModel.setQuotaList(model.getQuotaList())
                .setMethod(model.getMethod())
                .setFrequency(model.getFrequency())
                .setGroup(model.getGroup());
        return modelMapper.map(SchemeRepository.save(inModel), SchemeDto.class);
    }

}
