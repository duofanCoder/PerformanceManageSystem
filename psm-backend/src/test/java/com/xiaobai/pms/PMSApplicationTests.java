package com.xiaobai.pms;

import com.xiaobai.pms.model.business.News;
import com.xiaobai.pms.repository.business.DepartmentRepository;
import com.xiaobai.pms.repository.business.NewsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PMSApplicationTests {

    // niaho
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private NewsRepository newsRepository;

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void contextLoads() {
        for (News news : newsRepository.findAll()) {
            System.out.println(news.getCategory());
            System.out.println(news);
        }
    }
}
