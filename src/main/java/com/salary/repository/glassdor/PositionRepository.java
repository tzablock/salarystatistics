package com.salary.repository.glassdor;

import com.salary.repository.entity.PositionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionRepository extends JpaRepository<PositionDTO, Long>{
    List<PositionDTO> findByPositionNameAndEmployer_Name(String positionName, String employerName);
}
