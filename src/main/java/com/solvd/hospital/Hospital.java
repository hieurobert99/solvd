package com.solvd.hospital;

import com.solvd.individuals.patients.Patient;
import com.solvd.individuals.workers.Doctor;
import com.solvd.individuals.workers.Staff;
import com.solvd.individuals.workers.workDays.DayOfWeek;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalTime;
import java.util.*;
import java.util.function.*;

//Represents the basic structure of a hospital, containing attributes like name and location.
//Provides a general view of a hospital.
public final class Hospital extends Building implements HospitalInterface {
    private final List<Staff> staffList = new LinkedList<>(); //new
    private final Map<Doctor, Patient> doctorPatient = new HashMap<>(); //new
    private static final Logger logger = LogManager.getLogger(Hospital.class);

    static {
        System.out.println("Welcome to our Hospital!\n");
    }

    public Hospital(String name, String city, String address) {
        super(name, city, address);
        logger.info("Hospital: " + this);
    }

    @Override
    public Staff callStaff() {
        List<Staff> nonDoctors = staffList.stream()
                .filter(staff -> !(staff instanceof Doctor))
                .toList();

        // Select a random doctor from the filtered list
        if (!nonDoctors.isEmpty()) {
            Random random = new Random();
            return nonDoctors.get(random.nextInt(nonDoctors.size()));
        }
        return null;
    }

    @Override
    public void recruitStaff(Staff staff){
        this.staffList.add(staff);
        if(staff instanceof Doctor){
            logger.info(staff.getName() + " had been hired as a Doctor!");
        } else {
            logger.info(staff.getName() + " had been hired as a Staff!");
        }
    }

    @Override
    public void fireStaff(Staff staff) {
        this.staffList.remove(staff);
        logger.info("Farewell " + staff.getName() +". It was a pleasure working with you!");
    }


    public Doctor chooseDoctor(DayOfWeek dayOfWeek, LocalTime localTime){
        List<Doctor> doctorsList = staffList.stream()
                .filter(staff -> staff instanceof Doctor)
                .map(staff -> (Doctor) staff)
                .filter(doctor -> doctor.isSlotAvailable(dayOfWeek, localTime))
                .toList();

        // Select a random doctor from the filtered list
        if (!doctorsList.isEmpty()) {
            Random random = new Random();
            return doctorsList.get(random.nextInt(doctorsList.size()));
        }
         return null;
    }

    public List<Doctor> filterDoctorsByAge(int ageThreshold) {
        Predicate<Doctor> filterByAge = doctor -> doctor.getAge() > ageThreshold;
        return staffList.stream().filter(filterByAge).toList();
    }

    public List<String> mapPatientNamesToUpperCase() {
        Function<Patient, String> toUpperCaseName = patient -> patient.getName().toUpperCase();
        return patientList.stream().map(toUpperCaseName).toList();
    }
    public void printDoctorDetails() {
        Consumer<Doctor> printDoctorDetails = doctor -> System.out.println(doctor.getName() + ": " + doctor.getSpecialization());
        doctorList.forEach(printDoctorDetails);
    }

    public Patient generateNewPatient() {
        Supplier<Patient> createNewPatient = () -> new Patient("New", "Patient", 30, Gender.MALE, "P-0002");
        return createNewPatient.get();
    }
    public int incrementPatientAge(int currentAge) {
        UnaryOperator<Integer> incrementByFive = age -> age + 5;
        return incrementByFive.apply(currentAge);
    }



    @Override
    public String toString() {
        return super.toString();
    }
}

