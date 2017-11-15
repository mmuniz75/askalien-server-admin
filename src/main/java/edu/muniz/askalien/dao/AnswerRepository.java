package edu.muniz.askalien.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import edu.muniz.askalien.model.Answer;

public interface AnswerRepository extends CrudRepository<Answer, Integer> {
	
	@Query("select answer from Answer answer JOIN FETCH answer.video video where answer.id=?1")
	public Answer findAnswerById(Integer id);
	
	public AnswerSummary findById(Integer id);
	
	@Query("select answer from Answer answer order by id")
	public List<AnswerSummary> findAllSummary();
	
	@Query("SELECT new edu.muniz.askalien.model.Answer(answer.id,answer.subject,count(question.id)) "
			+ "FROM Question question join question.answer answer "
			+ "GROUP BY answer.id, answer.subject "
			+ "ORDER BY 3 desc")
	public List<Answer> findTopAnswers();
	
	@Query("SELECT new edu.muniz.askalien.model.Answer(answer.id,answer.subject,count(question.id)) "
			+ "FROM Question question join question.answer answer "
			+ "WHERE question.feedback is not null "
			+ "GROUP BY answer.id, answer.subject "
			+ "ORDER BY 3 desc")
	public List<Answer> findTopAnswersJustFeedBack();
	
	@Query("select answer from Answer answer JOIN FETCH answer.video video where answer.url=?1")
	public Answer findByUrl(String url);
	
}
