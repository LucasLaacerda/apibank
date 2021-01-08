package com.lucaslacerda.registrobancarioapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.lucaslacerda.registrobancarioapi.model.Cliente;
import com.lucaslacerda.registrobancarioapi.model.Conta;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long>{

}
