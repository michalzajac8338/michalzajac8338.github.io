package com.michal.booksylikeapp.contoller;

import com.michal.booksylikeapp.entity.Visit;
import com.michal.booksylikeapp.service.ClientVisitService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("B/client/id={id}")
@AllArgsConstructor
public class ClientVisitController {

    private ClientVisitService clientVisitService;

    // Client CRUD Visits
    // Create
    @PostMapping("/visit")
    public ResponseEntity<Visit> createVisit(@PathVariable Long id,
                                             @RequestBody Visit visit){



        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
