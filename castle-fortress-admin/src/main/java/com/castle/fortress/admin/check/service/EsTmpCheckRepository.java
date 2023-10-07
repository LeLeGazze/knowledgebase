package com.castle.fortress.admin.check.service;

import com.castle.fortress.admin.check.es.EsTmpCheck;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EsTmpCheckRepository extends ElasticsearchRepository<EsTmpCheck,String> {
}
