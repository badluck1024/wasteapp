package it.unisalento.pas.smarcity.service;

import it.unisalento.pas.smarcity.domain.Bill;
import it.unisalento.pas.smarcity.dto.BillDTO;
import it.unisalento.pas.smarcity.repositories.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private MessageSender messageSender;

    private BillDTO convertToDTO(Bill bill) {

        BillDTO billDTO = new BillDTO();
        billDTO.setId(bill.getId());
        billDTO.setUsername(bill.getUsername());
        billDTO.setAmount(bill.getAmount());
        billDTO.setIssueDate(bill.getIssueDate());
        billDTO.setPaid(bill.getIsPaid());

        return billDTO;
    }

    private Bill convertToEntity(BillDTO billDTO) {

        Bill bill = new Bill();
        bill.setId(billDTO.getId());
        bill.setUsername(billDTO.getUsername());
        bill.setAmount(billDTO.getAmount());
        bill.setIssueDate(billDTO.getIssueDate());
        bill.setPaid(billDTO.getIsPaid());

        return bill;
    }

    public Bill saveBill(BillDTO billDTO) {

        Bill newBill = convertToEntity(billDTO);
        newBill = billRepository.save(newBill);

        return newBill;
    }

    public List<BillDTO> getAllBills() {

        return billRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public BillDTO payBill(String billId) {
        return billRepository.findById(billId).map(bill -> {
            bill.setPaid(true);
            Bill updatedBill = billRepository.save(bill);
            BillDTO billDTO = convertToDTO(updatedBill);

            messageSender.updateOfficeBill(billDTO);

            return billDTO;
        }).orElseThrow(() -> new RuntimeException("Bill not found with ID: " + billId));
    }

    public List<Bill> getPaidBills() {
        return billRepository.findByIsPaidTrue();
    }

    public List<Bill> getNotPaidBills() {
        return billRepository.findByIsPaidFalse();
    }

    public void deleteAllBill() {

        billRepository.deleteAll();
    }
}
