package com.solvd.individuals.workers;

import com.solvd.individuals.patients.Patient;
import com.solvd.individuals.workers.workDays.DayOfWeek;


import java.time.LocalTime;

public interface StaffManagementInterface {
    void admitPatient(Patient patient, DayOfWeek dayOfWeek, LocalTime localTime);

    void dischargePatient(Patient patient);
}
