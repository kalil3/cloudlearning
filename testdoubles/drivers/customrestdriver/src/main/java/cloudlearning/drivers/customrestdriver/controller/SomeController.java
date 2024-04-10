package cloudlearning.drivers.customrestdriver.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
public class SomeController{
    @PostMapping("/payload")
    public ResponseEntity<String> getPayload(@RequestBody JsonNode payload) {
        try {
            if (payload.has("delay")) {
                int delay = payload.get("delay").asInt();
                if (delay > 0) {
                    TimeUnit.SECONDS.sleep(delay);
                }
            }
            if (payload.has("statusCode")) {
                int statusCode = payload.get("statusCode").asInt();
                HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
                return ResponseEntity.status(httpStatus).body(payload.toString());
            }
            return ResponseEntity.status(HttpStatus.OK).body(payload.toString());
        } catch (InterruptedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}