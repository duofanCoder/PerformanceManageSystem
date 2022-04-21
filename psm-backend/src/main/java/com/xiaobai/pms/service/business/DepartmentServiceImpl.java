package com.xiaobai.pms.service.business;

import com.xiaobai.pms.controller.v1.condition.business.DepartmentCondition;
import com.xiaobai.pms.dto.model.business.DepartmentDto;
import com.xiaobai.pms.dto.model.common.PageDto;
import com.xiaobai.pms.exception.type.OwnerException;
import com.xiaobai.pms.model.business.Department;
import com.xiaobai.pms.repository.business.DepartmentRepository;
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
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByPrimaryKey(Long[] primaryKey) {
        for (int i = 0; i < primaryKey.length; i++) {
            departmentRepository.deleteById(primaryKey[i]);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    // 新增操作
    public DepartmentDto save(DepartmentDto dto) {
        Department model = modelMapper.map(dto, Department.class);
        model.setCreateTime(new Date()).setUpdateTime(new Date());
        return modelMapper.map(departmentRepository.save(model), DepartmentDto.class);
    }

    @Override
    public DepartmentDto getByPrimaryKey(Long primaryKey) {
        return modelMapper.map(departmentRepository.findById(primaryKey), DepartmentDto.class);
    }

    @Override
    public PageDto<DepartmentDto> findPageByCondition(DepartmentCondition condition) {
        LinkedList<DepartmentDto> list = new LinkedList<>();
        PageRequest pageable = PageRequest.of(condition.getPageNum(), condition.getPageSize());
        Page<Department> modelPages = departmentRepository.findByNameContaining(condition.getName(), pageable);
        for (Department campaign : modelPages.getContent()) {
            list.add(modelMapper.map(campaign, DepartmentDto.class));
        }
        return new PageDto<DepartmentDto>()
                .setCurrentPage(pageable.getPageNumber())
                .setTotalPage(modelPages.getTotalPages())
                .setData(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DepartmentDto update(DepartmentDto dto) {
        Department model = departmentRepository.findById(dto.getId()).orElseThrow(
                () -> new OwnerException("修改的部门不存在！")
        );
        model.setName(dto.getName()).setDescription(dto.getDescription()).setUpdateTime(new Date());
        return modelMapper.map(departmentRepository.save(model), DepartmentDto.class);
    }

}
