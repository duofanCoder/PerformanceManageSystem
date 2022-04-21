package com.xiaobai.pms.controller.v1.api;


import com.xiaobai.pms.controller.v1.condition.business.RecordCondition;
import com.xiaobai.pms.controller.v1.request.RecordRequest;
import com.xiaobai.pms.dto.mapper.RecordMapper;
import com.xiaobai.pms.dto.model.business.RecordDto;
import com.xiaobai.pms.dto.model.common.UserDto;
import com.xiaobai.pms.dto.response.Response;
import com.xiaobai.pms.service.business.RecordService;
import com.xiaobai.pms.service.common.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2022/3/29
 */
@RestController
@RequestMapping("/api/v1/record")
@Api(value = "application")
public class RecordController {

    @Autowired
    private RecordService recordService;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RecordMapper recordMapper;

    @Autowired
    private UserService userService;

    @PostMapping("remove")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    public Response remove(Long[] primaryKeys) {
        recordService.removeByPrimaryKey(primaryKeys);
        return Response.ok();
    }

    @PostMapping("save")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    public Response save(@RequestBody RecordRequest recordRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findUserByUsername(auth.getName());
        recordRequest.setOwner(userDto.getId());
        recordService.save(recordMapper.toModel(recordRequest));
        return Response.ok();
    }

    @PostMapping("query")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    public Response save(@RequestBody RecordCondition condition) {
        return Response.ok().setData(recordService.findPageByCondition(condition));
    }

}
