package com.lucaslacerda.registrobancarioapi.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "conta")
public class Conta{

	 

	  public Conta() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Conta(Long cliente, float saldo) {
		super();
		this.cliente = cliente;
		this.saldo = saldo;
	}


   	  @Id
	  @Column(name="conta_id")
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;
	  
	  //@ManyToOne(targetEntity = Cliente.class,fetch = FetchType.LAZY)
	 // @JoinColumn(name = "cliente",referencedColumnName = "cliente_id")
	  private Long cliente;
	  
	  @Column(name="saldo")
	  private float saldo = 0;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCliente() {
		return cliente;
	}

	public void setCliente(Long cliente) {
		this.cliente = cliente;
	}

	public float getSaldo() {
		return saldo;
	}

	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}
	  
	  
	}
