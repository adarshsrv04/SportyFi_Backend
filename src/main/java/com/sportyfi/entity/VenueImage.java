package com.sportyfi.entity;

import jakarta.persistence.*;

@Entity
public class VenueImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id")
    private VenueRequest venueRequest;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public VenueRequest getVenueRequest() {
		return venueRequest;
	}

	public void setVenueRequest(VenueRequest venueRequest) {
		this.venueRequest = venueRequest;
	}

    
}

