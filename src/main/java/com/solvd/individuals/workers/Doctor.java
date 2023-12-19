package com.solvd.individuals.workers;//Represents a doctor working within a department.
//Contains attributes such as the doctor's name and specialization.
//Inherits from the Department class, so it also has the attributes of the Department and hospital.Hospital classes.
import com.solvd.custom.interfaces.DoctorManagementInterface;
import com.solvd.hospital.Hospital;
import java.time.LocalTime;
import java.util.*;

import com.solvd.custom.enums.Gender;
import com.solvd.individuals.patients.Patient;
import com.solvd.custom.enums.Status;
import com.solvd.custom.enums.DayOfWeek;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Doctor extends Staff implements DoctorManagementInterface {
    private final List<Patient> myPatients = new ArrayList<>();
    private final Map<DayOfWeek, List<LocalTime>> availableSlots = new HashMap<>();
    private static final Logger logger = LogManager.getLogger(Doctor.class);

    public Doctor(String name, String surname, int age, Gender gender, String doctorId, Hospital hospital) {
        super("Dr. " + name, surname, age, gender, doctorId, hospital);
    }

    public void addPatient(Patient patient){
        myPatients.add(patient);
//        System.out.println("Patient " + patient.getName() + " has been assigned to " + this.getName() + " on ["
//                + patient.getDayOfWeek() + ", " + patient.getTime() + "]");
    }
    public void removePatient(Patient patient) {
        myPatients.remove(patient);
//        System.out.println("Patient " + patient.getName() + " has been removed from " + this.getName() + " on ["
//                + patient.getDayOfWeek() + ", " + patient.getTime() + "]");
    }

    @Override
    public void addWorkingHours(WorkingHours... workingHours) {
        getWorkingHoursList().addAll(Arrays.asList(workingHours));
        divideWorkingHoursIntoSlots();
    }

    private void divideWorkingHoursIntoSlots() {
        for (WorkingHours workingHours : getWorkingHoursList()) {
            DayOfWeek dayOfWeek = workingHours.getDayOfWeek();
            LocalTime startTime = workingHours.getStartTime();
            LocalTime endTime = workingHours.getEndTime();
            List<LocalTime> slots = new ArrayList<>();
            while (startTime.isBefore(endTime)) {
                slots.add(startTime);
                startTime = startTime.plusHours(1);
            }
            availableSlots.put(dayOfWeek, slots);
        }
    }

    public boolean isSlotAvailable(DayOfWeek dayOfWeek, LocalTime time) {
        List<LocalTime> slots = availableSlots.get(dayOfWeek);
        return slots != null && slots.contains(time);
    }

    public void toggleSlotAvailability(DayOfWeek dayOfWeek, LocalTime time) {
        List<LocalTime> slots = availableSlots.get(dayOfWeek);
        if (slots != null) {
            if (slots.contains(time)) {
                slots.remove(time); // Mark slot as unavailable
                availableSlots.put(dayOfWeek, slots);
            } else {
                slots.add(time); // Mark slot as available
                Collections.sort(slots);
                availableSlots.put(dayOfWeek, slots);
            }
        }
    }

    @Override
    public void prescribeMedicine(Patient patient) {
        logger.info("Prescribing medicine for " + patient.getName() + "...");
        patient.setStatus(Status.HEALTHY);
    }

    @Override
    public void heal(Patient patient) {
        logger.info("Status: " + patient.getStatus());
        logger.info("Healing patient " + patient.getName() + "...");
        prescribeMedicine(patient);
        logger.info("Status: " + patient.getStatus());
    }

    public void checkAvailability() {
        logger.info(getName() + "'s available slots: " + this.availableSlots);
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "patients=" + myPatients +
                ", availableSlots=" + availableSlots +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Doctor doctor = (Doctor) o;
        return Objects.equals(myPatients, doctor.myPatients) && Objects.equals(availableSlots, doctor.availableSlots);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), myPatients, availableSlots);
    }
}
