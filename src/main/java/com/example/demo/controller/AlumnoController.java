package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Alumno;
import com.example.demo.service.IAlumnoService;

@RestController
@RequestMapping("/api")
public class AlumnoController {

	@Autowired
	private IAlumnoService alumnoService;
	
	// http://localhost:8080/api/alumnos
	@GetMapping("/alumnos")
	public List<Alumno> index(){
		return alumnoService.getAlumnos();
	}

	//listar alumnos
	/*@GetMapping("/alumno/{id}")
	public Alumno show(@PathVariable Long id) {
		return alumnoService.findAlumnoById(id);
	}*/
	
	//http://localhost:8080/api/alumno/1
	@GetMapping("/alumno/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		Alumno alumno = null;
		Map<String, Object> response =  new HashMap<>();
		
		try {
			alumno = alumnoService.findAlumnoById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Erro de acceso a la base de datos");
			response.put("error", e.getMessage().concat(" ".concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		if (alumno == null) {
			response.put("mensaje", "El alumno no se encuentra en el sistema");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Alumno>(alumno, HttpStatus.OK);
	}
	
	// guardar alumno
	/*@PostMapping("/save")
	public Alumno create(@RequestBody Alumno alumno) {
		return alumnoService.saveAlumno(alumno);
	}*/
	
	//http://localhost:8080/api/save
	// Pasar json al post> { "nombre": "Tatiana", "apellidos": "Rojas", "curso": "Física", "nota": 20.0 }
	@PostMapping("/save")
	public ResponseEntity<?> create(@RequestBody Alumno alumno) {
		// ?: comodin para que acepte cualquier tipo de objeto
		Alumno alumnoNuevo = null;
		Map<String, Object> response =  new HashMap<>();
		
		try {
			alumnoNuevo = alumnoService.saveAlumno(alumno);
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Erro al insertar al alumno a la base de datos");
			response.put("error", e.getMessage().concat(" ".concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Alumno creado con exito");
		response.put("alumno", alumnoNuevo);
		
		return new ResponseEntity<Alumno>(alumno, HttpStatus.CREATED);
		
	}
	
	// actualizar alumno
	/*@PutMapping("/update/{id}")
	public Alumno update(@RequestBody Alumno alumno, @PathVariable Long id) {
		Alumno alumnoActual = alumnoService.findAlumnoById(id);
		alumnoActual.setNombre(alumno.getNombre());
		alumnoActual.setApellidos(alumno.getApellidos());
		alumnoActual.setCurso(alumno.getCurso());
		alumnoActual.setNota(alumno.getNota());
		return alumnoService.saveAlumno(alumnoActual);
	}*/
	
	// http://localhost:8080/api/update/6
	// Pasar json al put> { "nombre": "María", "apellidos": "Altamirano", "curso": "Matemática", "nota": 20.0 }
	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@RequestBody Alumno alumno, @PathVariable Long id) {
		Alumno alumnoActual = alumnoService.findAlumnoById(id);
		Alumno alumnoActualizado = null;
		Map<String, Object> response =  new HashMap<>();
		
		if (alumnoActual == null) {
			response.put("mensaje", "No se ha podido actualizar al alumno porque no existe en el sistema");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			alumnoActual.setNombre(alumno.getNombre());
			alumnoActual.setApellidos(alumno.getApellidos());
			alumnoActual.setCurso(alumno.getCurso());
			alumnoActual.setNota(alumno.getNota());
			
			alumnoActualizado = alumnoService.saveAlumno(alumnoActual);
		} catch (DataAccessException e) {
			response.put("mensaje", "Erro al actualizar al alumno a la base de datos");
			response.put("error", e.getMessage().concat(" ".concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		response.put("mensaje", "Alumno actualizado con exito");
		response.put("alumnoActualizado", alumnoActualizado);
		
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		
	}
	
	
	// eliminar alumno
	/*@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable Long id) {
		alumnoService.delete(id);
	}*/
	
	// http://localhost:8080/api/delete/6
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response =  new HashMap<>();
		
		try {
			alumnoService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Erro al eliminar al alumno a la base de datos");
			response.put("error", e.getMessage().concat(" ".concat(e.getMostSpecificCause().getMessage())));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		
		}
		
		response.put("mensaje", "Alumno eliminado con exito");
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		
	}
	
}
