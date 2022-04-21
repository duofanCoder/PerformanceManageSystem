package com.xiaobai.pms.model.business;

import com.xiaobai.pms.model.enums.BizFrequency;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2022/3/29
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "biz_scheme")
public class Scheme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date createTime;
    private Date updateTime;
    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private BizFrequency frequency;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "biz_scheme_quota",
            joinColumns = {@JoinColumn(name = "scheme_id")},
            inverseJoinColumns = {@JoinColumn(name = "quota_id")})
    @ToString.Exclude
    private List<Quota> quotaList;

    @OneToOne
    @JoinColumn(name = "method_id", referencedColumnName = "id")
    private Method method;

    @OneToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    // 被评分组
    private Group group;
    @OneToMany(mappedBy = "scheme", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Record> recordList;


}
