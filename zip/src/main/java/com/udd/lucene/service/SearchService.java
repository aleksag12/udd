package com.udd.lucene.service;

import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import com.udd.dto.FieldDTO;
import com.udd.dto.GeoLocationRequest;
import com.udd.dto.SearchResultsDTO;
import com.udd.lucene.mapper.Mapper;
import com.udd.lucene.model.Index;
import com.udd.model.Operator;
import com.udd.service.LocationService;

@Service
public class SearchService {

	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	@Autowired
	private LocationService locationService;
	
	public SearchResultsDTO simpleSearch(String criterion, Pageable pageable) {
		NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
		BoolQueryBuilder queryParams = new BoolQueryBuilder();
		
		queryParams.should(QueryBuilders.commonTermsQuery("firstName", criterion));
		queryParams.should(QueryBuilders.commonTermsQuery("lastName", criterion));
		queryParams.should(QueryBuilders.commonTermsQuery("education", criterion));

		NestedQueryBuilder nestedCvQuery = nestedCvBuilder(criterion);
		queryParams.should(nestedCvQuery);

		SearchQuery query = nativeSearchQueryBuilder
			.withQuery(queryParams)
			.withHighlightFields(new HighlightBuilder.Field("cv.content")
			.preTags("<b>")
			.postTags("</b>")
			.numOfFragments(1)
			.fragmentSize(500))
			.withPageable(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize())).build();
		
		Mapper mapper = new Mapper();
		AggregatedPage<Index> results = elasticsearchTemplate.queryForPage(query, Index.class, mapper);

		if (results == null) {
			return new SearchResultsDTO(new ArrayList<Index>(), 0);
		}
		return new SearchResultsDTO(results.getContent(), mapper.totalElements);
    }
	
	
	public SearchResultsDTO geoLocationSearch(GeoLocationRequest geoLocationRequest, Pageable pageable) throws Exception {
        GeoPoint location = locationService.forwardGeocoding(geoLocationRequest.getPlace());
        GeoDistanceQueryBuilder geoDistanceBuilder = new GeoDistanceQueryBuilder("geoPoint")
                .point(location.getLat(), location.getLon())
                .distance(geoLocationRequest.getKilometres(), DistanceUnit.KILOMETERS);
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withFilter(geoDistanceBuilder)
                .withPageable(pageable)
                .build();
        Mapper mapper = new Mapper();
        AggregatedPage<Index> results = elasticsearchTemplate.queryForPage(searchQuery, Index.class, mapper);
        if (results == null) {
			return new SearchResultsDTO(new ArrayList<Index>(), 0);
		}
		return new SearchResultsDTO(results.getContent(), mapper.totalElements);     
    }
	
	public SearchResultsDTO advancedSearch(List<FieldDTO> searchParams, Pageable pageable) {
		NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
		BoolQueryBuilder queryParams = QueryBuilders.boolQuery();

		for (FieldDTO searchParam : searchParams) {
			
			
			//range za obrazovanje
			if (searchParam.getName().equals("education")) {
				if (searchParam.getOperator().equals(Operator.AND)) {
					queryParams.must(QueryBuilders.rangeQuery("education").from(searchParam.getValue()).to(searchParam.getValue2()));
				} else {
					queryParams.should(QueryBuilders.rangeQuery("education").from(searchParam.getValue()).to(searchParam.getValue2()));
				}
				
				
				
			//nested za cv	
			} else if (searchParam.getName().equals("cv")) {
				buildNestedParam(queryParams, searchParam.isPhrase(), searchParam.getName(),searchParam.getValue(), searchParam.getOperator());
				
				//sva ostala polja
			} else {
				if (searchParam.getOperator().equals(Operator.AND)) {
                    if (searchParam.isPhrase()) {
                    	queryParams.must(QueryBuilders.matchPhraseQuery(searchParam.getName(), searchParam.getValue()));
                    } else {
                    	queryParams.must(QueryBuilders.matchQuery(searchParam.getName(), searchParam.getValue()));
                    }
                } else {
                    if (searchParam.isPhrase()) {
                    	queryParams.should(QueryBuilders.matchPhraseQuery(searchParam.getName(), searchParam.getValue()));
                    } else {
                    	queryParams.should(QueryBuilders.matchQuery(searchParam.getName(), searchParam.getValue()));
                    }
                }
			}
		}
		
		SearchQuery query = nativeSearchQueryBuilder
				.withQuery(queryParams)
				.withHighlightFields(new HighlightBuilder.Field("cv.content")
				.preTags("<b>")
				.postTags("</b>")
				.numOfFragments(1)
				.fragmentSize(500))
				.withPageable(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize())).build();
		
		Mapper mapper = new Mapper();
		AggregatedPage<Index> results = elasticsearchTemplate.queryForPage(query, Index.class, mapper);

		if (results == null) {
			return new SearchResultsDTO(new ArrayList<Index>(), 0);
		}
		return new SearchResultsDTO(results.getContent(), mapper.totalElements);
    }
	
	private void buildNestedParam(BoolQueryBuilder queryParams, Boolean isPhraseQuery, String searchName, String searchValue, Operator type) {
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
		NestedQueryBuilder nestedQuery;
		String searchNameContent = searchName + ".content";

		if (isPhraseQuery) {
			if (type.equals(Operator.AND)) {
				nestedQuery = QueryBuilders.nestedQuery(searchName,
						boolQuery.must(QueryBuilders.matchPhraseQuery(searchNameContent, searchValue)), ScoreMode.None);
				queryParams.must(nestedQuery);
			} else  {
				nestedQuery = QueryBuilders.nestedQuery(searchName,
						boolQuery.should(QueryBuilders.matchPhraseQuery(searchNameContent, searchValue)),
						ScoreMode.None);
				queryParams.should(nestedQuery);
			}
		} else {
			if (type.equals(Operator.AND)) {
				nestedQuery = QueryBuilders.nestedQuery(searchName,
						boolQuery.must(QueryBuilders.commonTermsQuery(searchNameContent, searchValue)), ScoreMode.None);
				queryParams.must(nestedQuery);
			} else {
				nestedQuery = QueryBuilders.nestedQuery(searchName,
						boolQuery.should(QueryBuilders.commonTermsQuery(searchNameContent, searchValue)),
						ScoreMode.None);
				queryParams.should(nestedQuery);
			}
		}
	}
	
	private NestedQueryBuilder nestedCvBuilder(String criterion) {
		BoolQueryBuilder boolQueryCv = QueryBuilders.boolQuery();
		NestedQueryBuilder nestedQueryCv = QueryBuilders.nestedQuery("cv",
				boolQueryCv.should(QueryBuilders.commonTermsQuery("cv.content", criterion)), ScoreMode.Total);
		return nestedQueryCv;
	}
	
}
