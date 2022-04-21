package com.xiaobai.pms.repository.business;

import com.xiaobai.pms.model.business.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2022/3/29
 */
public interface DepartmentRepository extends CrudRepository<Department, Long> {
    Page<Department> findByNameContaining(String title, Pageable pageable);
}
