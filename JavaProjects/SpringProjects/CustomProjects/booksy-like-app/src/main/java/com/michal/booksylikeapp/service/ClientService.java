package com.michal.booksylikeapp.service;

import com.michal.booksylikeapp.dto.ClientDto;

public interface ClientService {
    ClientDto createClient(ClientDto clientDto);

    ClientDto readClient(Long id);

    ClientDto updateClient(Long id, ClientDto clientDto);

    void deleteClient(Long id);
}
