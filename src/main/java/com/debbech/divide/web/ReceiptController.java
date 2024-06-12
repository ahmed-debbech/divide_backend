package com.debbech.divide.web;

import com.debbech.divide.services.interfaces.IReceiptService;
import com.debbech.divide.web.models.GeneralMessage;
import com.debbech.divide.web.models.LoginReq;
import com.debbech.divide.web.models.ScanReq;
import com.debbech.divide.web.models.ScanResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/receipt")
@Slf4j
public class ReceiptController {

    @Autowired
    private IReceiptService receiptService;

    @PostMapping("/scan")
    public ResponseEntity<Object> scan(@RequestBody ScanReq req){
        try {
            String processingAuthCode = receiptService.startProcessing(req.getUploadedPic());
            ScanResp sr = new ScanResp(processingAuthCode);
            return ResponseEntity.ok().body(sr);
        } catch (Exception e) {
            GeneralMessage lr = new GeneralMessage( e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(lr);
        }
    }
}
