package com.salary.repository.glassdor.impl;

import com.salary.repository.entity.AggregatedPositionDTO;
import com.salary.repository.entity.PositionDTO;
import com.salary.repository.glassdor.SalariesCriteriaQueriesRepository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class SalariesCriteriaQueriesRepositoryImpl implements SalariesCriteriaQueriesRepository {
    @PersistenceContext
    private EntityManager em;
    private CriteriaBuilder cb;

    @PostConstruct
    private void initCb() {
        cb = em.getCriteriaBuilder();
    }

    @Override
    public Map<String, Map<String, Integer>> selectEmployersPositionsSalaries(List<String> employers) { //employer_name position_name avg_salary
        CriteriaQuery<PositionDTO> posQuery = cb.createQuery(PositionDTO.class);
        Join<Object, Object> prJoinEmp = posQuery.from(PositionDTO.class).join("employer");
        CriteriaBuilder.In<Object> ifOneOfEmployees = cb.in(prJoinEmp.get("name")).value(employers);
        posQuery.where(ifOneOfEmployees);
        Stream<PositionDTO> matchingPositions = em.createQuery(posQuery).getResultStream();
        return mapListToMapEmpToPosToSal(matchingPositions);
    }

    @Override
    public Map<String, Map<String, Integer>> selectEmployersPositionsSalaries(List<String> employers, List<String> positions) { //employer_name position_name avg_salary
        CriteriaQuery<PositionDTO> posQuery = cb.createQuery(PositionDTO.class);
        Root<PositionDTO> fromPos = posQuery.from(PositionDTO.class);
        Join<Object, Object> posJoinEmp = fromPos.join("employer");
        CriteriaBuilder.In<Object> ifOneOfEmployees = cb.in(posJoinEmp.get("name")).value(employers);
        CriteriaBuilder.In<Object> ifOneOfPositions = cb.in(fromPos.get("positionName")).value(positions);
        posQuery.where(ifOneOfEmployees).where(ifOneOfPositions);
        Stream<PositionDTO> matchingPositions = em.createQuery(posQuery).getResultStream();
        return mapListToMapEmpToPosToSal(matchingPositions);
    }

    @Override
    public Map<String, Integer> selectPositionsSalaries(List<String> positions) { //position_name avg_salary
        CriteriaQuery<AggregatedPositionDTO> posNameToAvgSal = cb.createQuery(AggregatedPositionDTO.class);
        Root<PositionDTO> fromPos = posNameToAvgSal.from(PositionDTO.class);
        CriteriaBuilder.In<Object> ifOneOfPositions = cb.in(fromPos.get("positionName")).value(positions);
        Expression<Double> avgSalaryForAllEmployees = cb.avg(fromPos.get("avgSalary"));
        posNameToAvgSal.where(ifOneOfPositions);
        posNameToAvgSal.groupBy(fromPos.get("positionName"));
        posNameToAvgSal.multiselect(fromPos.get("positionName"), avgSalaryForAllEmployees);
        Stream<AggregatedPositionDTO> matchingPositions = em.createQuery(posNameToAvgSal).getResultStream();
        return mapPosToPosToAvgSal(matchingPositions);
    }

    @Override
    public Map<String, Integer> selectEmployerSalaries(String position, List<String> employers) { // Employer_name avg_Salary
        CriteriaQuery<PositionDTO> empNameToAvgSal = cb.createQuery(PositionDTO.class);
        Root<PositionDTO> fromPos = empNameToAvgSal.from(PositionDTO.class);
        Join<Object, Object> posToEmpl = fromPos.join("employer");
        Predicate ifPosition = cb.equal(fromPos.get("positionName"), position);
        CriteriaBuilder.In<Object> ifOneOfEmployers = cb.in(posToEmpl.get("name")).value(employers);
        Predicate predicates = cb.and(ifPosition, ifOneOfEmployers);
        empNameToAvgSal.where(predicates);
        Stream<PositionDTO> matchingPositions = em.createQuery(empNameToAvgSal).getResultStream();
        return mapListToMapEmpToSal(matchingPositions);
    }

    @Override
    public List<Integer> selectSalaries(String employer, String position) { //salaries(not aggregated)
        CriteriaQuery<PositionDTO> salaries = cb.createQuery(PositionDTO.class);
        Root<PositionDTO> fromPos = salaries.from(PositionDTO.class);
        Join<Object, Object> posToEmpl = fromPos.join("employer");
        Predicate ifPosition = cb.equal(fromPos.get("positionName"), position);
        Predicate ifEmployer = cb.equal(posToEmpl.get("name"), employer);
        Predicate predicates = cb.and(ifEmployer, ifPosition);
        salaries.where(predicates);
        return em.createQuery(salaries).getSingleResult().getSalaries();
    }

    private Map<String, Map<String, Integer>> mapListToMapEmpToPosToSal(Stream<PositionDTO> matchingPositions) {
        return matchingPositions.collect(toMap(
                p -> p.getEmployer().getName(),
                p -> new HashMap<String, Integer>(){{put(p.getPositionName(), p.getAvgSalary());}},
                (oldVal, newVal) -> {oldVal.putAll(newVal); return oldVal;}
        ));
    }

    private Map<String, Integer> mapListToMapEmpToSal(Stream<PositionDTO> matchingPositions){
        return matchingPositions.collect(toMap(
                p -> p.getEmployer().getName(),
                PositionDTO::getAvgSalary
        ));
    }
    private Map<String, Integer> mapPosToPosToAvgSal(Stream<AggregatedPositionDTO> matchingPositions) {
        return matchingPositions.collect(toMap(
                AggregatedPositionDTO::getPositionName,
                ap -> ap.getAvgSalary().intValue()
        ));
    }
}
