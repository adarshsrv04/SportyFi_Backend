package com.sportyfi.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.UUID;

@Entity
@Table(name = "venues", schema = "public")
public class Venues {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(nullable = false)
    private String location;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @Column(name = "price_per_hour", nullable = false)
    private Double price_per_hour;

    @Column(name = "contact_phone")
    private String contact_phone;

    @Column(name = "contact_email")
    private String contact_email;

    @Column(name = "is_verified", nullable = false)
    private Boolean is_verified = false;

    @Column(name = "owner_id")
    private UUID owner_id;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    private Timestamp created_at;

    public Double getPrice_per_hour() {
		return price_per_hour;
	}

	public void setPrice_per_hour(Double price_per_hour) {
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

	public Boolean getIs_verified() {
		return is_verified;
	}

	public void setIs_verified(Boolean is_verified) {
		this.is_verified = is_verified;
	}

	public UUID getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(UUID owner_id) {
		this.owner_id = owner_id;
	}

	public Timestamp getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}

	public Timestamp getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Timestamp updated_at) {
		this.updated_at = updated_at;
	}

	@Column(name = "updated_at", nullable = false, insertable = false)
    private Timestamp updated_at;

    @Column(columnDefinition = "text[]", nullable = false)
    private String[] sports;

    @Column(columnDefinition = "text[]", nullable = false)
    private String[] amenities;

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

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
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

	@Override
	public String toString() {
		return "Venues [id=" + id + ", name=" + name + ", description=" + description + ", location=" + location
				+ ", latitude=" + latitude + ", longitude=" + longitude + ", price_per_hour=" + price_per_hour
				+ ", contact_phone=" + contact_phone + ", contact_email=" + contact_email + ", is_verified="
				+ is_verified + ", owner_id=" + owner_id + ", created_at=" + created_at + ", updated_at=" + updated_at
				+ ", sports=" + Arrays.toString(sports) + ", amenities=" + Arrays.toString(amenities) + "]";
	}
    
}

