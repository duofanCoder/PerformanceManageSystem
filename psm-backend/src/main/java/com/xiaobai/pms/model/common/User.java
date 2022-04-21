package com.xiaobai.pms.model.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xiaobai.pms.model.business.Department;
import com.xiaobai.pms.model.business.Group;
import com.xiaobai.pms.model.business.Position;
import com.xiaobai.pms.model.business.Record;
import com.xiaobai.pms.model.enums.UserRoles;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.nio.file.attribute.GroupPrincipal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Arpit Khandelwal.
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "sys_user",
        indexes = @Index(
                name = "idx_user_username",
                columnList = "username",
                unique = true)
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date createTime;
    private Date updateTime;
    private String name;
    private String password;
    private String username;
    private String mobile;
    private boolean gender;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    // appraisal records completed by current user
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Record> assessmentRecordList;

    // the current user's evaluated record
    @OneToMany(mappedBy = "audience", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Record> assessedRecordList;

    @Enumerated(EnumType.STRING)
    private UserRoles role;

    @ManyToMany(mappedBy = "userList")
    private Collection<Group> users;
}
