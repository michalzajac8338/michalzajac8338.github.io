package com.michal.booksylikeapp.service.Impl;

import com.michal.booksylikeapp.dto.ClientDto;
import com.michal.booksylikeapp.entity.Client;
import com.michal.booksylikeapp.entity.Role;
import com.michal.booksylikeapp.mapper.ClientMapper;
import com.michal.booksylikeapp.repository.ClientRepository;
import com.michal.booksylikeapp.repository.RoleRepository;
import com.michal.booksylikeapp.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public ClientDto createClient(ClientDto clientDto) {

        Client client = ClientMapper.mapToClient(clientDto, null);
        client.setRole(roleRepository.findByName("CLIENT"));
        Client savedClient = clientRepository.save(client);

        return ClientMapper.mapToClientDto(savedClient);
    }

    @Override
    public ClientDto readClient(Long id) {

        Client queriedClient = clientRepository.findById(id).orElseThrow(RuntimeException::new);
        return ClientMapper.mapToClientDto(queriedClient);
    }

    @Override
    public ClientDto updateClient(Long id, ClientDto clientDto) {

        Client existingClient = clientRepository.findById(id).orElseThrow(RuntimeException::new);
        Client updatedClient = ClientMapper.mapToClient(clientDto, existingClient);

        return ClientMapper.mapToClientDto(updatedClient);
    }

    @Override
    public void deleteClient(Long id) {

        clientRepository.deleteById(id);

    }
}
