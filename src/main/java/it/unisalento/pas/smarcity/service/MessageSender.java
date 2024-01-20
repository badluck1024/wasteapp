package it.unisalento.pas.smarcity.service;

import it.unisalento.pas.smarcity.dto.BillDTO;
import it.unisalento.pas.smarcity.dto.WasteRecordDTO;
import it.unisalento.pas.smarcity.messages.ThrownWasteMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void thrownWasteTrigger(String userId, String binId, String wasteType, String waste) {

        ThrownWasteMessage message = new ThrownWasteMessage();
        message.setUserId(userId);
        message.setBinId(binId);
        message.setWasteType(wasteType);
        message.setWaste(waste);

        rabbitTemplate.convertAndSend("throwWasteTriggerExchange", "throwWasteTriggerKey", message);
    }

    public void updateOfficeBill(BillDTO billDTO) {

        rabbitTemplate.convertAndSend("updateOfficeBillExchange", "updateOfficeBillKey", billDTO);
    }

    public void sendRecords(WasteRecordDTO wasteRecordDTO) {

        rabbitTemplate.convertAndSend("updateOfficeRecordsExchange", "updateOfficeRecordsKey", wasteRecordDTO);
    }
}
