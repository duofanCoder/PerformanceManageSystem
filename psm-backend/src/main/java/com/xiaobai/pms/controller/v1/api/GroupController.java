package com.xiaobai.pms.controller.v1.api;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xiaobai.pms.controller.v1.condition.business.GroupCondition;
import com.xiaobai.pms.controller.v1.condition.common.UserCondition;
import com.xiaobai.pms.controller.v1.request.GroupRequest;
import com.xiaobai.pms.dto.model.business.GroupDto;
import com.xiaobai.pms.dto.response.Response;
import com.xiaobai.pms.service.business.GroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2022/3/29
 */
@RestController
@RequestMapping("/api/v1/group")
@Api(value = "application")
public class GroupController {

    @Autowired
    private GroupService groupService;
    @Autowired
    private ModelMapper modelMapper;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("remove")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    public Response remove(Long[] primaryKeys) {
        groupService.removeByPrimaryKey(primaryKeys);
        return Response.ok();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("update")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    public Response update(@RequestBody GroupRequest groupRequest) {
        return Response.ok().setData(groupService.update(modelMapper.map(groupRequest, GroupDto.class)));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("save")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    public Response save(@RequestBody GroupRequest groupRequest) {
        return Response.ok().setData(groupService.save(modelMapper.map(groupRequest, GroupDto.class)));
    }

    @PostMapping("query")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    public Response save(@RequestBody GroupCondition condition) {
        return Response.ok().setData(groupService.findPageByCondition(condition));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{id}")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    public Response getMember(Long id) {
        return Response.ok().setData(groupService.getByPrimaryKey(id));
    }


    @Getter
    @Setter
    @Accessors(chain = true)
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AddUserRequest {
        @NotNull(message = "{constraints.NotEmpty.message}")
        private Long groupId;
        @NotNull(message = "{constraints.NotEmpty.message}")
        private List<Long> userList;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("user/add")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    public Response save(@RequestBody AddUserRequest request) {
        groupService.addUser(request);
        return Response.ok();
    }
}
