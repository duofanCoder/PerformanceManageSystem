package com.xiaobai.pms.service.business;


import com.xiaobai.pms.controller.v1.condition.business.RecordCondition;
import com.xiaobai.pms.dto.mapper.RecordMapper;
import com.xiaobai.pms.dto.model.business.RecordDto;
import com.xiaobai.pms.dto.model.business.SchemeDto;
import com.xiaobai.pms.dto.model.common.PageDto;
import com.xiaobai.pms.dto.model.common.UserDto;
import com.xiaobai.pms.exception.type.OwnerException;
import com.xiaobai.pms.model.business.Record;
import com.xiaobai.pms.model.business.Scheme;
import com.xiaobai.pms.model.common.User;
import com.xiaobai.pms.model.enums.UserRoles;
import com.xiaobai.pms.repository.business.RecordRepository;
import com.xiaobai.pms.service.common.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.security.acl.Owner;
import java.util.*;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2022/3/29
 */
@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private RecordMapper recordMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByPrimaryKey(Long[] primaryKey) {
        for (int i = 0; i < primaryKey.length; i++) {
            recordRepository.deleteById(primaryKey[i]);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    // 新增操作
    public void save(Record model) {
        model.setCreateTime(new Date()).setUpdateTime(new Date());
        try {
            recordRepository.save(model);
        } catch (Exception e) {
            throw new OwnerException("您已评过此人！");
        }
    }

    @Override
    public RecordDto getByPrimaryKey(Long primaryKey) {
        return modelMapper.map(recordRepository.findById(primaryKey), RecordDto.class);
    }

    @Override
    public PageDto<RecordDto> findPageByCondition(RecordCondition condition) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findUserByUsername(auth.getName());
        if (userDto.getRole() != UserRoles.ADMIN) {
            condition.setAudienceId(userDto.getId());
        }
        LinkedList<RecordDto> list = new LinkedList<>();
        PageRequest pageable = PageRequest.of(condition.getPageNum(), condition.getPageSize(), Sort.by("createTime"));
        Specification<Record> spec = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (!condition.getName().isEmpty()) {
                predicateList.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + condition.getName() + "%"));
            }
            if (condition.getAudienceId() != 0) {
                predicateList.add(criteriaBuilder.equal(root.get("audience").as(User.class), new User().setId(condition.getAudienceId())));
            }
            if (condition.getOwnerId() != 0) {
                predicateList.add(criteriaBuilder.equal(root.get("owner").as(User.class), new User().setId(condition.getOwnerId())));
            }
            if (condition.getSchemeId() != 0) {
                predicateList.add(criteriaBuilder.equal(root.get("scheme").as(Scheme.class), new Scheme().setId(condition.getSchemeId())));
            }
            return criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()])).getRestriction();
        };
        Page<Record> modelPages = recordRepository.findAll(spec, pageable);
        HashMap<String, RecordDto> map = new HashMap<>();
        if (userDto.getRole() != UserRoles.ADMIN) {
            List<Record> recordList = modelPages.getContent();
            for (Record record : recordList) {
                // 新来的
                RecordDto c = recordMapper.toRecordDto(record);
                if (!map.containsKey(record.getScheme().getId().toString())) {
                    map.put(record.getScheme().getId().toString(), c);
                } else {
                    // map 里存的
                    RecordDto v = map.get(record.getScheme().getId().toString());
                    for (String k : v.getScoreMap().keySet()) {
                        v.getScoreMap().put(k, (v.getScoreMap().get(k) + c.getScoreMap().get(k)) / 2);
                    }
                    map.put(record.getScheme().getId().toString(), v);
                }
            }
            for (String sid : map.keySet()) {
                list.add(map.get(sid));
            }
        } else {
            for (Record campaign : modelPages.getContent()) {
                list.add(recordMapper.toRecordDto(campaign));
            }

        }
        return new PageDto<RecordDto>()
                .setCurrentPage(pageable.getPageNumber())
                .setTotalPage(modelPages.getTotalPages())
                .setData(list);
    }
}
