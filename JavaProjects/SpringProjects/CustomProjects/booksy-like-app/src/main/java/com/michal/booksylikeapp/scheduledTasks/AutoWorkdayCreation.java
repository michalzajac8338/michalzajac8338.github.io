package com.michal.booksylikeapp.scheduledTasks;

import com.michal.booksylikeapp.constants.OtherConstants;
import com.michal.booksylikeapp.entity.DefaultWeekWorkHours;
import com.michal.booksylikeapp.entity.Employee;
import com.michal.booksylikeapp.entity.Workday;
import com.michal.booksylikeapp.repository.DefaultWeekWorkHoursRepository;
import com.michal.booksylikeapp.repository.EmployeeRepository;
import com.michal.booksylikeapp.repository.WorkdayRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


@Component
@AllArgsConstructor
public class AutoWorkdayCreation {

    private EmployeeRepository employeeRepository;
    private WorkdayRepository workdayRepository;
    private DefaultWeekWorkHoursRepository defaultWeekWorkHoursRepository;

    // to create workdays automatically due to set DefaultWeekWorkHours
    @Scheduled(cron = "1 0 * * * *", zone = "Europe/Paris") // minute past midnight
    public void autoWorkdaysCreator(){
//        List<Employee> employees = employeeRepository.findAll();
//        employees.forEach(e->createWorkdaysForSingleEmployee(e.getId()));
        createWorkdaysForSingleEmployee();
    }

    @Transactional
    public void createWorkdaysForSingleEmployee(){

        Employee employee = employeeRepository.findById(1L).orElseThrow(RuntimeException::new);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime updateUpTo = now.plusDays(OtherConstants.DAYS_TO_AUTO_SCHEDULE.getNumber());

        LocalDateTime updatedUpTo = (employee.getWorkdayUpdatedUpTo()!=null) ? employee.getWorkdayUpdatedUpTo() : now;
        Set<DefaultWeekWorkHours> defaultWeekWorkHoursList = defaultWeekWorkHoursRepository.findByEmployee(employee);

        List<LocalDate> dates = workdayRepository.findByEmployee(employee).stream().map(Workday::getDate).toList();

        while (!updatedUpTo.toLocalDate().isAfter(updateUpTo.toLocalDate())){

            // skip if employee has already created workday manually
            if(dates.contains(updatedUpTo.toLocalDate())){continue;}

            // find DefaultWeekWorkHours for day of week of current iteration
            DayOfWeek dayOfWeek = updatedUpTo.getDayOfWeek();
            List<DefaultWeekWorkHours> workHoursForSingleDayOfWeek =
                    defaultWeekWorkHoursList.stream().filter(dwwh -> dwwh.getDayOfWeek() == dayOfWeek).toList();

            for(DefaultWeekWorkHours dwwh : workHoursForSingleDayOfWeek){
                Workday workday = Workday.builder().date(updatedUpTo.toLocalDate())
                        .workStartTime(LocalDateTime.of(updatedUpTo.toLocalDate(), dwwh.getStartTime()))
                        .workEndTime(LocalDateTime.of(updatedUpTo.toLocalDate(), dwwh.getEndTime()))
                        .employee(employee).build();

//                employee.getWorkdayList().add(workday);

                workdayRepository.save(workday);
            }
            updatedUpTo = updatedUpTo.plusDays(1);
        }

        employee.setWorkdayUpdatedUpTo(updatedUpTo.minusDays(1));
        employeeRepository.save(employee);
    }
}
