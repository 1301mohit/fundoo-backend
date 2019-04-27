package com.bridgelabz.fundoo.elasticSearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.note.model.Note;
import com.fasterxml.jackson.databind.ObjectMapper;
@Service

public class ElasticSearchImp implements ElasticSearch{

	private final static String INDEX = "fundoo";
	private static final String TYPE = "note";
	
	@Autowired
	private RestHighLevelClient client;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	public String createNote(Note note) throws IOException {
		
		System.out.println("Create note in elastic Search");
		Map<String, Object> noteMapper = objectMapper.convertValue(note, Map.class);
		IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, note.getNoteId().toString()).source(noteMapper);
		IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
		return indexResponse.getResult().name();
		
	}
	
	public String deleteNote(Note note) throws IOException {
		
		DeleteRequest deleteRequest = new DeleteRequest(INDEX, TYPE, note.getNoteId().toString());
		DeleteResponse response = client.delete(deleteRequest, RequestOptions.DEFAULT);
		return response.getResult().name();
	}
	
	public String updateNote(Note note) throws IOException {
	
		UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, note.getNoteId().toString());
		Map<String, Object> noteMapper = objectMapper.convertValue(note, Map.class);
		
		updateRequest.doc(noteMapper);
		
		UpdateResponse response = client.update(updateRequest, RequestOptions.DEFAULT);
		return response.getResult().name();
	}
	
	public List<Note> search(String query, Long userId) {
		SearchRequest searchRequest = new SearchRequest(INDEX).types(TYPE);
		
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		
		QueryBuilder queryBuilder = QueryBuilders.boolQuery()
			.must(QueryBuilders.queryStringQuery("*"+query+"*").analyzeWildcard(true).field("title", 2.0f)
			.field("description").field("label"))
			.filter(QueryBuilders.termsQuery("user.userId", String.valueOf(userId)));

			searchSourceBuilder.query(queryBuilder);

			searchRequest.source(searchSourceBuilder);

			SearchResponse searchResponse=null;
			try {
				searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

				System.out.println("Search response:"+searchResponse);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			List<Note> allnote = getSearchResult(searchResponse);
		

			return allnote;

	}
	
	public List<Note> getSearchResult(SearchResponse response) {
		SearchHit[] searchHits = response.getHits().getHits();
		List<Note> notes = new ArrayList<>();
		for (SearchHit hit : searchHits) {
			notes.add(objectMapper.convertValue(hit.getSourceAsMap(), Note.class));
		}
		System.out.println("Notes in Search notes"+notes);
		return notes;
	}
	
	
}
