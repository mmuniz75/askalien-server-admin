package edu.muniz.askalien.dao;

import java.util.List;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;

import edu.muniz.askalien.model.Usage;

public interface UsageRepository extends CrudRepository<Usage, Integer>{
	
	List<Usage> findByYearOrderByMonthAsc(Short year);

	@Procedure(procedureName="update_usage")
	void updateUsage();

}
