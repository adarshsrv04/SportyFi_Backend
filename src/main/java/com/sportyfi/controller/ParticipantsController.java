package com.sportyfi.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sportyfi.entity.Participants;
import com.sportyfi.services.ParticipantsService;

@RestController
@RequestMapping("/sportyfi")
public class ParticipantsController {
	
	@Autowired
	ParticipantsService participantsService;
	
	@GetMapping("/participants/{matchId}")
    public List<Participants> fetchAllParticipants(@PathVariable UUID matchId) {
		System.out.println("fetchParticipants: "+ matchId);
        return participantsService.fetchAllParticipants(matchId);
    }
	
	@PostMapping("/addParticipant")
	public boolean addParticipant(@RequestBody Participants participant) {
		System.out.println("addParticipant: "+ participant);
		return participantsService.addParticipant(participant);
	}
	
	@DeleteMapping("/deleteParticipant/{id}")
	public boolean deleteParticipant(@PathVariable("id") UUID participantId) {
		System.out.println("deleteParticipant: "+ participantId);
		return participantsService.deleteParticipant(participantId);
	}

}
