package com.udd.dto;

import java.util.List;

import com.udd.lucene.model.Index;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchResultsDTO {

	private List<Index> content;
	private long totalElements;
	
}
