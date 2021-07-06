package com.scqzy.springdataelasticsearch.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @Description:
 * @Author 盛春强
 * @Date 2021/7/6 11:56
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "index_02")
public class Article {
    @Id
    @Field(type = FieldType.Long,store = true)
    private long id;
    @Field(type = FieldType.Text,store = true,analyzer = "ik_smart")
    private String title;
    @Field(type = FieldType.Text,store = true,analyzer = "ik_smart")
    private  String content;
}
