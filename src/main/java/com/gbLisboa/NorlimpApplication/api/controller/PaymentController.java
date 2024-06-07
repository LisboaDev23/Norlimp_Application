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

@AllArgsConstructor
@RestController
@RequestMapping("/payment")
public class PaymentController {

    private PaymentService paymentService;
    private PaymentRepository paymentRepository;
    private ModelMapper modelMapper;

    @GetMapping
    public List<Payment> findAllPayments(){
        return paymentRepository.findAll();
    }
    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentModel> findPayment(@PathVariable Long paymentId){
        return paymentRepository.findById(paymentId)
                .map(payment -> modelMapper.map(payment, PaymentModel.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Payment registerPayment(@Valid @RequestBody Payment payment){
        return paymentService.savePayment(payment);
    }
    @DeleteMapping("/{paymentId}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long paymentId){
        if (!paymentRepository.existsById(paymentId)){
            return ResponseEntity.notFound().build();
        }
        paymentRepository.deleteById(paymentId);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{paymentId}")
    public ResponseEntity<PaymentModel> updatePayment (@PathVariable Long paymentId,
                                                      @Valid @RequestBody Payment payment){
        if(!paymentRepository.existsById(paymentId)){
            return ResponseEntity.notFound().build();
        }
        payment.setId(paymentId);
        paymentService.savePayment(payment);
        return paymentRepository.findById(payment.getId())
                .map(p -> modelMapper.map(p, PaymentModel.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
