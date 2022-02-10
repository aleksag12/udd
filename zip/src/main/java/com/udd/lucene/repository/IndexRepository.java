package com.udd.lucene.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.udd.lucene.model.Index;


public interface IndexRepository extends ElasticsearchRepository<Index, Long> {

}
