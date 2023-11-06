package com.michal.booksylikeapp.contoller.clientRequests;

import com.michal.booksylikeapp.dto.ClientDto;
import com.michal.booksylikeapp.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("B/client")
@AllArgsConstructor
public class CRUDController {

    private ClientService clientService;

    // CRUD FOR CLIENTS
    // Create
    @PostMapping
    public ResponseEntity<ClientDto> createClient(@RequestBody ClientDto clientDto){

        ClientDto createdClient = clientService.createClient(clientDto);
        return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
    }

    // Read
    @GetMapping("/clientId={clientId}")
    public ResponseEntity<ClientDto> readClient(@PathVariable Long clientId){

        ClientDto queriedClient = clientService.readClient(clientId);
        return new ResponseEntity<>(queriedClient, HttpStatus.OK);
    }

    // Update
    @PutMapping("/clientId={clientId}")
    public ResponseEntity<ClientDto> updateClient(@PathVariable Long clientId,
                                                  @RequestBody ClientDto clientDto){

        ClientDto updatedClient = clientService.updateClient(clientId, clientDto);
        return new ResponseEntity<>(updatedClient, HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/clientId={clientId}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long clientId){

        clientService.deleteClient(clientId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
