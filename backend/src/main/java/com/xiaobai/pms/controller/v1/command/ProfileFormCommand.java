package com.xiaobai.pms.controller.v1.command;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
public class ProfileFormCommand {
    @NotBlank
    private String name;

    @NotBlank
    private String mobile;

    @NotBlank
    private boolean gender;
}
