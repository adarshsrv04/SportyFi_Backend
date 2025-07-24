package com.sportyfi.services;

import java.util.List;
import java.util.UUID;

import com.sportyfi.entity.Participants;

public interface ParticipantsService {

	List<Participants> fetchAllParticipants(UUID id);

	boolean addParticipant(Participants participant);

	boolean deleteParticipant(UUID participantId);

}
