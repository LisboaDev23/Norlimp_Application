package com.gbLisboa.NorlimpApplication.domain.service;

import com.gbLisboa.NorlimpApplication.api.model.PaymentModel;
import com.gbLisboa.NorlimpApplication.api.model.ScheduleModel;
import com.gbLisboa.NorlimpApplication.domain.exception.PaymentException;
import com.gbLisboa.NorlimpApplication.domain.model.Payment;
import com.gbLisboa.NorlimpApplication.domain.model.Schedule;
import com.gbLisboa.NorlimpApplication.domain.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PaymentService {

    private PaymentRepository paymentRepository;
    private ModelMapper modelMapper;

    public List<PaymentModel> findAllPayments(){
        return paymentRepository.findAll()
                .stream()
                .map(this::toPaymentModel)
                .collect(Collectors.toList());
    }

    public PaymentModel findPayment(Long paymentId){
        return paymentRepository.findById(paymentId)
                .map(this::toPaymentModel)
                .orElseThrow(() -> new PaymentException("Pagamento não encontrado!"));
    }

    @Transactional
    public PaymentModel savePayment (PaymentModel paymentModel){
        Payment payment = new Payment();
        payment.setDescription(paymentModel.getDescription());
        payment.setValue(paymentModel.getValue());
        paymentRepository.save(payment);

        return modelMapper.map(payment, PaymentModel.class);
    }
    @Transactional
    public void deletePayment (Long paymentId){
        try {
            paymentRepository.deleteById(paymentId);
        } catch (RuntimeException e) {
            if (!paymentRepository.existsById(paymentId)){
                throw new PaymentException("Pagamento não encontrado!");
            }
            throw new PaymentException("Pagamento não excluído!");
        }
    }

    @Transactional
    public PaymentModel updatePayment (Long paymentId, PaymentModel paymentModel){
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentException("Pagamento não encontrado!"));
        payment.setDescription(paymentModel.getDescription());
        payment.setValue(paymentModel.getValue());
        payment.setId(paymentId);
        Payment paymentUpdate = paymentRepository.save(payment);
        return toPaymentModel(paymentUpdate);
    }
    public List<ScheduleModel> findSchedulesByPayment (Long paymentId){
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentException("Pagamento não encontrado!"));
        return toPaymentModel(payment).getSchedulesList();
    }

    private PaymentModel toPaymentModel(Payment payment){
        return modelMapper.map(payment, PaymentModel.class);
    }
}
