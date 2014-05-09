package com.naiaraodiaga.calculadorafragment;

public class Calculadora {

	private boolean ultimaEntradaEsOperador;
	private String display = "", operador = "", operando1 = "";
	
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
	     String dot = ".";
	     
	     if(str.contains(dot)){
	    	 return true;
	     }

	     return false;
	 }
	
	
	public void inputOperador(String operador){
	    this.operador = operador;
	    this.operando1 = this.display;
	    
	    this.ultimaEntradaEsOperador = true;
	}
	
	
	public void inputIgual(){
	    if(!this.operando1.isEmpty() && !ultimaEntradaEsOperador){
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
	
	
	public String getOperador() {
		return operador;
	}


	public void setOperador(String operador) {
		this.operador = operador;
	}


	public String getOperando1() {
		return operando1;
	}


	public void setOperando1(String operando1) {
		this.operando1 = operando1;
	}


	public String getDisplay() {
		return display;
	}


	public void setDisplay(String display) {
		this.display = display;
	}


	public boolean isUltimaEntradaEsOperador() {
		return ultimaEntradaEsOperador;
	}


	public void setUltimaEntradaEsOperador(boolean ultimaEntradaEsOperador) {
		this.ultimaEntradaEsOperador = ultimaEntradaEsOperador;
	}
	
	
	
}
