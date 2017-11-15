package edu.muniz.askalien.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import edu.muniz.askalien.model.Question;

public interface QuestionRepository extends CrudRepository<Question, Integer>,QuestionCustomizedRepository{
	
	@Query("select question from Question question JOIN FETCH question.answer answer where question.id=?1")
	public Question findQuestionById(Integer id);
	
	public List<Question> findByAnswerIdAndFeedbackIsNotNull(Integer id);
	
	public List<Question> findByAnswerIdAndFeedbackIsNull(Integer id);
	
	
	@Query("select count(ip) "
			+ "from Question "
			+ "where ip not in ('','x.x.x.x') "
			+ "group by ip "
			+ "having count(ip) > 10")
	public List<Long> findFrequentUsers();
	
	@Query("SELECT count(DISTINCT question.ip) FROM Question question")
	public Long findCountUsers();
	
	@Query("SELECT count(DISTINCT question.country) FROM Question question")
	public Long findCountCountries();
	
			
}
