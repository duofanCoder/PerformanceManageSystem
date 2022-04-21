package com.xiaobai.pms.service.common;

import com.xiaobai.pms.controller.v1.condition.common.UserCondition;
import com.xiaobai.pms.dto.model.common.UserDto;
import com.xiaobai.pms.dto.model.common.PageDto;
import com.xiaobai.pms.dto.model.common.UserDto;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Arpit Khandelwal.
 */
public interface UserService {
    /**
     * Register a new user
     *
     * @param userDto
     * @return
     */
    UserDto signup(UserDto userDto);

    /**
     * Search an existing user
     *
     * @param email
     * @return
     */
    UserDto findUserByUsername(String email);

    /**
     * Update profile of the user
     *
     * @param userDto
     * @return
     */
    UserDto updateProfile(UserDto userDto);

    /**
     * Update password
     *
     * @param newPassword
     * @return
     */
    UserDto changePassword(UserDto userDto, String newPassword);



    void removeByPrimaryKey(Long[] primaryKey);

    UserDto save(UserDto dto);

    UserDto getByPrimaryKey(Long primaryKey);

    PageDto<UserDto> findPageByCondition(UserCondition condition);

    UserDto update(UserDto dto);

}
