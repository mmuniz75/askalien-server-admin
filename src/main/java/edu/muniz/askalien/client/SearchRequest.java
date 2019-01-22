package edu.muniz.askalien.client;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchRequest {
	
	private String subject;
	private String content;

}
