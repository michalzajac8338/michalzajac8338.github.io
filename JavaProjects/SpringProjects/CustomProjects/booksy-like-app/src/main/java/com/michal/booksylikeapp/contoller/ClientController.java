package com.michal.booksylikeapp.contoller;

import com.michal.booksylikeapp.dto.ClientDto;
import com.michal.booksylikeapp.service.ClientService;
import com.michal.booksylikeapp.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("B/client")
@AllArgsConstructor
public class ClientController {

    private ClientService clientService;
    private RoleService roleService;

    // CRUD FOR CLIENTS
    // Create
    @PostMapping
    public ResponseEntity<ClientDto> createClient(@RequestBody ClientDto clientDto){

//        roleService.initializeRoles();
        ClientDto createdClient = clientService.createClient(clientDto);
        return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
    }

    // Read
    @GetMapping("/id={id}")
    public ResponseEntity<ClientDto> readClient(@PathVariable Long id){

        ClientDto queriedClient = clientService.readClient(id);
        return new ResponseEntity<>(queriedClient, HttpStatus.OK);
    }

    // Update
    @PutMapping("/id={id}")
    public ResponseEntity<ClientDto> updateClient(@PathVariable Long id,
                                                  @RequestBody ClientDto clientDto){

        ClientDto updatedClient = clientService.updateClient(id, clientDto);

        return new ResponseEntity<>(updatedClient, HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/id={id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id){

        clientService.deleteClient(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
