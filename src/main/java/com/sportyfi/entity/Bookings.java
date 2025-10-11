package com.sportyfi.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "bookings", schema = "public")
public class Bookings {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name = "venue_id", nullable = false, columnDefinition = "uuid")
    private UUID venue_id;

    @Column(name = "user_id", nullable = false, columnDefinition = "uuid")
    private UUID user_id;

    @Column(name = "booking_date", nullable = false)
    private LocalDate booking_date;

    @Column(name = "start_time", nullable = false)
    private LocalTime start_time;

    @Column(name = "end_time", nullable = false)
    private LocalTime end_time;

    @Column(nullable = false)
    private String status = "pending";

    @Column(name = "total_price", nullable = false)
    private BigDecimal total_price;

    private String notes;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime created_at;

    @CreationTimestamp
    @Column(name = "updated_at", nullable = false)
    private ZonedDateTime updated_at;
    
    @Transient
    private Venues venue;

    public Venues getVenue() {
        return venue;
    }

    public void setVenue(Venues venue) {
        this.venue = venue;
    }

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getVenue_id() {
		return venue_id;
	}

	public void setVenue_id(UUID venue_id) {
		this.venue_id = venue_id;
	}

	public UUID getUser_id() {
		return user_id;
	}

	public void setUser_id(UUID user_id) {
		this.user_id = user_id;
	}

	public LocalDate getBooking_date() {
		return booking_date;
	}

	public void setBooking_date(LocalDate booking_date) {
		this.booking_date = booking_date;
	}

	public LocalTime getStart_time() {
		return start_time;
	}

	public void setStart_time(LocalTime start_time) {
		this.start_time = start_time;
	}

	public LocalTime getEnd_time() {
		return end_time;
	}

	public void setEnd_time(LocalTime end_time) {
		this.end_time = end_time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getTotal_price() {
		return total_price;
	}

	public void setTotal_price(BigDecimal total_price) {
		this.total_price = total_price;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
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

}

