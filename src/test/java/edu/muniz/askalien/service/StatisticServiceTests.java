package edu.muniz.askalien.service;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StatisticServiceTests {
	
	@Autowired
	private StatisticService service;
	
	@Test
	public void testAccessStatistic(){
		StatisticDTO dto = service.getAccessStatistic();
		assertTrue(dto.getTotalQuestion()>90000);
		assertTrue(dto.getTotalFrequentUsers()>=1400);
		assertTrue(dto.getTotalUsers()>=4800);
		assertTrue(dto.getTotalAnswers()>=1200);
		assertTrue(dto.getTotalCountries()>=100);
	}

}
