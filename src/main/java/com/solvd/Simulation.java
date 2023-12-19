package com.solvd;

import com.solvd.custom.lambda.CustomLambdaFunctions;
import com.solvd.hospital.Hospital;
import com.solvd.custom.enums.Gender;
import com.solvd.individuals.patients.Patient;
import com.solvd.individuals.workers.Doctor;
import com.solvd.individuals.workers.Staff;
import com.solvd.custom.enums.DayOfWeek;
import com.solvd.individuals.workers.WorkingHours;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

//1. Use at least 5 lambda functions from the java.util.function package.
//2. Create 3 custom Lambda functions with generics.
//3. Create 5 complex Enums(with fields, methods, blocks)

public class Simulation {

    private static final Logger logger = LogManager.getLogger(Simulation.class);
    public static void sampleSimulation(Hospital hospital, Patient patient,
                                        DayOfWeek dayOfWeek, LocalTime localTime){

        Staff staff = hospital.callStaff();

        if (staff!=null){
            System.out.println("task-8");
            logger.info("Staff helping today: " + staff.getName());
            staff.welcome();
            patient.bookAppointment(staff, dayOfWeek, localTime); //adds patient to random doctor
            patient.cancelAppointment(staff); //cancels previous appointment
            patient.bookAppointment(staff, DayOfWeek.MONDAY, LocalTime.of(15,0)); //appoints patient again
            Doctor doctor = patient.getDoctor();
            doctor.checkAvailability();
            doctor.heal(patient);
            staff.dischargePatient(patient);
        }

    }

