package com.sportyfi.entity;

import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "participants")
public class Participants {
	
	@Id
    @GeneratedValue
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name = "match_id", nullable = false, columnDefinition = "uuid")
    private UUID match_id;

    @Column(name = "user_id", nullable = false, columnDefinition = "uuid")
    private UUID user_id;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDate created_at;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getMatch_id() {
		return match_id;
	}

	public void setMatch_id(UUID match_id) {
		this.match_id = match_id;
	}

	public UUID getUser_id() {
		return user_id;
	}

	public void setUser_id(UUID user_id) {
		this.user_id = user_id;
	}

	public LocalDate getCreated_at() {
		return created_at;
	}

	public void setCreated_at(LocalDate created_at) {
		this.created_at = created_at;
	}

    
}
