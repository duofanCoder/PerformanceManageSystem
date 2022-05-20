package com.xiaobai.pms.service.business;


import com.xiaobai.pms.controller.v1.condition.business.NewsCondition;
import com.xiaobai.pms.dto.mapper.NewsMapper;
import com.xiaobai.pms.dto.model.business.NewsDto;
import com.xiaobai.pms.dto.model.common.PageDto;
import com.xiaobai.pms.exception.type.OwnerException;
import com.xiaobai.pms.model.business.Category;
import com.xiaobai.pms.model.business.News;
import com.xiaobai.pms.repository.business.NewsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedList;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2022/3/29
 */
@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private NewsMapper newsMapper;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByPrimaryKey(Long[] primaryKey) {
        for (int i = 0; i < primaryKey.length; i++) {
            newsRepository.deleteById(primaryKey[i]);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    // 新增操作
    public NewsDto save(NewsDto dto) {
        News model = modelMapper.map(dto, News.class);
        model.setCreateTime(new Date()).setUpdateTime(new Date());
        return modelMapper.map(newsRepository.save(model), NewsDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public NewsDto getByPrimaryKey(Long primaryKey) {
        return modelMapper.map(newsRepository.findById(primaryKey), NewsDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PageDto<NewsDto> findPageByCondition(NewsCondition condition) {
        LinkedList<NewsDto> list = new LinkedList<>();
        PageRequest pageable = PageRequest.of(condition.getPageNum(), condition.getPageSize());
        Page<News> modelPages = newsRepository.findByNameContaining(condition.getName(), pageable);
        for (News campaign : modelPages.getContent()) {
            list.add(newsMapper.toNewsDto(campaign));
        }
        return new PageDto<NewsDto>()
                .setCurrentPage(pageable.getPageNumber())
                .setTotalPage(modelPages.getTotalPages())
                .setData(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public NewsDto update(NewsDto dto) {
        News model = newsRepository.findById(dto.getId()).orElseThrow(
                () -> new OwnerException("修改的部门不存在！")
        );
        model.setName(dto.getName()).setDescription(dto.getDescription()).setUpdateTime(new Date())
                .setCategory(new Category().setId(dto.getCategory().getId()));
        return modelMapper.map(newsRepository.save(model), NewsDto.class);
    }

}
