package com.sportyfi.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.UUID;

@Entity
@Table(name = "profiles", schema = "public")
public class Profiles {

	@Id
	@Column(name = "id", nullable = false)
	private UUID id;

	@Column(name = "username")
	private String username;

	@Column(name = "avatar_url")
	private String avatar_url;

	@Column(name = "created_at")
	private LocalDateTime created_at;

	@Column(name = "updated_at")
	private LocalDateTime updated_at;

	@Column(name = "role")
	private String role;

	@Column(name = "bio")
	private String bio;

	@Column(name = "preferred_sports")
	private String[] preferred_sports;

	@Column(name = "location")
	private String location;

	@Column(name = "primary_sport")
	private String primary_sport;
	
	@Column(name = "contact_phone")
	private String contact_phone;

	// Getters and Setters

	public UUID getId() { return id; }
	public void setId(UUID id) { this.id = id; }

	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }

	public String getRole() { return role; }
	public void setRole(String role) { this.role = role; }

	public String getBio() { return bio; }
	public void setBio(String bio) { this.bio = bio; }

	public String getLocation() { return location; }
	public void setLocation(String location) { this.location = location; }
	public String getAvatar_url() {
		return avatar_url;
	}
	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
	}
	public LocalDateTime getCreated_at() {
		return created_at;
	}
	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}
	public LocalDateTime getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(LocalDateTime updated_at) {
		this.updated_at = updated_at;
	}
	public String[] getPreferred_sports() {
		return preferred_sports;
	}
	public void setPreferred_sports(String[] preferred_sports) {
		this.preferred_sports = preferred_sports;
	}
	public String getPrimary_sport() {
		return primary_sport;
	}
	public void setPrimary_sport(String primary_sport) {
		this.primary_sport = primary_sport;
	}
	public String getContact_phone() {
		return contact_phone;
	}
	public void setContact_phone(String contact_phone) {
		this.contact_phone = contact_phone;
	}
	
	@Override
	public String toString() {
		return "Profiles [id=" + id + ", username=" + username + ", avatar_url=" + avatar_url + ", created_at="
				+ created_at + ", updated_at=" + updated_at + ", role=" + role + ", bio=" + bio + ", preferred_sports="
				+ Arrays.toString(preferred_sports) + ", location=" + location + ", primary_sport=" + primary_sport
				+ ", contact_phone=" + contact_phone + "]";
	}
}

