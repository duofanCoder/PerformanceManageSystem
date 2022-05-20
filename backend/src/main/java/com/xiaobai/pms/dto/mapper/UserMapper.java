package com.xiaobai.pms.dto.mapper;

import com.xiaobai.pms.controller.v1.request.UserRequest;
import com.xiaobai.pms.dto.model.business.DepartmentDto;
import com.xiaobai.pms.dto.model.business.PositionDto;
import com.xiaobai.pms.dto.model.common.UserDto;
import com.xiaobai.pms.model.common.User;
import org.springframework.stereotype.Component;


/**
 * Created by Arpit Khandelwal.
 */
@Component
public class UserMapper {

    public UserDto toUserDto(User user) {
        if (user == null) {
            return null;
        }
        return new UserDto()
                .setId(user.getId())
                .setMobile(user.getMobile())
                .setName(user.getName())
                .setUsername(user.getUsername())
                .setGender(user.isGender())
                .setPassword(user.getPassword())
                .setCreateTime(user.getCreateTime())
                .setUpdateTime(user.getUpdateTime())
                .setPosition(user.getPosition() != null ? new PositionDto().setId(user.getPosition().getId()).setName(user.getPosition().getName()) : null)
                .setDepartment(user.getDepartment() != null ? new DepartmentDto().setId(user.getDepartment().getId()).setName(user.getDepartment().getName()) : null)
                .setRole(user.getRole());
    }

    public UserDto toUserDto(UserRequest user) {
        if (user == null) {
            return null;
        }
        return new UserDto()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setMobile(user.getMobile())
                .setName(user.getName())
                .setGender(user.isGender())
                .setPosition(new PositionDto().setId(user.getPosition().getId()))
                .setDepartment(new DepartmentDto().setId(user.getDepartment().getId()));
    }
}
