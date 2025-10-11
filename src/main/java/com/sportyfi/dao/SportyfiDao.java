package com.sportyfi.dao;

import java.util.List;
import java.util.UUID;

import com.sportyfi.entity.Matches;

public interface SportyfiDao {

	public List<Matches> findAllMatches(String sport, String city, String date);

	public Matches findMatchById(UUID id);

	public boolean saveMatch(Matches matches);

	public boolean updateMatchField(UUID matchId, String field, String newValue);

	public List<Matches> findMatchesByUserId(UUID userId);

	public List<Matches> findMatchesByCity(String city);

}
