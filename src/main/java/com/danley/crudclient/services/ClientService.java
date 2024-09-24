package com.danley.crudclient.services;


import com.danley.crudclient.dto.ClientDto;
import com.danley.crudclient.models.Client;
import com.danley.crudclient.repositories.ClientRepository;
import com.danley.crudclient.services.exceptions.DatabaseException;
import com.danley.crudclient.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

//    find client by id
   @Transactional(readOnly = true)
    public ClientDto findById(Long id) {
       Client client = clientRepository.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));

       return new ClientDto(client);
   }


//   update client
    @Transactional
    public ClientDto update(Long id, ClientDto clientDto) {

       try {
           Client entity = clientRepository.getReferenceById(id);
            copyDtoToEntity(clientDto, entity);
            entity = clientRepository.save(entity);
            return new ClientDto(entity);

       } catch (EntityNotFoundException e) {
           throw new ResourceNotFoundException("client not found");
       }
    }

//    delete client
    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
       if (!clientRepository.existsById(id)) {
           throw new ResourceNotFoundException("Resource not found");
       }

       try {
           clientRepository.deleteById(id);
       } catch (DataIntegrityViolationException e) {
           throw new DatabaseException("Integrity violation");
       }
    }

//    insert client
    @Transactional
    public ClientDto insert(ClientDto clientDto) {
       Client entity = new Client();
       copyDtoToEntity(clientDto, entity);
       entity = clientRepository.save(entity);
       return new ClientDto(entity);
    }



//    pageable list
    @Transactional(readOnly = true)
    public Page<ClientDto> findAll(Pageable pageable) {
       Page<Client> clients = clientRepository.findAll(pageable);

       return clients.map(ClientDto::new);
    }


// mapper client
    private void copyDtoToEntity(ClientDto clientDto, Client entity) {
       entity.setName(clientDto.getName());
       entity.setCpf(clientDto.getCpf());
       entity.setIncome(clientDto.getIncome());
       entity.setBirthDate(clientDto.getBirthDate());
       entity.setChildren(clientDto.getChildren());
    }
}
