package com.danley.crudclient.controllers;


import com.danley.crudclient.dto.ClientDto;
import com.danley.crudclient.services.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private  ClientService clientService;

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> findById(@PathVariable Long id) {
        ClientDto clientDto = clientService.findById(id);

        return ResponseEntity.ok(clientDto);
    }

    @GetMapping
    public ResponseEntity<Page<ClientDto>> findAll(Pageable pageable) {
        Page<ClientDto> dto = clientService.findAll(pageable);

        return ResponseEntity.ok(dto);
    }


    @PostMapping
    public ResponseEntity<ClientDto> insert(@Valid @RequestBody ClientDto clientDto) {
        clientDto = clientService.insert(clientDto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(clientDto.getId())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(clientDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDto> update(@PathVariable Long id, @Valid @RequestBody ClientDto clientDto) {
        clientDto = clientService.update(id, clientDto);
        return ResponseEntity.ok(clientDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
