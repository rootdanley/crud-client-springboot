package com.danley.crudclient.repositories;

import com.danley.crudclient.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
