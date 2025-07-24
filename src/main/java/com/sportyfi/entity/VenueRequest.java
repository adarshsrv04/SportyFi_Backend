package com.sportyfi.entity;

import jakarta.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "venue_requests")
public class VenueRequest {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String description;
    private String location;

    @Column(name = "price_per_hour", nullable = false)
    private double price_per_hour;

    @Column(name = "contact_phone", nullable = false)
    private String contact_phone;

    @Column(name = "contact_email", nullable = false)
    private String contact_email;

//    @ElementCollection
//    @CollectionTable(name = "venue_requests", joinColumns = @JoinColumn(name = "venue_request_id"))
    @Column(name = "sports", nullable = false)
    private String[] sports;

//    @ElementCollection
//    @CollectionTable(name = "venue_requests", joinColumns = @JoinColumn(name = "venue_request_id"))
    @Column(name = "amenities", nullable = false)
    private String[] amenities;


    @Column(name = "owner_id", nullable = false)
    private UUID owner_id;

    @Column(nullable = false)
    private String status = "pending";

    @Column(name = "created_at")
    private ZonedDateTime created_at = ZonedDateTime.now();

    @Column(name = "updated_at")
    private ZonedDateTime updated_at = ZonedDateTime.now();

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public double getPrice_per_hour() {
		return price_per_hour;
	}

	public void setPrice_per_hour(double price_per_hour) {
		this.price_per_hour = price_per_hour;
	}

	public String getContact_phone() {
		return contact_phone;
	}

	public void setContact_phone(String contact_phone) {
		this.contact_phone = contact_phone;
	}

	public String getContact_email() {
		return contact_email;
	}

	public void setContact_email(String contact_email) {
		this.contact_email = contact_email;
	}

	public String[] getSports() {
		return sports;
	}

	public void setSports(String[] sports) {
		this.sports = sports;
	}

	public String[] getAmenities() {
		return amenities;
	}

	public void setAmenities(String[] amenities) {
		this.amenities = amenities;
	}

	public UUID getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(UUID owner_id) {
		this.owner_id = owner_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ZonedDateTime getCreated_at() {
		return created_at;
	}

	public void setCreated_at(ZonedDateTime created_at) {
		this.created_at = created_at;
	}

	public ZonedDateTime getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(ZonedDateTime updated_at) {
		this.updated_at = updated_at;
	}

	@Override
	public String toString() {
		return "VenueRequest [id=" + id + ", name=" + name + ", description=" + description + ", location=" + location
				+ ", price_per_hour=" + price_per_hour + ", contact_phone=" + contact_phone + ", contact_email="
				+ contact_email + ", sports=" + sports + ", amenities=" + amenities + ", owner_id=" + owner_id
				+ ", status=" + status + ", created_at=" + created_at + ", updated_at=" + updated_at + "]";
	}
}

