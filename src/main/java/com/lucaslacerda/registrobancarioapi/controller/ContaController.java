package com.lucaslacerda.registrobancarioapi.controller;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.ParameterExpression;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.expression.ExpressionException;
import org.springframework.jmx.MBeanServerNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.lucaslacerda.registrobancarioapi.ErroApi;
import com.lucaslacerda.registrobancarioapi.model.Cliente;
import com.lucaslacerda.registrobancarioapi.model.Conta;
import com.lucaslacerda.registrobancarioapi.repository.ClienteRepository;
import com.lucaslacerda.registrobancarioapi.repository.ContaRepository;

@RestController
@RequestMapping("/apibanco")
public class ContaController {

	@Autowired
	ClienteRepository clienteRepository;
	@Autowired
	ContaRepository contaRepository;
	
	@PersistenceContext
    private EntityManager entityManager;
	
	//Abertura de conta
	@PostMapping("/abrirconta")
	public Conta aberturaConta(@RequestBody Cliente cliente) {
		
		cliente = cadastraCliente(cliente);
		
		Conta conta = new Conta(cliente.getId(),0);

		return contaRepository.save(conta);
	}
	
	//Cadastro cliente
	public Cliente cadastraCliente(@RequestBody Cliente cliente) {
		
		String campoInvalido = "Requisição invalida";
		
		try {
			if(cliente.getCpf()==null || !validaCPF(cliente.getCpf())) {
				campoInvalido = "CPF invalido, favor verificar!";
				throw new Exception();
			}else if(cliente.getEmail()==null || !validaEmail(cliente.getEmail())) {
				campoInvalido = "Email invalido, favor verificar!";
				throw new Exception();
			}else if(cliente.getNome()==null || cliente.getNome().substring(0, 1).equals(" ")) {
				campoInvalido = "Nome invalido, favor verificar!";
				throw new Exception();
			}else if(cliente.getNascimento()==null) {
				campoInvalido = "Data de Nascimento invalida, favor verificar!";
				throw new Exception();
			}
			
			 cliente.setCpf(cliente.getCpf().replaceAll("\\D",""));
			 return clienteRepository.save(cliente);
		}catch (Exception e) {
			throw new ErroApi(campoInvalido,e);
		}
			 
	}
	
	
	
	//Verifica cpf
	 public boolean validaCPF(String CPF) {
		 CPF = CPF.replaceAll("\\D","");
		 if (CPF.length() != 11 || CPF.substring(0,1).equals(""))
	            return(false);
		 
		 	Query query = entityManager.createNativeQuery("SELECT * FROM cliente where cpf = :pessoafisica");
	        query.setParameter("pessoafisica", CPF);

	        List<Object[]> objectList = query.getResultList();	    
	        if(objectList.size()>0) {
	        	return false;
	        }
	        
	        
	        char dig10, dig11;
	        int sm, i, r, num, peso;

	        try {
	            sm = 0;
	            peso = 10;
	            for (i=0; i<9; i++) {
		            num = (int)(CPF.charAt(i) - 48);
		            sm = sm + (num * peso);
		            peso = peso - 1;
	            }

	            r = 11 - (sm % 11);
	            if ((r == 10) || (r == 11))
	                dig10 = '0';
	            else dig10 = (char)(r + 48);

	            sm = 0;
	            peso = 11;
	            for(i=0; i<10; i++) {
	            num = (int)(CPF.charAt(i) - 48);
	            sm = sm + (num * peso);
	            peso = peso - 1;
	            }

	            r = 11 - (sm % 11);
	            if ((r == 10) || (r == 11))
	                 dig11 = '0';
	            else dig11 = (char)(r + 48);

	            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
	                 return(true);
	            else return(false);
	           } catch (Exception erro) {
	                return(false);
	            }
	}
	
	 //Verifica Email
	 public boolean validaEmail(String email) {
	        boolean result = true;
	        Query query = entityManager.createNativeQuery("SELECT * FROM cliente where email = :endereco");
	        query.setParameter("endereco", email);

	        List<Object[]> objectList = query.getResultList();	    
	        if(objectList.size()>0) {
	        	return false;
	        }
	        
	        try {
	            InternetAddress emailAddr = new InternetAddress(email);
	            emailAddr.validate();
	        } catch (AddressException ex) {
	            result = false;
	        }
	        return result;
	    }
	 
	 
	 
	 

	public ClienteRepository getClienteRepository() {
		return clienteRepository;
	}

	public void setClienteRepository(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	public ContaRepository getContaRepository() {
		return contaRepository;
	}

	public void setContaRepository(ContaRepository contaRepository) {
		this.contaRepository = contaRepository;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	 
	 
}
