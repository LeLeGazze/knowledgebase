package com.castle.fortress.admin.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface EsFileRepository extends ElasticsearchRepository<EsFileDto,String> {
}
