package com.naiaraodiaga.calculadora;

public class Calculadora {

	private boolean ultimaEntradaEsOperador;
	private String display = "", operador, operando1;
	
	public void inputDigito(String num){
		if(ultimaEntradaEsOperador){
	        ultimaEntradaEsOperador = false;
	        display = "";
		} 
	    if(!num.equalsIgnoreCase(".") ||
	       (num.equalsIgnoreCase(".")  && !hasDot(display) && !this.display.equalsIgnoreCase(""))){
	    	display += num;
	    }
	}
	
	
	public String displayValor(){
	    if(display.equalsIgnoreCase("")){
	        return "";
	    }
	    return display;
	}
	
	
	public boolean hasDot(String str){
	     char dot = '.';
	 
	     for(int i = 0; i < str.length(); i ++){
	         if(str.charAt(i)  == dot){
	             return true;
	         }
	     }
	     return false;
	 }
	
	
	public void inputOperador(String operador){
//	    if(!operando1.equalsIgnoreCase("") && !operador.equalsIgnoreCase("")){
//	        inputIgual();
//	    }
	    
	    this.operador = operador;
	    this.operando1 = this.display;
	    
	    this.ultimaEntradaEsOperador = true;
	}
	
	
	public void inputIgual(){
	    if(!this.operando1.isEmpty()){
	        double operando1 = Double.parseDouble(this.operando1);
	        double operando2 = Double.parseDouble(this.display);
	        if(this.operador.equalsIgnoreCase("+")){
	        	this.display = String.valueOf(operando1 + operando2);
	        }
	        else if(this.operador.equalsIgnoreCase("-")){
	        	this.display = String.valueOf(operando1 - operando2);
	        }
	        else if(this.operador.equalsIgnoreCase("*")){
	        	this.display = String.valueOf(operando1 * operando2);
	        }
	        else if(this.operador.equalsIgnoreCase("/")){
	        	this.display = String.valueOf(operando1 / operando2);
	        }
	        
	        this.operando1 = "";

	        this.operador = "";
	    }
	}
	
	
	public void inputBorrar(){
		this.display = "";
	}
	
}
