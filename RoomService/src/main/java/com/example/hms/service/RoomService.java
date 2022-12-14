package com.example.hms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.hms.model.Room;
import com.example.hms.repository.RoomRepository;
@Service
public class RoomService {

	@Autowired
	private RoomRepository roomrepository;
	
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	
	public List<Room> findAll(){
		
		return roomrepository.findAll();
	}
	
	public void createRoom(@RequestBody Room room) {
		
		
		room.setId(sequenceGeneratorService.generateSequence(room.SEQUENCE_NAME));
			roomrepository.save(room);
		}
		
	public String deleteRoom(@PathVariable Long roomId) {
		
		roomrepository.deleteById(roomId);
		return "deleted successfully";
	}
	public void updateRoom(@RequestBody Room room) {
		
		roomrepository.save(room);
	}
	
	public Optional<Room> findById(@PathVariable Long roomId) {
		
		return roomrepository.findById(roomId);
	}

}
