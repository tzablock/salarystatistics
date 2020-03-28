package com.salary.repository.glassdor;

import java.util.List;
import java.util.Map;

public interface SalariesCriteriaQueriesRepository {
    Map<String, Map<String, Integer>> selectEmployersPositionsSalaries(List<String> employers);
    Map<String, Map<String, Integer>> selectEmployersPositionsSalaries(List<String> employers, List<String> positions);
    Map<String, Integer> selectPositionsSalaries(List<String> positions);
    Map<String, Integer> selectEmployerSalaries(String position, List<String> employers);
    List<Integer> selectSalaries(String employer, String position);
}
