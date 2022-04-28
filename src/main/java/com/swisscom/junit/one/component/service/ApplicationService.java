package com.swisscom.junit.one.component.service;

import com.swisscom.junit.one.component.dao.ApplicationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationDao applicationDao;

    public double addGradeResultsForSingleClass(List<Double> numbers) {
        return applicationDao.addGradeResultsForSingleClass(numbers);
    }

    public double findGradePointAverage (List<Double> grades ) {
        return applicationDao.findGradePointAverage(grades);
    }

    public Object checkNull(Object obj) {
        return applicationDao.checkNull(obj);
    }

}
