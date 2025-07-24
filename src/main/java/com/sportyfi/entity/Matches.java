package com.sportyfi.entity;

import jakarta.persistence.*;
//import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "matches")
public class Matches {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "sport", nullable = false)
    private String sport;

    @Column(name = "location", nullable = false)
    private String location;
    
    @Column(name = "city", nullable = true)
    private String city;

    @Column(name = "match_time", nullable = false)
    private ZonedDateTime match_time;

    @Column(name = "team_size", nullable = false)
    private int team_size;
    
    @Column(name = "host_join", nullable = false)
    private String host_join;

    @Column(name = "available_slots", nullable = false)
    private int available_slots;

    @Column(name = "skill_level", nullable = false)
    private String skill_level = "all";

    @Column(name = "description")
    private String description;

    @Column(name = "host_id", nullable = false)
    private UUID host_id;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private ZonedDateTime created_at;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getSport() {
		return sport;
	}

	public void setSport(String sport) {
		this.sport = sport;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public ZonedDateTime getMatch_time() {
		return match_time;
	}

	public void setMatch_time(ZonedDateTime match_time) {
		this.match_time = match_time;
	}

	public int getTeam_size() {
		return team_size;
	}

	public void setTeam_size(int team_size) {
		this.team_size = team_size;
	}

	public int getAvailable_slots() {
		return available_slots;
	}

	public void setAvailable_slots(int available_slots) {
		this.available_slots = available_slots;
	}

	public String getSkill_level() {
		return skill_level;
	}

	public void setSkill_level(String skill_level) {
		this.skill_level = skill_level;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public UUID getHost_id() {
		return host_id;
	}

	public void setHost_id(UUID host_id) {
		this.host_id = host_id;
	}
	
	public void setHost_join(String host_join) {
		this.host_join = host_join;
	}
	
	public String getHost_join() {
		return host_join;
	}
	
	public ZonedDateTime getCreated_at() {
		return created_at;
	}

	public void setCreated_at(ZonedDateTime created_at) {
		this.created_at = created_at;
	}

	@Override
	public String toString() {
		return "Matches [id=" + id + ", sport=" + sport + ", location=" + location + ", city=" + city + ", match_time=" + match_time
				+ ", team_size=" + team_size + ", available_slots=" + available_slots + ", skill_level=" + skill_level
				+ ", description=" + description + ", host_id=" + host_id + ", created_at=" + created_at + "]";
	}
}

