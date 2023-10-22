package com.michal.booksylikeapp.mapper;

import com.michal.booksylikeapp.dto.ClientDto;
import com.michal.booksylikeapp.entity.Client;

public class ClientMapper {

    public static Client mapToClient(ClientDto clientDto, Client client){

        // when creating
        if(client == null) {
            client = new Client();
        }

        client.setFirstName(clientDto.getFirstName());
        client.setLastName(clientDto.getLastName());
        client.setEmail(clientDto.getEmail());
        client.setUsername(clientDto.getUsername());
        client.setPassword(clientDto.getPassword());

        return client;
    }

    public static ClientDto mapToClientDto(Client client){

        ClientDto clientDto = new ClientDto();

        clientDto.setId(client.getId());
        clientDto.setFirstName(client.getFirstName());
        clientDto.setLastName(client.getLastName());
        clientDto.setEmail(client.getEmail());
        clientDto.setUsername(client.getUsername());
        clientDto.setPassword(client.getPassword());

        return clientDto;
    }
}
