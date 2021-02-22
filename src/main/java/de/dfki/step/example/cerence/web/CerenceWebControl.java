package de.dfki.step.example.cerence.web;

import de.dfki.step.example.cerence.InCarDialogue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Map;

@RestController
public class CerenceWebControl {

    @PostConstruct
    protected void init() {
        // nothing to do here in this case
    }

    @GetMapping(value = "/nlu/voice/status")
    public ResponseEntity getVoiceStatus() {
        try {
            System.out.println("Get Voice status for Cerenece");
            if(InCarDialogue.cerence.IsListening())
                return ResponseEntity.status(HttpStatus.OK).body("recording: true");
            else
                return ResponseEntity.status(HttpStatus.OK).body("recording: false");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping(value = "/nlu/voice/status")
    public ResponseEntity setVoiceStatus(@RequestParam Map<String, Object> body) {
        try {
            if(body.containsKey(("mic")))
            {
                switch((String)body.get("mic"))
                {
                    case "open":
                        System.out.println("Mix: Open Mic");
                        InCarDialogue.cerence.StartListening();
                        return ResponseEntity.status(HttpStatus.OK).body("ok");

                    case "close":
                        System.out.println("Mix: Close Mic");
                        InCarDialogue.cerence.StopListening();
                        return ResponseEntity.status(HttpStatus.OK).body("ok");

                    default:
                        System.out.println("Invalid Post request");
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("invalid request");
                }
            }
            else
            {
                System.out.println("Invalid Post request");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("invalid request");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping(value = "/nlu/text")
    public ResponseEntity analyzeText(@RequestParam Map<String, Object> body) {
        try {
            if(body.containsKey(("text")))
            {
                String text = (String)body.get("text");
                InCarDialogue.cerence.AnalyzeText(text);
                return ResponseEntity.status(HttpStatus.OK).body("ok");
            }
            else
            {
                System.out.println("Invalid Post request");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("invalid request");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}
