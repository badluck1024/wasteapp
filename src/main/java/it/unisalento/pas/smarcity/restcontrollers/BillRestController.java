package it.unisalento.pas.smarcity.restcontrollers;

import it.unisalento.pas.smarcity.domain.Bill;
import it.unisalento.pas.smarcity.dto.BillDTO;
import it.unisalento.pas.smarcity.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/bill")
public class BillRestController {

    @Autowired
    private BillService billService;

    @GetMapping("/")
    public ResponseEntity<List<BillDTO>> getAllBills() {
        List<BillDTO> bills = billService.getAllBills();
        return ResponseEntity.ok(bills);
    }

    @PutMapping("/pay/{billId}")
    public ResponseEntity<BillDTO> payBill(@PathVariable String billId) {
        BillDTO paidBill = billService.payBill(billId);
        return ResponseEntity.ok(paidBill);
    }

    @GetMapping("/paid")
    public ResponseEntity<List<Bill>> getPaidBills() {
        List<Bill> paidBills = billService.getPaidBills();
        return ResponseEntity.ok(paidBills);
    }

    @GetMapping("/not-paid")
    public ResponseEntity<List<Bill>> getNotPaidBills() {
        List<Bill> notPaidBills = billService.getNotPaidBills();
        return ResponseEntity.ok(notPaidBills);
    }

    @DeleteMapping("/delete/")
    public ResponseEntity<Void> deleteAllBills(){
        billService.deleteAllBill();
        return ResponseEntity.ok().build();
    }
}
