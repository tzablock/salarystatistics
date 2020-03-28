package com.salary.repository.glassdor;

import com.salary.repository.entity.PositionDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalariesReposiory extends JpaRepository<PositionDTO, Long>, SalariesCriteriaQueriesRepository {
}
