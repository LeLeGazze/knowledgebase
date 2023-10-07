package com.castle.fortress.admin.check.service;

import com.castle.fortress.admin.check.es.EsCheckLine;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EsCheckLineRepository extends ElasticsearchRepository<EsCheckLine,String> {
}
