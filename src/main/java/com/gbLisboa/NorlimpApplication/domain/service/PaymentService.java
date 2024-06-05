package com.gbLisboa.NorlimpApplication.domain.service;

import com.gbLisboa.NorlimpApplication.domain.exception.PaymentException;
import com.gbLisboa.NorlimpApplication.domain.model.Payment;
import com.gbLisboa.NorlimpApplication.domain.model.Schedule;
import com.gbLisboa.NorlimpApplication.domain.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
            throw new PaymentException("Pagamento não cadastrado pois já se encontra no banco de dados.");
        }
        return paymentRepository.save(payment);
    }

    @Transactional
    public void deletePaymentById (Long paymentId){
        boolean paymentExist = paymentRepository.existsById(paymentId);
        if(!paymentExist){
            throw new PaymentException("Pagamento não encontrado, dessa forma não foi possível excluí-lo do banco de dados.");
        }
        paymentRepository.deleteById(paymentId);
    }

    public List<Schedule> findSchedulesByPayment (Payment payment){
        return findPayment(payment.getId()).getSchedulesList();
    }
}
