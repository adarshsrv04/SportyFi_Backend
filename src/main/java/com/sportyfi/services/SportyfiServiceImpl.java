package com.sportyfi.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sportyfi.dao.SportyfiDao;
import com.sportyfi.entity.Bookings;
import com.sportyfi.entity.Matches;

@Service
public class SportyfiServiceImpl implements SportyfiService {
	
	@Autowired
	SportyfiDao sportyfiDao;

	@Override
	public List<Matches> findAllMatches(String sport) {
		return sportyfiDao.findAllMatches(sport);
	}
	
	@Override
//	@Transactional
	public List<Matches> findMatchesByCity(String city) {
		return sportyfiDao.findMatchesByCity(city);
	}

	@Override
	public Matches findMatchById(UUID matchId) {
		return sportyfiDao.findMatchById(matchId);
	}
	
	@Override
	public List<Matches> findMatchesByUserId(UUID userId) {
		return sportyfiDao.findMatchesByUserId(userId);
	}

	@Override
	public boolean saveMatch(Matches matches) {
		return sportyfiDao.saveMatch(matches);
	}

	@Override
	public boolean updateMatchField(UUID matchId, String field, String newValue) {
		return sportyfiDao.updateMatchField(matchId, field, newValue);
	}

}
