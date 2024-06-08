package com.gbLisboa.NorlimpApplication.domain.service;

import com.gbLisboa.NorlimpApplication.domain.exception.PaymentException;
import com.gbLisboa.NorlimpApplication.domain.model.Payment;
import com.gbLisboa.NorlimpApplication.domain.model.Schedule;
import com.gbLisboa.NorlimpApplication.domain.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class PaymentService {

    private PaymentRepository paymentRepository;

    public Payment findPayment(Long paymentId){
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentException("Pagamento não encontrado no banco de dados."));
    }

    @Transactional
    public Payment savePayment (Payment payment){
        boolean paymentIsSave = paymentRepository.existsById(payment.getId());
        if (paymentIsSave){
            throw new PaymentException("Pagamento já se encontra no banco de dados.");
        }
        return paymentRepository.save(payment);
    }

    public List<Schedule> findSchedulesByPayment (Payment payment){
        return findPayment(payment.getId()).getSchedulesList();
    }
}
