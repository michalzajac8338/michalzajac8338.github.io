package com.michal.springboot;

import com.michal.springboot.dto.EmployeeDto;
import com.michal.springboot.repository.EmployeeRepository;
import com.michal.springboot.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerIntegrationTests {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void before(){
        System.out.println("Before Each Test");
        employeeRepository.deleteAll().subscribe();
    }

    @Test
    public void testSaveEmployee(){

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Martyna");
        employeeDto.setLastName("Z");
        employeeDto.setEmail("m @gmail.com");

        webTestClient.post().uri("/api/employees").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(employeeDto), EmployeeDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName())
                .jsonPath("$.lastName").isEqualTo(employeeDto.getLastName())
                .jsonPath("$.email").isEqualTo(employeeDto.getEmail());

    }

    @Test
    public void testGetSingleEmployee(){

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Monika");
        employeeDto.setLastName("Zajac");
        employeeDto.setEmail("monika@gmail.com");

        EmployeeDto savedEmployee = employeeService.saveEmployee(employeeDto).block();

        webTestClient.get().uri("/api/employees/{id}", Collections.singletonMap("id", savedEmployee.getId()))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName())
                .jsonPath("$.lastName").isEqualTo(employeeDto.getLastName())
                .jsonPath("$.email").isEqualTo(employeeDto.getEmail())
                .jsonPath("$.id").isEqualTo(savedEmployee.getId());

    }

    @Test
    public void testGetAll(){

        webTestClient.get().uri("/api/employees")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(EmployeeDto.class)
                .consumeWith(System.out::println);


    }

    @Test
    public void testUpdate(){

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Mario");
        employeeDto.setLastName("ZZZ");
        employeeDto.setEmail("mario@gmail.com");

        EmployeeDto savedEmployee = employeeService.saveEmployee(employeeDto).block();

        EmployeeDto updatedEmployee = new EmployeeDto();
        updatedEmployee.setFirstName("Mar");
        updatedEmployee.setLastName("Zaj");
        updatedEmployee.setEmail("mario@onet.pl");

        webTestClient.put().uri("/api/employees/{id}", Collections.singletonMap("id",savedEmployee.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(updatedEmployee), EmployeeDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.firstName").isEqualTo(updatedEmployee.getFirstName())
                .jsonPath("$.lastName").isEqualTo(updatedEmployee.getLastName())
                .jsonPath("$.email").isEqualTo(updatedEmployee.getEmail())
                .jsonPath("$.id").isEqualTo(savedEmployee.getId());
    }

    @Test
    public void testDelete(){

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Mario");
        employeeDto.setLastName("ZZZ");
        employeeDto.setEmail("mario@gmail.com");

        EmployeeDto savedEmployee = employeeService.saveEmployee(employeeDto).block();

        webTestClient.delete().uri("api/employees/{id}", Collections.singletonMap("id", savedEmployee.getId()))
                .exchange()
                .expectStatus().isNoContent()
                .expectBody().consumeWith(System.out::println);
    }
}
