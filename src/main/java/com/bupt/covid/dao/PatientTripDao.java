package com.bupt.covid.dao;

import com.bupt.covid.pojo.PatientTrip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PatientTripDao extends JpaSpecificationExecutor<PatientTrip>, JpaRepository<PatientTrip,String> {

}
