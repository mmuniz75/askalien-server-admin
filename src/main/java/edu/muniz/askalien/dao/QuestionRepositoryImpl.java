package edu.muniz.askalien.dao;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import edu.muniz.askalien.model.Question;

@Component
public class QuestionRepositoryImpl implements QuestionCustomizedRepository {
	
	@PersistenceContext
	private EntityManager em;
	 
	public List<Question> findAll(QuestionFilter filter) {
		
		StringBuilder sql = new StringBuilder("select obj from Question obj where 1=1 ");
		if (filter.isJustFeedback())
			sql.append(" and obj.feedback is not null");

		if (filter.getAnswerId() != null && filter.getAnswerId() > 0)
			sql.append(" and obj.answer.id =" + filter.getAnswerId());

		if (filter.getQuestion() != null && filter.getQuestion().length() > 0)
			sql.append(" and obj.text like '%" + filter.getQuestion() + "%'");

		if (filter.getIpFilter() != null && filter.getIpFilter().length() > 0)
			sql.append(" and obj.ip like '%" + filter.getIpFilter() + "%'");

		if (filter.isJustThisMonth())
			sql.append(" and obj.creationDate >= :monthDate");

		if (filter.getStartDate() != null)
			sql.append(" and obj.creationDate >= :startDate");

		if (filter.getEndDate() != null)
			sql.append(" and obj.creationDate <= :endDate");

		sql.append(" order by creationdate desc");

		Query query = em.createQuery(sql.toString(), Question.class);

		if (filter.isJustThisMonth()) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DATE, 1);
			cal.set(Calendar.HOUR, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			cal.set(Calendar.AM_PM, Calendar.AM);
			query.setParameter("monthDate", cal.getTime());
		}

		if (filter.getStartDate() != null)
			query.setParameter("startDate", filter.getStartDate());

		if (filter.getEndDate() != null)
			query.setParameter("endDate", filter.getEndDate());

		List<Question> questions = query.getResultList();
		
		return questions;
	}

}
