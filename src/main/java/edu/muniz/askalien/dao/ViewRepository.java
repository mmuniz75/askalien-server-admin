package edu.muniz.askalien.dao;

import java.util.List;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;

import edu.muniz.askalien.model.View;

public interface ViewRepository extends CrudRepository<View, Integer>{
	
	List<View> findByYearOrderByMonthAsc(Short year);

	@Procedure(procedureName="update_view")
	void updateView();

}
