package com.sportyfi.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sportyfi.entity.Bookings;
import com.sportyfi.entity.Matches;
import com.sportyfi.entity.Profiles;
import com.sportyfi.services.ProfilesService;
import com.sportyfi.services.SportyfiService;

@RestController
@RequestMapping("/sportyfi")
public class SportyfiController {
	
	@Autowired
	private SportyfiService sportyfiService;
	
	@GetMapping("/bookings")
    public List<Bookings> getAllBookings() {
        return sportyfiService.findAllBookings();
    }

    @GetMapping("/booking/{id}")
    public Optional<Bookings> getBookingById(@PathVariable UUID id) {
        return sportyfiService.findBookingById(id);
    }

    @PostMapping("/createBooking")
    public Bookings createBooking(@RequestBody Bookings booking) {
        return sportyfiService.saveBooking(booking);
    }
    
    @GetMapping("/matches")
    public ResponseEntity<List<Matches>> getAllMatches(@RequestParam(required = false) String sport) {
    	if(sport != null) {
    		System.out.println("reached---" + sport);
    	} else {
    		System.out.println("no sport---" + sport);
    	}
        return ResponseEntity.ok(sportyfiService.findAllMatches(sport));
    }
    
    @GetMapping("/matches/city")
    public ResponseEntity<List<Matches>> getMatchesByCity(@RequestParam(required = false) String city) {
    	if(city != null) {
    		System.out.println("reached---" + city);
    	} else {
    		System.out.println("no city---" + city);
    	}
        return ResponseEntity.ok(sportyfiService.findMatchesByCity(city));
    }

    // find match using match id
    @GetMapping("/match/{matchId}")
    public Matches getMatchById(@PathVariable UUID matchId) {
    	System.out.println("fetchmatch---" + matchId);
        return sportyfiService.findMatchById(matchId);
    }
    
    // find match of user joined
    @GetMapping("/matches/{userId}")
    public ResponseEntity<List<Matches>> getMatchesByUserId(@PathVariable UUID userId) {
        List<Matches> matches = sportyfiService.findMatchesByUserId(userId);
        System.out.println("user matches: " + matches.toString());
        return ResponseEntity.ok(matches);
    }

    @PostMapping("/createMatch")
    public boolean createMatch(@RequestBody Matches matches) {
    	System.out.println("create match: "+ matches);
        return sportyfiService.saveMatch(matches);
    }
    
    @PutMapping("/match/update/{matchId}/{field}")
    public boolean updateMatchField(
            @PathVariable UUID matchId,
            @PathVariable String field,
            @RequestBody String newValue) {
    	System.out.println(matchId+ field+ newValue);
//    	return ResponseEntity.ok("Match updated successfully.");
        return sportyfiService.updateMatchField(matchId, field, newValue);
//        if (updated) {
//            return ResponseEntity.ok("Match updated successfully.");
//        } else {
//            return ResponseEntity.badRequest().body("Invalid field or update failed.");
//        }
    }
    
    @Autowired
    private ProfilesService profilesService;

    @GetMapping("/profiles/{userId}")
    public ResponseEntity<Profiles> getProfile(@PathVariable UUID userId) {
        Profiles profile = profilesService.getProfileByUserId(userId);
        if (profile != null) {
            return ResponseEntity.ok(profile);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/profiles/update/{userId}")
    public ResponseEntity<String> updateProfile(@PathVariable UUID userId, @RequestBody Profiles profile) {
    	System.out.println(profile);
        boolean isUpdated = profilesService.updateProfile(userId, profile);
        return ResponseEntity.ok("Profile updated successfully");
    }
}
