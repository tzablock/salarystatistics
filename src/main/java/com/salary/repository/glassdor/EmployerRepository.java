package com.salary.repository.glassdor;

import com.salary.repository.entity.Employer;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EmployerRepository implements JpaRepository<Employer, Long> {

    @Override
    public List<Employer> findAll() {
        return null;
    }

    @Override
    public List<Employer> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Employer> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Employer> findAllById(Iterable<Long> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Employer employer) {

    }

    @Override
    public void deleteAll(Iterable<? extends Employer> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Employer> S save(S s) {
        return null;
    }

    @Override
    public <S extends Employer> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<Employer> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Employer> S saveAndFlush(S s) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<Employer> iterable) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Employer getOne(Long aLong) {
        return null;
    }

    @Override
    public <S extends Employer> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Employer> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Employer> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Employer> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Employer> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Employer> boolean exists(Example<S> example) {
        return false;
    }
}
