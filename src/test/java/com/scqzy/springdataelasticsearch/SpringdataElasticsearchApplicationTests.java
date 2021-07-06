package com.scqzy.springdataelasticsearch;

import com.apifan.common.random.source.OtherSource;
import com.scqzy.springdataelasticsearch.entity.Article;
import com.scqzy.springdataelasticsearch.repository.ArticleRepository;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
class SpringdataElasticsearchApplicationTests {

    @Autowired
    private ArticleRepository articleRepository;

    @Qualifier("restTemplate")
    @Autowired
    private ElasticsearchRestTemplate template;


    @Test
    void contextLoads() {
    }

    @Test
    public void createIndex() {
        template.indexOps(Article.class).create();
    }

    @Test
    void addDocument() {
        Article article = new Article();
        article.setId(1L);
        article.setTitle(OtherSource.getInstance().randomChineseSentence());
        article.setContent(OtherSource.getInstance().randomChineseSentence());
        articleRepository.save(article);
    }

    @Test
    void addDocumentBatch() {
        List<Article> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list.add(new Article(i, OtherSource.getInstance().randomChineseSentence(),
                    OtherSource.getInstance().randomChineseSentence()));
        }
        List<IndexQuery> indexQueryList = new ArrayList<>();
        list.forEach(article -> {
            IndexQuery indexQuery = new IndexQuery();
            indexQuery.setObject(article);
            indexQueryList.add(indexQuery);
        });
        template.bulkIndex(indexQueryList, IndexCoordinates.of("index_02"));
    }

    @Test
    void deleteDocument() {
        articleRepository.deleteAll();
    }

    @Test
    void modifyDocument() {
        template.save(new Article(0L, OtherSource.getInstance().randomChineseSentence(),
                OtherSource.getInstance().randomChineseSentence()));
    }

    @Test
    void findAll() {
        Iterable<Article> all = articleRepository.findAll();
        all.forEach(System.out::print);
    }

    @Test
    void findById() {
        Optional<Article> articleOptional = articleRepository.findById(0L);
        Article article = articleOptional.orElse(new Article());
        System.out.println(article);
    }

    @Test
    void findByTitle() {
        List<Article> list = articleRepository.findByTitle("咨文照常赌咒外相");
        list.forEach(System.out::println);
    }

    @Test
    void findByTitleOrContent() {
        List<Article> list = articleRepository.findByTitleOrContent("咨文照常赌咒外相", "头天八成加深干劲昼夜");
        list.forEach(System.out::println);
    }

    @Test
    void findByTitleOrContentByPage() {
        List<Article> list = articleRepository.findByTitleOrContent("咨文照常赌咒外相", "头天", PageRequest.of(0, 10));
        list.forEach(System.out::println);
    }

    @Test
    void nativeSearchQuery() {
        NativeSearchQuery build = new NativeSearchQueryBuilder().withQuery(QueryBuilders.queryStringQuery("咨文照常赌咒外相").defaultField("title"
        )).withPageable(PageRequest.of(0, 10)).build();
        SearchHits<Article> searchHits = template.search(build, Article.class);
        searchHits.getSearchHits().forEach(System.out::println);

    }
}
