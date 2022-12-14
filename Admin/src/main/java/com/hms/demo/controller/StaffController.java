package com.hms.demo.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hms.demo.exception.ResourceNotFoundException;
import com.hms.demo.model.Staff;
import com.hms.demo.repository.StaffRepository;
import com.hms.demo.service.SequenceGeneratorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@RequestMapping("/api/v1") 
public class StaffController {
	@Autowired
	private StaffRepository staffRepository;
	
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;


	@GetMapping("/staff")
	public List<Staff> getAllStaff() {
		return staffRepository.findAll();
	}
	
	@GetMapping("/staff/count")
	public int getAllStaffCount() {
		List<Staff> staffList = staffRepository.findAll();
		int count =0;
		for(Staff staff:staffList) {
			count++;
		}
		return count;
	}


	@GetMapping("/staff/{id}")
	public ResponseEntity<Staff> getStaffById(@PathVariable(value = "id") Long staffId)
			throws ResourceNotFoundException {
		Staff staff = staffRepository.findById(staffId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + staffId));
		return ResponseEntity.ok().body(staff);
	}

	@PostMapping("/staff")
	public Staff createEmployee(@Valid @RequestBody Staff staff) {
		staff.setId(sequenceGeneratorService.generateSequence(Staff.SEQUENCE_NAME));
		return staffRepository.save(staff);
	}

	@PutMapping("/staff/{id}")
	public ResponseEntity<Staff> updateEmployee(@PathVariable(value = "id") Long staffId,
			 @Valid @RequestBody Staff staffDetails) throws ResourceNotFoundException {
		Staff staff = staffRepository.findById(staffId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + staffId));

		staff.setEmailId(staffDetails.getEmailId());
		staff.setLastName(staffDetails.getLastName());
		staff.setFirstName(staffDetails.getFirstName());
		final Staff updatedStaff = staffRepository.save(staff);
		return ResponseEntity.ok(updatedStaff);
	}


	@DeleteMapping("/staff/{id}")
	public Map<String, Boolean> deleteStaff(@PathVariable(value = "id") Long staffId)
			throws ResourceNotFoundException {
		Staff staff = staffRepository.findById(staffId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + staffId));

		staffRepository.delete(staff);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
