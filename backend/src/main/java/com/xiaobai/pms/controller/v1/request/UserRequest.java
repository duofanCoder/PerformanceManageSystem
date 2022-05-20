package com.xiaobai.pms.controller.v1.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xiaobai.pms.dto.model.business.DepartmentDto;
import com.xiaobai.pms.dto.model.business.PositionDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2022/4/4
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRequest {
    private Long id;
    @NotNull(message = "{constraints.NotEmpty.message}")
    private String name;
    private String mobile;
    @NotNull(message = "{constraints.NotEmpty.message}")
    private PositionDto position;
    @NotNull(message = "{constraints.NotEmpty.message}")
    private DepartmentDto department;
    private boolean gender;
    @NotNull(message = "{constraints.NotEmpty.message}")
    private String username;

}
