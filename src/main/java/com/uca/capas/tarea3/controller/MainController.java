package com.uca.capas.tarea3.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

	@RequestMapping("/ingresar")
	public String index() {
		return "ingresar";
	}
	
	@RequestMapping("/guardarAlumno")
	public ModelAndView guardarAlumno(HttpServletRequest request) throws ParseException {
		ModelAndView mav = new ModelAndView();
		
		String nombres = request.getParameter("nombres");
		String apellidos = request.getParameter("apellidos");
		String fechaNacimiento = request.getParameter("fechaNacimiento");
		String lugarNacimiento = request.getParameter("lugarNacimiento");
		String colegio = request.getParameter("colegio");
		String telMovil = request.getParameter("telMovil");
		String telFijo = request.getParameter("telFijo");
		
		List<String> errores = new ArrayList<>();
		
		if(!validarLongitud(nombres,1,25)) errores.add("Los nombres deben de contener de 1 a 25 caracteres");
		if(!validarLongitud(apellidos,1,25)) errores.add("Los apellidos deben de contener de 1 a 25 caracteres");
		
		Date fNacimiento=new SimpleDateFormat("dd/MM/yyyy").parse(fechaNacimiento);  
		Date fMaxima=new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2003");
		if(fNacimiento.after(fMaxima)) errores.add("La fecha de nacimiento no puede ser despues del 1 de Enero del 2003");
		
		if(!validarLongitud(colegio, 1, 100)) errores.add("El colegio debe contener de 1 a 100 caracteres");
		
		String regex = "[0-9]{8}";
		if(!telMovil.matches(regex)) errores.add("El telefono movil debe ser de 8 digitos exactamente, solamente numeros");
		if(!telFijo.matches(regex)) errores.add("El telefono fijo debe ser de 8 digitos exactamente, solamente numeros");
		
		
		if(errores.isEmpty()) mav.setViewName("/success");
		else {
			mav.addObject("errores", errores);
			mav.setViewName("errores");
		}
		
		return mav;
	}
	
	
	private boolean validarLongitud(String string,int min,int max) {
		if(string.length()<min || string.length()>max) return false;
		return true;
	}
	
}
