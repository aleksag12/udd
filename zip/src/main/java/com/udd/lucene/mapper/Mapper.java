package com.udd.lucene.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import com.google.gson.Gson;
import com.udd.lucene.model.CVIndex;
import com.udd.lucene.model.Index;

public class Mapper implements SearchResultMapper {

	public Integer totalElements;
	
	public Mapper() {
		this.totalElements = 0;
	}

	@Override
	public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
		List<Index> result = new ArrayList<>();

		for (SearchHit searchHit : searchResponse.getHits()) {
			if (searchResponse.getHits().getHits().length <= 0) {
				return null;
			}

			Gson gson = new Gson();
			Map<String, Object> source = searchHit.getSourceAsMap();

			@SuppressWarnings("unchecked")
			Map<String, Object> cvMap = gson.fromJson(gson.toJson(source.get("cv")), Map.class);

			CVIndex cv = new CVIndex(Math.round((double) cvMap.get("id")), (String) cvMap.get("content"),
					(String) cvMap.get("url"));

			String highValueCv;
			try {
				highValueCv = "..." + searchHit.getHighlightFields().get("cv.content").fragments()[0].toString()
						.replaceAll("[\\n\\r\\t]+", " ").trim().replaceAll(" +", " ") + "...";
			} catch (Exception e) {
				String[] words = cv.getContent().replaceAll("[\\n\\r\\t]+", " ").trim().replaceAll(" +", " ")
						.split(" ");
				if (words.length > 40) {
					highValueCv = String.join(" ", Arrays.copyOfRange(words, 0, 39));
				} else {
					highValueCv = String.join(" ", Arrays.copyOfRange(words, 0, words.length));
				}
			}

			cv.setContent(highValueCv);

			Index indexingUnit = new Index(Long.valueOf(searchHit.getId()),
					(String) source.get("firstName"), (String) source.get("lastName"),
					(String) source.get("education").toString(), (String) source.get("informations"), cv, null);

			result.add(indexingUnit);
		}
		if (result.size() > 0) {
			this.totalElements = (int) searchResponse.getHits().getTotalHits();
			return new AggregatedPageImpl(result);
		}

		return null;
	}

}
