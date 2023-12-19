package com.solvd.custom.interfaces;

import com.solvd.individuals.patients.Patient;
import com.solvd.custom.enums.DayOfWeek;


import java.time.LocalTime;

public interface StaffManagementInterface {
    void admitPatient(Patient patient, DayOfWeek dayOfWeek, LocalTime localTime);

    void dischargePatient(Patient patient);
}
