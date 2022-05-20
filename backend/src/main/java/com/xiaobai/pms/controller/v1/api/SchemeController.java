package com.xiaobai.pms.controller.v1.api;


import com.xiaobai.pms.controller.v1.condition.SchemeCondition;
import com.xiaobai.pms.controller.v1.request.SchemeRequest;
import com.xiaobai.pms.dto.mapper.SchemeMapper;
import com.xiaobai.pms.dto.model.business.SchemeDto;
import com.xiaobai.pms.dto.response.Response;
import com.xiaobai.pms.model.business.Scheme;
import com.xiaobai.pms.service.business.SchemeService;
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
@RequestMapping("/api/v1/scheme")
@Api(value = "application")
public class SchemeController {

    @Autowired
    private SchemeService schemeService;
    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private SchemeMapper schemeMapper;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("remove")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    public Response remove(Long[] primaryKeys) {
        schemeService.removeByPrimaryKey(primaryKeys);
        return Response.ok();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("update")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    public Response update(@RequestBody SchemeRequest schemeRequest) {
        Scheme model = schemeMapper.toModel(schemeRequest);
        return Response.ok().setData(schemeService.update(model));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("save")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    public Response save(@RequestBody SchemeRequest schemeRequest) {
        return Response.ok().setData(schemeService.save(schemeMapper.toModel(schemeRequest)));
    }

    @PostMapping("query")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    public Response save(@RequestBody SchemeCondition condition) {
        return Response.ok().setData(schemeService.findPageByCondition(condition));
    }

}
