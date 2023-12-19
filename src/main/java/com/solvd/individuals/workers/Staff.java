package com.solvd.individuals.workers;

import com.solvd.custom.exceptions.HospitalNotFoundException;
import com.solvd.custom.exceptions.InvalidWorkingHoursException;
import com.solvd.custom.exceptions.PatientNotAssignedException;
import com.solvd.custom.exceptions.UnavailableSlotException;
import com.solvd.custom.interfaces.StaffManagementInterface;
import com.solvd.hospital.Hospital;
import com.solvd.custom.enums.Gender;
import com.solvd.individuals.Person;
import com.solvd.individuals.patients.Patient;
import com.solvd.custom.enums.DayOfWeek;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Staff extends Person implements StaffManagementInterface {
    private final Hospital hospital;
    private final String staffId;
    private final List<WorkingHours> workingHoursList = new ArrayList<>();
    private static final Logger logger = LogManager.getLogger(Staff.class);

    public Staff(String name, String surname, int age, Gender gender, String staffId, Hospital hospital) {
        super(name, surname, age, gender);
        this.staffId = staffId;
        this.hospital = hospital;
    }

    public List<WorkingHours> getWorkingHoursList() {
        return workingHoursList;
    }

    public void addWorkingHours(WorkingHours... workingHours) {
        try {
            for (WorkingHours hours : workingHours) {
                if (hours.getStartTime().isAfter(hours.getEndTime())) {
                    throw new InvalidWorkingHoursException("Invalid working hours: start time cannot be after end time.");
                }
            }
            workingHoursList.addAll(Arrays.asList(workingHours));
        } catch (InvalidWorkingHoursException e) {
            logger.error("InvalidWorkingHoursException: " + e.getMessage());
        }
    }
    @Override
    public String getDetails() {
        return "I am a Worker";
    }

    @Override
    public final void welcome() {
        System.out.println("Hello patient!");
    }

    @Override
    public final void goodbye() {
        System.out.println("Goodbye patient!");
    }

    @Override
    public final void admitPatient(Patient patient, DayOfWeek dayOfWeek, LocalTime localTime) {
        try {
            if (hospital == null) {
                logger.error("Hospital not found");
                throw new HospitalNotFoundException();
            }
            Doctor selectedDoctor = hospital.chooseDoctor(dayOfWeek, localTime);
            if (selectedDoctor != null) {
                patient.setDoctor(selectedDoctor);
                patient.setTime(localTime);
                patient.setDayOfWeek(dayOfWeek);
                selectedDoctor.addPatient(patient);
                selectedDoctor.toggleSlotAvailability(dayOfWeek, localTime);
                logger.info("Patient " + patient.getName() + " has been assigned to " + this.getName() + " on ["
                        + patient.getDayOfWeek() + ", " + patient.getTime() + "]");
            } else {
                logger.error("No available slot for the specified day and time.");
                throw new UnavailableSlotException();
            }
        } catch (HospitalNotFoundException | UnavailableSlotException e) {
            logger.error("Exception occurred: " + e.getClass().getSimpleName());
        }
    }

    @Override
    public final void dischargePatient(Patient patient) {
        try {
            Doctor doctor = patient.getDoctor();
            if (Objects.isNull(doctor)) {
                throw new PatientNotAssignedException("Patient not assigned to a doctor");
            } else {
                logger.info("Patient " + patient.getName() + " has been removed from " + this.getName() + " on ["
                        + patient.getDayOfWeek() + ", " + patient.getTime() + "]");
                doctor.toggleSlotAvailability(patient.getDayOfWeek(), patient.getTime());
                doctor.removePatient(patient);
                patient.setDoctor(null);
                patient.setTime(null);
                patient.setDayOfWeek(null);
            }
        } catch (PatientNotAssignedException e) {
            logger.error("PatientNotAssignedException: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Staff{" +
                "staffId='" + staffId + '\'' +
                ", workingHoursList=" + workingHoursList +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Staff staff = (Staff) o;
        return Objects.equals(staffId, staff.staffId) && Objects.equals(workingHoursList, staff.workingHoursList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(staffId, workingHoursList);
    }
}
