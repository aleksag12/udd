package com.udd.lucene.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import com.udd.lucene.model.CVIndex;

public interface CVIndexRepository extends ElasticsearchRepository<CVIndex, Long> {

}
