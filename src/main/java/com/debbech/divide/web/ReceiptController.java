package com.debbech.divide.web;

import com.debbech.divide.entity.division.Division;
import com.debbech.divide.entity.receipt.Receipt;
import com.debbech.divide.services.interfaces.IReceiptService;
import com.debbech.divide.web.models.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/receipt")
@Slf4j
public class ReceiptController {

    @Autowired
    private IReceiptService receiptService;

    @PostMapping("/scan")
    public ResponseEntity<Object> scan(@RequestBody ScanReq req){
        System.err.println(req.getUploadedPic());
        try {
            String processingAuthCode = receiptService.startProcessing(req.getUploadedPic());
            ScanResp sr = new ScanResp(processingAuthCode);
            return ResponseEntity.ok().body(sr);
        } catch (Exception e) {
            GeneralMessage lr = new GeneralMessage( e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(lr);
        }
    }
    @GetMapping("/check_progress/{id}")
    public ResponseEntity<Object> checkProgess(@PathVariable("id") String id){
        try {
            boolean result = receiptService.checkProcessing(id);
            CheckProgressResp sr = new CheckProgressResp(result, "ready");
            return ResponseEntity.ok().body(sr);
        } catch (Exception e) {
            GeneralMessage lr = new GeneralMessage( e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(lr);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getReceipt(@PathVariable("id") String id){
        try {
            Receipt result = receiptService.getOne(id);
            ReceiptDto rd = new ReceiptDto();
            rd.setId(result.getId());
            rd.setReceiptData(result.getReceiptData());
            rd.setInitiator(result.getInitiator().getUid());
            rd.setIsProcessing(result.getIsProcessing());
            rd.setFailureReason(result.getFailureReason());
            rd.setOurReference(result.getOurReference());
            rd.setCreatedAt(result.getCreatedAt());
            return ResponseEntity.ok().body(rd);
        } catch (Exception e) {
            GeneralMessage lr = new GeneralMessage( e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(lr);
        }
    }

    @PostMapping("/{receipt_id}/division")
    public ResponseEntity<Object> divide(@PathVariable("receipt_id") Long id, @RequestBody Division division){
        try {
            receiptService.divide(id, division);
            GeneralMessage lr = new GeneralMessage("", true);
            return ResponseEntity.ok().body(lr);
        } catch (Exception e) {
            GeneralMessage lr = new GeneralMessage( e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(lr);
        }
    }
}
