package com.solvd.custom.interfaces;

import com.solvd.individuals.patients.Patient;


public interface DoctorManagementInterface {
   void heal(Patient patient);
   void prescribeMedicine(Patient patient);
}
