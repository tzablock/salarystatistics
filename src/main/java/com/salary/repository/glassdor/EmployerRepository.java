package com.salary.repository.glassdor;

import com.salary.repository.entity.EmployerDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployerRepository extends JpaRepository<EmployerDTO, Long> {
}
