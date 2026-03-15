package com.inetum.devsecops;

// These are the missing "maps" for the compiler
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/api/conflict")
public class InfoController {
    
    @GetMapping("/summary")
    public Map<String, String> getConflictData() {
        return Map.of(
            "title", "2026 Iran War",
            "date", "28 February 2026 – Present",
            "status", "Ongoing",
            "key_event", "Joint US-Israeli Operation Epic Fury"
        );
    }
}