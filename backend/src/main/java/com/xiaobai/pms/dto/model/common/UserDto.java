package com.xiaobai.pms.dto.model.common;

import com.fasterxml.jackson.annotation.*;
import com.xiaobai.pms.dto.model.business.DepartmentDto;
import com.xiaobai.pms.dto.model.business.PositionDto;
import com.xiaobai.pms.model.business.Position;
import com.xiaobai.pms.model.enums.UserRoles;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Arpit Khandelwal.
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private Long id;
    private String name;
    private String username;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date createTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date updateTime;
    private Boolean gender;
    private String mobile;
    private PositionDto position;
    private DepartmentDto department;
    @JsonEnumDefaultValue
    private UserRoles role;
    @JsonIgnore
    private String password;
    private boolean isAdmin;
}
