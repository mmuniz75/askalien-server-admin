package edu.muniz.askalien.dao;

import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.muniz.askalien.model.Question;
import edu.muniz.askalien.model.Usage;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsageRepositoryTests {
	
	@Autowired
	private UsageRepository repo;
	
	@Autowired
	private QuestionRepository questionRepo;


	@Test
	public void testCountryQuestions(){
		List<Usage> usages = repo.findByYearOrderByMonthAsc((short)2015);
		
		assertTrue(usages.size()==12);
		
		Usage usage = usages.get(0);
		assertTrue(usage.getMonth()==1);
		assertTrue(usage.getYear()==2015);
		assertTrue(usage.getNewUsers()==100);
		assertTrue(usage.getNumberUsers()==133);
		
	}
	
	@Test
	public void testUpdateUsage(){
		LocalDate date = LocalDate.now();
		int month = date.getMonthValue();
		int year = date.getYear();
		
		List<Usage> usages = repo.findByYearOrderByMonthAsc((short)year);
		Usage usage = usages.get(month-1);
		int countUsers = usage.getNumberUsers();
		int newUsers = usage.getNewUsers();
		
		Integer questionId = -1;

		try {
			Question question = new Question();
			question.setText("some question");
			question.setIp("1.2.3.4.5");
			questionRepo.save(question);
			questionId = question.getId();

			repo.updateUsage();

			usages = repo.findByYearOrderByMonthAsc((short) year);
			usage = usages.get(month - 1);

			assertTrue(usage.getNumberUsers() == countUsers + 1);
			assertTrue(usage.getNewUsers() == newUsers + 1);

		}finally{
			if(questionId > 0) {
				questionRepo.delete(questionId);
				repo.updateUsage();
			}	
		}
		
	}
	 
	
	

}
