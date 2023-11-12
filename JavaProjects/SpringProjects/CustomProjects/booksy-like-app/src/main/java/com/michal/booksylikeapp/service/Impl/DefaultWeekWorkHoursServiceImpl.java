package com.michal.booksylikeapp.service.Impl;

import com.michal.booksylikeapp.dto.DefaultWeekWorkHoursDto;
import com.michal.booksylikeapp.entity.DefaultWeekWorkHours;
import com.michal.booksylikeapp.entity.Employee;
import com.michal.booksylikeapp.repository.DefaultWeekWorkHoursRepository;
import com.michal.booksylikeapp.repository.EmployeeRepository;
import com.michal.booksylikeapp.service.DefaultWeekWorkHoursService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DefaultWeekWorkHoursServiceImpl implements DefaultWeekWorkHoursService {

    private EmployeeRepository employeeRepository;
    private DefaultWeekWorkHoursRepository weekWorkHoursRepository;

    @Override
    @Transactional
    public DefaultWeekWorkHoursDto setDefaultWeekWorkHours(Long employeeId, DefaultWeekWorkHoursDto dto) {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(RuntimeException::new);
        Map<String, Map<String, String>> savedDefaultWWHDto = new HashMap<>();
        HashMap<String, String> savedHours = new HashMap<>();

        // saving default work hours to db
        for (DayOfWeek day : DayOfWeek.values()) {
            savedHours.clear();

            // hashmap of hours (allows employee to work twice a day, schedule breaks etc.)
            Map<String, String> hours = dto.getDayOfWeekWorkHours().get(day.toString());

            if (hours != null) {
                for (String k : hours.keySet()) {
                    DefaultWeekWorkHours defaultWeekWorkHours =
                            new DefaultWeekWorkHours(null, day, LocalTime.parse(k), LocalTime.parse(hours.get(k)), employee);

                    DefaultWeekWorkHours saved = weekWorkHoursRepository.save(defaultWeekWorkHours);

                    savedHours.put(saved.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                            saved.getEndTime().format(DateTimeFormatter.ofPattern("HH:mm")));
                }

                savedDefaultWWHDto.put(day.toString(), (Map<String, String>) savedHours.clone());
            }
        }

        return DefaultWeekWorkHoursDto.builder().dayOfWeekWorkHours(savedDefaultWWHDto).build();
    }

    @Override
    public DefaultWeekWorkHoursDto readDefaultWeekWorkHours(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(RuntimeException::new);
        List<DefaultWeekWorkHours> defaultWeekWorkHours = employee.getDefaultWeekWorkHours();
        Map<String, Map<String, String>> savedDefaultWWHDto = new HashMap<>();

        Map<DayOfWeek, List<DefaultWeekWorkHours>> daysAndHours = defaultWeekWorkHours.stream()
                .collect(Collectors.groupingBy(DefaultWeekWorkHours::getDayOfWeek));


        daysAndHours.forEach(
                (day, listOfHours) -> {
                    savedDefaultWWHDto.put(day.name(),

                            listOfHours.stream()
                                    .collect(Collectors.toMap(shift -> shift.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                                            shift -> shift.getEndTime().format(DateTimeFormatter.ofPattern("HH:mm")))));
                }
        );

        return DefaultWeekWorkHoursDto.builder().dayOfWeekWorkHours(savedDefaultWWHDto).build();
    }
}