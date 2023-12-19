package com.solvd.individuals.patients;
import com.solvd.individuals.workers.Staff;
import com.solvd.individuals.workers.workDays.DayOfWeek;

import java.time.LocalTime;
public interface PatientManagementInterface {
    void bookAppointment(Staff staff, DayOfWeek dayOfWeek, LocalTime localTime);
    void cancelAppointment(Staff staff);

}
