package com.xiaobai.pms.service.common;

import com.xiaobai.pms.controller.v1.condition.common.UserCondition;
import com.xiaobai.pms.dto.mapper.UserMapper;
import com.xiaobai.pms.dto.model.common.PageDto;
import com.xiaobai.pms.dto.model.common.UserDto;
import com.xiaobai.pms.exception.type.OwnerException;
import com.xiaobai.pms.model.business.Department;
import com.xiaobai.pms.model.business.Position;
import com.xiaobai.pms.model.common.User;
import com.xiaobai.pms.model.enums.UserRoles;
import com.xiaobai.pms.repository.common.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.*;


@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDto signup(UserDto userDto) {
        UserRoles userRole;
        User user = userRepository.findByUsername(userDto.getUsername());
        if (user == null) {
            if (userDto.isAdmin()) {
                userRole = UserRoles.ADMIN;
            } else {
                userRole = UserRoles.COMMON;
            }

            User model = modelMapper.map(userDto, User.class);
            model.setPassword(bCryptPasswordEncoder.encode("123456"))
                    .setCreateTime(new Date())
                    .setUpdateTime(new Date())
                    .setRole(userRole);
            return userMapper.toUserDto(userRepository.save(model));
        } else {
            throw new RuntimeException("账号已存在！");
        }
    }

    /**
     * Search an existing user
     *
     * @param email
     * @return
     */
    @Override
    @Transactional
    public UserDto findUserByUsername(String email) {
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(email));
        if (user.isPresent()) {
            return userMapper.toUserDto(user.get());
        } else {
            return null;
        }
    }

    /**
     * Update User Profile
     *
     * @param userDto
     * @return
     */
    @Override
    public UserDto updateProfile(UserDto userDto) {
        User userModel = userRepository.findByUsername(userDto.getUsername());
        userModel.setName(userDto.getName())
                .setGender(userDto.getGender())
                .setUpdateTime(new Date())
                .setMobile(userDto.getMobile());
        return userMapper.toUserDto(userRepository.save(userModel));
    }

    /**
     * Change Password
     *
     * @param userDto
     * @param newPassword
     * @return
     */
    @Override
    public UserDto changePassword(UserDto userDto, String newPassword) {
        User userModel = userRepository.findByUsername(userDto.getUsername());
        userModel.setPassword(bCryptPasswordEncoder.encode(newPassword));
        return userMapper.toUserDto(userRepository.save(userModel));
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByPrimaryKey(Long[] primaryKey) {
        for (int i = 0; i < primaryKey.length; i++) {
            userRepository.deleteById(primaryKey[i]);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    // 新增操作
    public UserDto save(UserDto dto) {
        return signup(dto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDto getByPrimaryKey(Long primaryKey) {
        return userMapper.toUserDto(userRepository.findById(primaryKey).get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PageDto<UserDto> findPageByCondition(UserCondition condition) {
        LinkedList<UserDto> list = new LinkedList<>();
        PageRequest pageable = PageRequest.of(condition.getPageNum(), condition.getPageSize(), Sort.by("createTime"));
        Specification<User> spec = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (!condition.getName().isEmpty()) {
                predicateList.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + condition.getName() + "%"));
            }
            if (condition.getPositionId() != null && condition.getPositionId() != 0) {
                predicateList.add(criteriaBuilder.equal(root.get("position").as(Position.class), new Position().setId(condition.getPositionId())));
            }
            if (condition.getDepartmentId() != null && condition.getDepartmentId() != 0) {
                predicateList.add(criteriaBuilder.equal(root.get("department").as(Department.class), new Department().setId(condition.getDepartmentId())));
            }
            return criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()])).getRestriction();
        };
        Page<User> modelPages = userRepository.findAll(spec, pageable);
        for (User campaign : modelPages.getContent()) {
            list.add(userMapper.toUserDto(campaign));
        }
        return new PageDto<UserDto>()
                .setCurrentPage(pageable.getPageNumber())
                .setTotalPage(modelPages.getTotalPages())
                .setData(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDto update(UserDto dto) {
        User model = userRepository.findById(dto.getId()).orElseThrow(
                () -> new OwnerException("修改的用户不存在！")
        );
        model.setId(dto.getId())
                .setName(dto.getName())
                .setGender(dto.getGender())
                .setMobile(dto.getMobile())
                .setPosition(dto.getPosition() == null ? null : new Position().setId(dto.getPosition().getId()))
                .setDepartment(dto.getDepartment() == null ? null : new Department().setId(dto.getDepartment().getId()));
        return modelMapper.map(userRepository.save(model), UserDto.class);
    }

}
