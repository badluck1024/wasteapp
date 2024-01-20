package it.unisalento.pas.smarcity.restcontrollers;

import it.unisalento.pas.smarcity.domain.WasteRecord;
import it.unisalento.pas.smarcity.dto.WasteRecordDTO;
import it.unisalento.pas.smarcity.service.WasteRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/waste-record")
public class WasteRecordRestController {

    @Autowired
    private WasteRecordService wasteRecordService;

    @GetMapping("/")
    public ResponseEntity<List<WasteRecordDTO>> getAllRecords() {
        List<WasteRecordDTO> records = wasteRecordService.getAllRecords();
        return ResponseEntity.ok(records);
    }

    @PostMapping("/add")
    public ResponseEntity<WasteRecord> addWasteRecord(@RequestBody WasteRecordDTO wasteRecordDTO) {
        WasteRecord wasteRecord = wasteRecordService.addWasteRecord(wasteRecordDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(wasteRecord);
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<WasteRecordDTO>> getWasteRecordsByUsername(@PathVariable String username) {
        List<WasteRecordDTO> records = wasteRecordService.getWasteRecordsByUsername(username);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/performance/{username}")
    public ResponseEntity<Double> getUserPerformance(@PathVariable String username) {
        double performance = wasteRecordService.calculatePerformance(username);
        return ResponseEntity.ok(performance);
    }

    @DeleteMapping("/delete/")
    public ResponseEntity<Void> deleteAllWasteRecords() {
        wasteRecordService.deleteAllWasteRecords();
        return ResponseEntity.ok().build();
    }
}
