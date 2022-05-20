package com.xiaobai.pms.dto.mapper;

import com.alibaba.fastjson.JSONObject;
import com.xiaobai.pms.controller.v1.request.RecordRequest;
import com.xiaobai.pms.dto.model.business.RecordDto;
import com.xiaobai.pms.model.business.Record;
import com.xiaobai.pms.model.business.Scheme;
import com.xiaobai.pms.model.common.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springfox.documentation.spring.web.json.Json;

import java.util.Map;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2022/4/10
 */
@Component
public class RecordMapper {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SchemeMapper schemeMapper;

    public Record toModel(RecordRequest request) {
        Map<Integer, Integer> map = request.getScoreMap();
        String json = JSONObject.toJSONString(map);
        return new Record()
                .setId(request.getId())
                .setAudience(new User().setId(request.getAudience()))
                .setOwner(new User().setId(request.getOwner()))
                .setScheme(new Scheme().setId(request.getSchemeId()))
                .setScoreJson(json);
    }

    public RecordDto toRecordDto(Record model) {
        RecordDto dto = modelMapper.map(model, RecordDto.class);
        return dto.setAudience(userMapper.toUserDto(model.getAudience()))
                .setOwner(userMapper.toUserDto(model.getOwner()))
                .setScheme(schemeMapper.toSchemeDto(model.getScheme()))
                .setScoreMap((Map<String, Integer>) JSONObject.parse(model.getScoreJson()));
    }
}
