package it.unisalento.pas.smarcity.service;

import it.unisalento.pas.smarcity.dto.BillDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageReceiver {

    @Autowired
    private BillService billService;

    @RabbitListener(queues = "requestPaymentsQueue")
    public void receivePaymentInfo(BillDTO billDTO) {

        billService.saveBill(billDTO);
        System.out.println("Received and saved bill:" + billDTO.getId());
    }
}
