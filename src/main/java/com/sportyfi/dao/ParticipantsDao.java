package com.sportyfi.dao;

import java.util.List;
import java.util.UUID;

import com.sportyfi.entity.Participants;

public interface ParticipantsDao {

	List<Participants> fetchAllParticipants(UUID matchId);

	boolean addParticipant(Participants participant);

	boolean deleteParticipant(UUID participantId);

}
