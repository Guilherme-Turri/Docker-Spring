package com.apirestdocker.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apirestdocker.course.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}
