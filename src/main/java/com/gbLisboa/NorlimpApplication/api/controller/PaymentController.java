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

    @GetMapping("/list")
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
    public PaymentModel register(@Valid @RequestBody PaymentModel paymentModel){
        return paymentService.savePayment(paymentModel);
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
                                                      @Valid @RequestBody PaymentModel paymentModel){
        if(!paymentRepository.existsById(paymentId)){
            return ResponseEntity.notFound().build();
        }
        PaymentModel paymentUpdate = paymentService.updatePayment(paymentId,paymentModel);
        return ResponseEntity.ok(paymentUpdate);
    }

}
