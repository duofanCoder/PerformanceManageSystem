package com.xiaobai.pms.controller.v1.api;


import com.xiaobai.pms.controller.v1.condition.business.CategoryCondition;
import com.xiaobai.pms.controller.v1.request.CategoryRequest;
import com.xiaobai.pms.dto.model.business.CategoryDto;
import com.xiaobai.pms.dto.response.Response;
import com.xiaobai.pms.service.business.CategoryService;
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
@RequestMapping("/api/v1/category")
@Api(value = "application")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ModelMapper modelMapper;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("remove")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    public Response remove(Long[] primaryKeys) {
        categoryService.removeByPrimaryKey(primaryKeys);
        return Response.ok();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("update")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    public Response update(@RequestBody CategoryRequest categoryRequest) {
        return Response.ok().setData(categoryService.update(modelMapper.map(categoryRequest, CategoryDto.class)));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("save")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    public Response save(@RequestBody CategoryRequest categoryRequest) {
        return Response.ok().setData(categoryService.save(modelMapper.map(categoryRequest, CategoryDto.class)));
    }

    @PostMapping("query")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    public Response save(@RequestBody CategoryCondition condition) {
        return Response.ok().setData(categoryService.findPageByCondition(condition));
    }

}
