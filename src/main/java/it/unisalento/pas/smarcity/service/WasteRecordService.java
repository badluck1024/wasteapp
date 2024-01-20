package it.unisalento.pas.smarcity.service;

import it.unisalento.pas.smarcity.domain.WasteRecord;
import it.unisalento.pas.smarcity.dto.WasteRecordDTO;
import it.unisalento.pas.smarcity.repositories.WasteRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WasteRecordService {

    @Autowired
    private WasteRecordRepository wasteRecordRepository;

    @Autowired
    private MessageSender messageSender;

    private WasteRecordDTO convertToDTO(WasteRecord wasteRecord) {

        WasteRecordDTO wasteRecordDTO = new WasteRecordDTO();
        wasteRecordDTO.setId(wasteRecord.getId());
        wasteRecordDTO.setUserUsername(wasteRecord.getUserUsername());
        wasteRecordDTO.setWaste(wasteRecord.getWaste());
        wasteRecordDTO.setWasteTypeDeclared(wasteRecord.getWasteTypeDeclared());
        wasteRecordDTO.setWasteTypeEffective(wasteRecord.getWasteTypeEffective());
        wasteRecordDTO.setTimeStamp(wasteRecord.getTimeStamp());
        wasteRecordDTO.setBinLocation(wasteRecord.getBinLocation());

        return wasteRecordDTO;
    }

    private WasteRecord convertToEntity(WasteRecordDTO wasteRecordDTO) {

        WasteRecord wasteRecord = new WasteRecord();
        wasteRecord.setId(wasteRecordDTO.getId());
        wasteRecord.setUserUsername(wasteRecordDTO.getUserUsername());
        wasteRecord.setWaste(wasteRecordDTO.getWaste());
        wasteRecord.setWasteTypeDeclared(wasteRecordDTO.getWasteTypeDeclared());
        wasteRecord.setWasteTypeEffective(wasteRecordDTO.getWasteTypeEffective());
        wasteRecord.setTimeStamp(wasteRecordDTO.getTimeStamp());
        wasteRecord.setBinLocation(wasteRecordDTO.getBinLocation());

        return wasteRecord;
    }

    public WasteRecord addWasteRecord(WasteRecordDTO wasteRecordDTO) {

        WasteRecord newWasteRecord = convertToEntity(wasteRecordDTO);
        newWasteRecord = wasteRecordRepository.save(newWasteRecord);
        messageSender.sendRecords(wasteRecordDTO);

        return newWasteRecord;
    }

    public List<WasteRecordDTO> getAllRecords() {

        return wasteRecordRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<WasteRecordDTO> getWasteRecordsByUsername(String username) {

        List<WasteRecord> result = wasteRecordRepository.findByUserUsername(username);
        List<WasteRecordDTO> records = new ArrayList<>();

        for(WasteRecord wasteRecord: result) {
            WasteRecordDTO wasteRecordDTO = convertToDTO(wasteRecord);

            records.add(wasteRecordDTO);
        }

        return records;
    }

    public double calculatePerformance(String username) {
        List<WasteRecordDTO> records = getWasteRecordsByUsername(username);
        if (records.isEmpty()) {
            return 0.0;
        }

        long correctCount = records.stream()
                .filter(record -> record.getWasteTypeDeclared().equals(record.getWasteTypeEffective()))
                .count();

        return (double) correctCount / records.size() * 100.0;
    }

    public void deleteAllWasteRecords() {

        wasteRecordRepository.deleteAll();
    }
}

