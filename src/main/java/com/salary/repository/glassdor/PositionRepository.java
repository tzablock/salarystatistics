package com.salary.repository.glassdor;

import com.salary.repository.entity.PositionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<PositionDTO, Long>{
}
