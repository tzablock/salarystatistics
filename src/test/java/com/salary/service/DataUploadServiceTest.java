package com.salary.service;

import com.salary.controller.response.ResponseInvocationWrapper;
import com.salary.repository.entity.EmployerDTO;
import com.salary.repository.entity.PositionDTO;
import com.salary.repository.glassdor.EmployerRepository;
import com.salary.repository.glassdor.PositionRepository;
import com.salary.repository.glassdor.SalariesReposiory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DataUploadServiceTest {
    private DataUploadService out;
    @Mock
    private SalariesReposiory salariesReposiory;
    @Mock
    private PositionRepository positionRepo;
    @Mock
    private EmployerRepository employerRepository;
    @Mock
    private RestService restService;


    @Test
    void uploadPositionShouldSaveNewPositionAndReturnPositiveResponseWhenPositionWithSuchPositionNameAndEmployerNameNotExist() {
        PositionDTO newPosition = preparePositionInput(Collections.emptyList());
        prepareMocksForPositionRepo(newPosition, Collections.emptyList());
        this.out = new DataUploadService(salariesReposiory, positionRepo, employerRepository, restService);
        ResponseInvocationWrapper response = this.out.uploadPosition(newPosition);

        verify(positionRepo, times(1)).save(newPosition);
        assertThat(response.httpFormat("Position Saved!").getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void uploadPositionShouldUpdatePositionAndReturnPositiveResponseWhenPositionWithSuchPositionNameAndEmployerNameExist() {
        PositionDTO updateFromPosition = preparePositionInput(Collections.singletonList(1000));
        PositionDTO positionToUpdate = preparePositionInput(Collections.singletonList(2000));
        prepareMocksForPositionRepo(updateFromPosition, Collections.singletonList(positionToUpdate));
        this.out = new DataUploadService(salariesReposiory, positionRepo, employerRepository, restService);
        ResponseInvocationWrapper response = this.out.uploadPosition(updateFromPosition);

        verify(positionRepo, times(1)).save(positionToUpdate);
        assertThat(positionToUpdate.getSalaries()).contains(1000).contains(2000);
        assertThat(positionToUpdate.getAvgSalary()).isEqualTo(1500);
        assertThat(response.httpFormat("Position Updated!").getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void uploadPositionShouldReturnINTERNAL_SERVER_ERRORWhenSomeExceptionIsThrown() {
        PositionDTO newPosition = preparePositionInput(Collections.emptyList());
        prepareMocksForPositionRepo(newPosition, Collections.emptyList());
        prepareMocksForPositionRepoThrowingException(newPosition);
        this.out = new DataUploadService(salariesReposiory, positionRepo, employerRepository, restService);
        ResponseInvocationWrapper response = this.out.uploadPosition(newPosition);

        assertThat(response.httpFormat("").getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void uploadEmployerShouldSaveNewEmployerAndReturnPositiveResponseWhenEmployerWithSuchNameNotExist() {
        EmployerDTO newEmployer = prepareEmployerInput();
        prepareMocksForNotExistingEmployer(newEmployer);
        this.out = new DataUploadService(salariesReposiory, positionRepo, employerRepository, restService);
        ResponseInvocationWrapper response = this.out.uploadEmployer(newEmployer);

        verify(employerRepository, times(1)).save(newEmployer);
        assertThat(response.httpFormat("Employer Saved!").getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void uploadEmployerShouldNotSaveNewEmployerAndReturnINTERNAL_SERVER_ERRORResponseWhenEmployerWithSuchNameExist() {
        EmployerDTO newEmployer = prepareEmployerInput();
        prepareMocksForExistingEmployer(newEmployer);
        this.out = new DataUploadService(salariesReposiory, positionRepo, employerRepository, restService);
        ResponseInvocationWrapper response = this.out.uploadEmployer(newEmployer);

        verify(employerRepository, times(0)).save(newEmployer);
        assertThat(response.httpFormat("Employer Saved!").getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void uploadEmployerShouldReturnINTERNAL_SERVER_ERRORWhenSomeExceptionIsThrown() {
        EmployerDTO newEmployer = prepareEmployerInput();
        prepareMocksForNotExistingEmployer(newEmployer);
        prepareMockForEmployerRepoThrowingException(newEmployer);
        this.out = new DataUploadService(salariesReposiory, positionRepo, employerRepository, restService);
        ResponseInvocationWrapper response = this.out.uploadEmployer(newEmployer);

        assertThat(response.httpFormat("").getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private PositionDTO preparePositionInput(List<Integer> salaries) {
        PositionDTO newPosition = new PositionDTO();
        newPosition.setPositionName("PositionName");
        newPosition.addSalaries(salaries);
        EmployerDTO newPositionEmployer = new EmployerDTO();
        newPositionEmployer.setName("Name");
        newPosition.setEmployer(newPositionEmployer);
        return newPosition;
    }

    private EmployerDTO prepareEmployerInput() {
        EmployerDTO newEmployer = new EmployerDTO();
        newEmployer.setName("Name");
        return newEmployer;
    }

    private void prepareMocksForPositionRepo(PositionDTO position, List<PositionDTO> findResponse){
        Mockito.when(positionRepo.findByPositionNameAndEmployer_Name(position.getPositionName(), position.getEmployer().getName()))
                .thenReturn(findResponse);
    }

    private void prepareMocksForPositionRepoThrowingException(PositionDTO position){
        Mockito.when(positionRepo.save(position))
                .thenThrow(RuntimeException.class);
    }

    private void prepareMocksForNotExistingEmployer(EmployerDTO employer){
        Mockito.when(employerRepository.ifNotExistByName(employer.getName()))
                .thenReturn(true);
    }

    private void prepareMocksForExistingEmployer(EmployerDTO employer){
        Mockito.when(employerRepository.ifNotExistByName(employer.getName()))
                .thenReturn(false);
    }

    private void prepareMockForEmployerRepoThrowingException(EmployerDTO employer){
        Mockito.when(employerRepository.save(employer))
                .thenThrow(RuntimeException.class);
    }
}