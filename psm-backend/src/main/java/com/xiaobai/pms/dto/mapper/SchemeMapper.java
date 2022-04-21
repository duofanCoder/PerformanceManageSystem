package com.xiaobai.pms.dto.mapper;

import cn.hutool.setting.GroupedMap;
import com.xiaobai.pms.controller.v1.request.SchemeRequest;
import com.xiaobai.pms.dto.model.business.GroupDto;
import com.xiaobai.pms.dto.model.business.MethodDto;
import com.xiaobai.pms.dto.model.business.QuotaDto;
import com.xiaobai.pms.dto.model.business.SchemeDto;
import com.xiaobai.pms.model.business.Group;
import com.xiaobai.pms.model.business.Method;
import com.xiaobai.pms.model.business.Quota;
import com.xiaobai.pms.model.business.Scheme;
import com.xiaobai.pms.repository.business.SchemeRepository;
import org.hibernate.sql.Template;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2022/4/4
 */
@Component
public class SchemeMapper {

    @Autowired
    private ModelMapper modelMapper;


    public SchemeDto toSchemeDto(Scheme model) {
        SchemeDto dto = modelMapper.map(model, SchemeDto.class);
        dto.setGroup(modelMapper.map(model.getGroup(), GroupDto.class))
                .setQuotaList(model.getQuotaList().stream().map(item -> modelMapper.map(item, QuotaDto.class)).collect(Collectors.toList()))
                .setMethod(modelMapper.map(model.getMethod(), MethodDto.class));

        dto.setGroupId(model.getGroup().getId())
                .setMethodId(model.getMethod().getId())
                .setQuotaIds(model.getQuotaList().stream().map(item -> item.getId())
                        .collect(Collectors.toList()).toArray(new Long[dto.getQuotaList().size()]));
        return dto;
    }

    public SchemeDto toSchemeDto(SchemeRequest request) {
        SchemeDto dto = modelMapper.map(request, SchemeDto.class);
        dto.setGroup(new GroupDto().setId(request.getGroupId()))
                .setMethod(new MethodDto().setId(request.getGroupId()))
                .setQuotaList(Arrays.stream(request.getQuotaIds()).map(item -> modelMapper.map(item, QuotaDto.class)).collect(Collectors.toList()));
        return dto;
    }


    public Scheme toModel(SchemeRequest request) {
        Scheme model = modelMapper.map(request, Scheme.class);
        model.setFrequency(request.getFrequency());
        model.setGroup(new Group().setId(request.getGroupId()))
                .setMethod(new Method().setId(request.getGroupId()))
                .setQuotaList(Arrays.stream(request.getQuotaIds()).map(item -> new Quota().setId(item)).collect(Collectors.toList()));
        return model;
    }
}
