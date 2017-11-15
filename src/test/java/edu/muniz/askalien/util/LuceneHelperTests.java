package edu.muniz.askalien.util;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class LuceneHelperTests {
	
	@Autowired
	IndexingHelper indexing;
	

	@Test
	public void indexQuestion() {
		final Integer ID = -1;
		final String SUBJECT = "muniz subjet est";
		final String CONTENT = "<font>muniz content test</font>";
		
		try{
			
			indexing.indexObject(ID, SUBJECT, CONTENT);
			
			Map<Integer,Float> ids = new HashMap<>();
	
			ids = indexing.getIdsFromSearch("'" + SUBJECT + "'");
			assertTrue(ids.containsKey(ID));
			
			ids = indexing.getIdsFromSearch("'" + CONTENT + "'");
			assertTrue(ids.containsKey(ID));
			
		}finally{
			indexing.removeObject(ID);
		}
	}
	
	@Test
	public void updateQuestion() {
		final Integer ID = -2;
		final String SUBJECT = "struts velociry jsp jsf";
		final String CONTENT = "oracle msqsl sybase sqlserver";
		final String SUBJECT_UPATED = "angular react vue timeleaf";
		final String CONTENT_UPDATED = "mysql mongodb postgres mariadb";
		
		
		try{
			indexing.indexObject(ID, SUBJECT, CONTENT);
			indexing.updateIndexing(ID, SUBJECT_UPATED, CONTENT_UPDATED);
			
			Map<Integer,Float> ids = new HashMap<>();
	
			ids = indexing.getIdsFromSearch("'" + SUBJECT + "'");
			assertTrue(!ids.containsKey(ID));
			
			ids = indexing.getIdsFromSearch("'" + CONTENT + "'");
			assertTrue(!ids.containsKey(ID));
			
			ids = indexing.getIdsFromSearch("'" + SUBJECT_UPATED + "'");
			assertTrue(ids.containsKey(ID));
			
			ids = indexing.getIdsFromSearch("'" + CONTENT_UPDATED + "'");
			assertTrue(ids.containsKey(ID));
		
		}finally{
			indexing.removeObject(ID);
		}
	}
	
	
}
