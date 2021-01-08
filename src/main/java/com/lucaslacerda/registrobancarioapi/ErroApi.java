package com.lucaslacerda.registrobancarioapi;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class ErroApi extends RuntimeException{
		public ErroApi(String message, Exception e) {
			super(message,e);
		}
}
