package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Alumno;

public interface IAlumnoService {

	public List<Alumno> getAlumnos();

	public Alumno findAlumnoById(Long id);

	public Alumno saveAlumno(Alumno alumno);

	public void delete(Long id);
	
}
