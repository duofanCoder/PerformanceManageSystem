package com.xiaobai.pms.controller.v1.api;


import com.xiaobai.pms.controller.v1.condition.business.MethodCondition;
import com.xiaobai.pms.controller.v1.request.MethodRequest;
import com.xiaobai.pms.dto.model.business.MethodDto;
import com.xiaobai.pms.dto.response.Response;
import com.xiaobai.pms.service.business.MethodService;
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
@RequestMapping("/api/v1/method")
@Api(value = "application")
public class MethodController {

    @Autowired
    private MethodService methodService;
    @Autowired
    private ModelMapper modelMapper;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("remove")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    public Response remove(Long[] primaryKeys) {
        methodService.removeByPrimaryKey(primaryKeys);
        return Response.ok();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("update")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    public Response update(@RequestBody MethodRequest methodRequest) {
        return Response.ok().setData(methodService.update(modelMapper.map(methodRequest, MethodDto.class)));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("save")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    public Response save(@RequestBody MethodRequest methodRequest) {
        return Response.ok().setData(methodService.save(modelMapper.map(methodRequest, MethodDto.class)));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("query")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    public Response save(@RequestBody MethodCondition condition) {
        return Response.ok().setData(methodService.findPageByCondition(condition));
    }

}
