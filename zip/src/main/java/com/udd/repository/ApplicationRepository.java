package com.udd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udd.model.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

}
