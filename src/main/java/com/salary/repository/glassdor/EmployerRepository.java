package com.salary.repository.glassdor;

import com.salary.repository.entity.EmployerDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployerRepository extends JpaRepository<EmployerDTO, Long> {
    //List<EmployerDTO> findByField(String field);  way to implement simple where conditions(Field need to have exact name of field in Entity)
    List<EmployerDTO> findByName(String name);
    default boolean ifNotExistByName(String name){
        return findByName(name).isEmpty();
    }
}
