package com.xiaobai.pms.controller.v1.api;

import com.xiaobai.pms.controller.v1.condition.business.DepartmentCondition;
import com.xiaobai.pms.controller.v1.request.DepartmentRequest;
import com.xiaobai.pms.dto.model.business.DepartmentDto;
import com.xiaobai.pms.dto.response.Response;
import com.xiaobai.pms.service.business.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/api/v1/department")
@Api(value = "application")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private ModelMapper modelMapper;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("remove")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    public Response remove(Long[] primaryKeys) {
        departmentService.removeByPrimaryKey(primaryKeys);
        return Response.ok();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("update")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    public Response update(@RequestBody DepartmentRequest departmentRequest) {
        return Response.ok().setData(departmentService.update(modelMapper.map(departmentRequest, DepartmentDto.class)));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("save")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    public Response save(@RequestBody DepartmentRequest departmentRequest) {
        return Response.ok().setData(departmentService.save(modelMapper.map(departmentRequest, DepartmentDto.class)));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("query")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    public Response save(@RequestBody DepartmentCondition condition) {
        return Response.ok().setData(departmentService.findPageByCondition(condition));
    }

}
