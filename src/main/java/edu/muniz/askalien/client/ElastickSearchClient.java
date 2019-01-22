package edu.muniz.askalien.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "elastick-search-askalien", url = "${uri.elasticksearch.host}")
public interface ElastickSearchClient {
		
	@PutMapping(value="/answers-v3/_doc/{id}")
	public void putAnswer(@RequestBody SearchRequest request,@PathVariable(name="id") int id);

}
