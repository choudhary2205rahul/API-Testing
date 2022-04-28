package com.swisscom.junit.repository;

import com.swisscom.junit.models.HistoryGrade;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryGradesDao extends CrudRepository<HistoryGrade, Integer> {

    public Iterable<HistoryGrade> findGradeByStudentId (int id);

    public void deleteByStudentId(int id);
}
