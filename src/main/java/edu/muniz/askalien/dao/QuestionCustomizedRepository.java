package edu.muniz.askalien.dao;

import java.util.List;

import edu.muniz.askalien.model.Question;

public interface QuestionCustomizedRepository {

	public List<Question> findAll(QuestionFilter filter);
}
