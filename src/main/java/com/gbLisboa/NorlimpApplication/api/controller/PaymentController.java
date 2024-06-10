package com.gbLisboa.NorlimpApplication.api.controller;

import com.gbLisboa.NorlimpApplication.api.model.PaymentModel;
import com.gbLisboa.NorlimpApplication.domain.model.Payment;
import com.gbLisboa.NorlimpApplication.domain.repository.PaymentRepository;
import com.gbLisboa.NorlimpApplication.domain.service.PaymentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/payment")
public class PaymentController {

    private PaymentService paymentService;
    private PaymentRepository paymentRepository;
    private ModelMapper modelMapper;

    @GetMapping("/listPayments")
    public List<PaymentModel> findAll(){
        return paymentService.findAllPayments();
    }
    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentModel> find(@PathVariable Long paymentId){
        if (!paymentRepository.existsById(paymentId)){
            return ResponseEntity.notFound().build();
        }
        PaymentModel paymentFound = paymentService.findPayment(paymentId);
        return ResponseEntity.ok(paymentFound);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public ResponseEntity<PaymentModel> register(@Valid @RequestBody Payment payment){
        PaymentModel paymentSaved = paymentService.savePayment(payment);
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentSaved);
    }
    @DeleteMapping("/delete/{paymentId}")
    public ResponseEntity<Void> delete(@PathVariable Long paymentId){
        if (!paymentRepository.existsById(paymentId)){
            return ResponseEntity.notFound().build();
        }
        paymentService.deletePayment(paymentId);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/update/{paymentId}")
    public ResponseEntity<PaymentModel> update (@PathVariable Long paymentId,
                                                      @Valid @RequestBody Payment payment){
        if(!paymentRepository.existsById(paymentId)){
            return ResponseEntity.notFound().build();
        }
        payment.setId(paymentId);
        PaymentModel paymentUpdate = paymentService.savePayment(payment);
        return ResponseEntity.ok(paymentUpdate);
    }


}
