package com.example.hms.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hms.service.RoomService;
import com.example.hms.repository.*;
import com.example.hms.service.*;
import com.example.hms.model.*;

@RestController
@RequestMapping("/api/v3")
public class RoomController {
	
	@Autowired
	private RoomService service;

	
	@GetMapping("/room")
	public List<Room> findAll(){
		
		return service.findAll();
	}
	
	@PostMapping("/room")
	public String createRoom(@RequestBody Room room) {
		System.out.println("CAME");
		service.createRoom(room);
		return "room created";
	}
	
	@DeleteMapping("/room/{roomId}")
	public String deleteRoom(@PathVariable Long roomId) {
		
		service.deleteRoom(roomId);
		return "deleted successfully";
	}
	
	@PutMapping("/room/{roomId}")
	public void updateRoom(@RequestBody Room room) {
		
		service.updateRoom(room);
	}
	
	
	@GetMapping("/room/{roomId}")
	public Optional<Room> findById(@PathVariable Long roomId) {
		
		return service.findById(roomId);
	}
	
}
