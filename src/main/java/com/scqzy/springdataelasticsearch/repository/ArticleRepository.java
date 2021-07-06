package com.scqzy.springdataelasticsearch.repository;

import com.scqzy.springdataelasticsearch.entity.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Author 盛春强
 * @Date 2021/7/6 12:00
 */
@Repository
public interface ArticleRepository extends ElasticsearchRepository<Article, Long> {

    List<Article> findByTitle(String title);

    List<Article> findByTitleOrContent(String title, String content);

    List<Article> findByTitleOrContent(String title, String content, Pageable pageable);
}
