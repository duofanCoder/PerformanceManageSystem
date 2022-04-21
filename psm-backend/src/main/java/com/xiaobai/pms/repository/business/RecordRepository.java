package com.xiaobai.pms.repository.business;


import com.xiaobai.pms.model.business.Record;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2022/3/29
 */
public interface RecordRepository extends PagingAndSortingRepository<Record, Long>, CrudRepository<Record, Long>, JpaSpecificationExecutor<Record> {

}