    public static void main(String[] args) {
        //new hospital
        Hospital hospital = new Hospital(
                "Medicover",
                "Warsaw",
                "Aleje Jerozolimskie 123"
        );

        //new patient
        Patient patient1 = new Patient(
                "Bob",
                "Bobinski",
                25,
                Gender.MALE,
                "P-0001"
        );

        //new staff
        Doctor doctor1 = new Doctor(
                "John",
                "Oetker",
                45,
                Gender.MALE,
                "DR-0001",
                hospital

        );

        Staff staff1 = new Staff(
                "Peter",
                "Doolittle",
                42,
                Gender.MALE,
                "ST-0002",
                hospital
        );

        Staff staff2 = new Staff(
                "Leila",
                "Doobigger",
                42,
                Gender.MALE,
                "ST-0005",
                hospital
        );

        Doctor doctor2 = new Doctor(
                "Emma",
                "Parker",
                45,
                Gender.FEMALE,
                "DR-0002",
                hospital
        );

        Doctor doctor3 = new Doctor(
                "Alex",
                "McDonald",
                45,
                Gender.FEMALE,
                "DR-0002",
                hospital
        );

        System.out.println("\n---------- Hiring new staff -----------");
        hospital.recruitStaff(doctor1); //adds doctor to staff
        hospital.recruitStaff(doctor2); //adds doctor to staff
        hospital.recruitStaff(staff1); //adds worker to staff
        hospital.recruitStaff(staff2); //adds worker to staff
        hospital.recruitStaff(doctor3); //adds doctor to staff

        //sample working hours
        WorkingHours wh1 = new WorkingHours(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(17, 0));
        WorkingHours wh2 = new WorkingHours(DayOfWeek.TUESDAY, LocalTime.of(9, 0), LocalTime.of(17, 0));
        WorkingHours wh3 = new WorkingHours(DayOfWeek.THURSDAY, LocalTime.of(14, 0), LocalTime.of(22, 0));
        WorkingHours wh4 = new WorkingHours(DayOfWeek.FRIDAY, LocalTime.of(14, 0), LocalTime.of(22, 0));
        WorkingHours wh5 = new WorkingHours(DayOfWeek.SUNDAY, LocalTime.of(9, 0), LocalTime.of(17, 0));
        WorkingHours wh6 = new WorkingHours(DayOfWeek.TUESDAY, LocalTime.of(14, 0), LocalTime.of(22, 0));
        WorkingHours wh7 = new WorkingHours(DayOfWeek.MONDAY, LocalTime.of(15, 0), LocalTime.of(23, 0));
        WorkingHours wh8 = new WorkingHours(DayOfWeek.WEDNESDAY, LocalTime.of(6, 0), LocalTime.of(14, 0));
        WorkingHours wh9 = new WorkingHours(DayOfWeek.SATURDAY, LocalTime.of(6, 0), LocalTime.of(14, 0));


        doctor1.addWorkingHours(wh7, wh3, wh4, wh5);
        doctor2.addWorkingHours(wh1, wh2, wh5);
        doctor3.addWorkingHours(wh1, wh4, wh6, wh8, wh9);

        System.out.println("\n------ Doctors' schedules: -------");
        doctor1.checkAvailability();
        doctor2.checkAvailability();
        doctor3.checkAvailability();


        System.out.println("\n----------- Simulation: A patient named Bob wants to get an appointment with a doctor" +
                " for Tuesday at 16:00, but then changes to Monday 15:00. -----------");
        sampleSimulation(hospital, patient1, DayOfWeek.TUESDAY, LocalTime.of(16, 0));

        System.out.println("\n-------- Dr. Emma retires -------");
        hospital.fireStaff(doctor2);

        System.out.println("\n-------- Task 8 -------");

        //Predicate Lambda: Checking for available slots in the doctor's schedule.
        Predicate<WorkingHours> isSlotAvailable = wh ->
                wh.getDayOfWeek() == DayOfWeek.TUESDAY && wh.getStartTime().equals(LocalTime.of(16, 0));

        boolean isAvailable = doctor2.getWorkingHoursList().stream().anyMatch(isSlotAvailable);
        System.out.println("Is slot available on Tuesday at 16:00 for Doctor 2? " + isAvailable);

        //Consumer Lambda: Displaying doctor availability.
        Consumer<WorkingHours> displayAvailability = wh ->
                System.out.println(doctor1.getName() + " is available on " + wh.getDayOfWeek() +
                        " from " + wh.getStartTime() + " to " + wh.getEndTime());

        System.out.println("Doctor 1's availability:");
        doctor1.getWorkingHoursList().forEach(displayAvailability);

        //Function Lambda: Mapping doctor details to their names.
        Function<Staff, String> mapToName = Staff::getName;

        List<String> doctorNames = Arrays.asList(doctor1, doctor2, doctor3).stream()
                .map(mapToName)
                .toList();

        System.out.println("Doctor names: " + doctorNames);

        //Supplier Lambda: Creating a new instance of WorkingHours.
        Supplier<WorkingHours> createWorkingHours = () ->
                new WorkingHours(DayOfWeek.WEDNESDAY, LocalTime.of(12, 0), LocalTime.of(20, 0));

        WorkingHours newWorkingHours = createWorkingHours.get();
        System.out.println("Newly created working hours: " + newWorkingHours);

        //Comparator Lambda: Sorting the doctor's available slots.
        doctor3.getWorkingHoursList().sort(Comparator.comparing(WorkingHours::getDayOfWeek));

        System.out.println("Sorted Doctor 3's available slots:");
        doctor3.getWorkingHoursList().forEach(System.out::println);

        // Using the filterStaffByGender method to filter staff by gender
        List<Staff> filteredStaffByGender = CustomLambdaFunctions.filterStaffByGender(
                hospital.getStaffList(), Gender.MALE);
        System.out.println("Filtered Staff by Gender (Male): " + filteredStaffByGender);

        // Using the mapStaffNames method to map staff to their names
        List<String> staffNames = CustomLambdaFunctions.mapStaffNames(hospital.getStaffList());
        System.out.println("Staff Names: " + staffNames);

        // Using the calculateAverageAge method to calculate the average age of staff
        double averageAge = CustomLambdaFunctions.calculateAverageAge(hospital.getStaffList());
        System.out.println("Average Age of Staff: " + averageAge);

    }
}