package com.example.basesdedatos;

public class Usuario {

	public long id;
	public String name;
	public String drink;
	public String sport;
	
	public Usuario(String name, String drink, String sport) throws Exception {
		super();
		if(name.isEmpty() || drink.isEmpty() || sport.isEmpty()) throw new Exception("Algun campo viene vacío");
		this.name = name;
		this.drink = drink;
		this.sport = sport;
		this.id = 0;
	}
	
	public Usuario(long id, String name, String drink, String sport) throws Exception {
		super();
		if(id<1 || name.isEmpty() || drink.isEmpty() || sport.isEmpty()) throw new Exception("Algun campo viene vacío");
		this.name = name;
		this.drink = drink;
		this.sport = sport;
		this.id = id;
	}	
}
