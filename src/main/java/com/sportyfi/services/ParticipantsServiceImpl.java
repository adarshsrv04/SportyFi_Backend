package com.sportyfi.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sportyfi.dao.ParticipantsDao;
import com.sportyfi.entity.Participants;

@Service
public class ParticipantsServiceImpl implements ParticipantsService {
	
	@Autowired
	ParticipantsDao participantsDao;

	@Override
	public List<Participants> fetchAllParticipants(UUID matchId) {
		return participantsDao.fetchAllParticipants(matchId);
	}

	@Override
	public boolean addParticipant(Participants participant) {
		return participantsDao.addParticipant(participant);
	}

	@Override
	public boolean deleteParticipant(UUID participantId) {
		return participantsDao.deleteParticipant(participantId);
	}

}
