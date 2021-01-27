package com.example.demo.todoController;

import java.util.List;

import org.apache.catalina.LifecycleListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Todo;
import com.example.demo.service.TodoService;

@RestController
//@RequestMapping("/api")
public class TodoController {

	@Autowired
	private TodoService todoService;
	
	@GetMapping("/todo")
	public ResponseEntity<?> getAllTodos() {
		List<Todo> todos = this.todoService.getAllTodos();
		return ResponseEntity.ok().body(todos);
	}
	
	@PostMapping("/newTodo")
	public ResponseEntity<?> newTodo(@RequestParam("title") String title){
		this.todoService.createNewTodo(title);
		return ResponseEntity.ok().body("Create Successfully");
	}
	
	@PutMapping("/completingTodo")
	public ResponseEntity<?> completingTodo(@RequestParam("id") Long id){
		return ResponseEntity.ok().body(this.todoService.completingTodo(id));
	}
	
	@DeleteMapping("/deleteTodo")
	public ResponseEntity<?> deleteTodo(@RequestParam("id") Long id){
		return ResponseEntity.ok().body(this.todoService.deleteTodo(id));
	}
}

