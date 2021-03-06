package com.example.search;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.SearchParameters;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

@Service
public class SearchService {
    private Twitter twitter;

    @Autowired
    public SearchService(Twitter twitter) {
	this.twitter = twitter;
    }

    public List<LightTweet> search(String searchType, List<String> keywords) {
	List<SearchParameters> searches = keywords.stream().map(taste -> createSearchCriteria(searchType, taste))
		.collect(Collectors.toList());
	List<LightTweet> results = searches.stream().map(params -> twitter.searchOperations().search(params))
		.flatMap(searchResults -> searchResults.getTweets().stream()).map(LightTweet::ofTweet)
		.collect(Collectors.toList());
	return results;
    }

    private SearchParameters.ResultType getResultType(String searchType) {
	for (SearchParameters.ResultType knowType : SearchParameters.ResultType.values()) {
	    if (knowType.name().equalsIgnoreCase(searchType)) {
		return knowType;
	    }
	}
	return SearchParameters.ResultType.RECENT;
    }

    private SearchParameters createSearchCriteria(String searchType, String taste) {
	SearchParameters.ResultType resultType = getResultType(searchType);
	SearchParameters searchParameters = new SearchParameters(taste);
	searchParameters.resultType(resultType);
	searchParameters.count(3);
	return searchParameters;
    }
}
