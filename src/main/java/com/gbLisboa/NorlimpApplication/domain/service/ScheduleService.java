package com.gbLisboa.NorlimpApplication.domain.service;

import com.gbLisboa.NorlimpApplication.api.model.ScheduleModel;
import com.gbLisboa.NorlimpApplication.domain.exception.PaymentException;
import com.gbLisboa.NorlimpApplication.domain.exception.ScheduleException;
import com.gbLisboa.NorlimpApplication.domain.exception.UserException;
import com.gbLisboa.NorlimpApplication.domain.model.Payment;
import com.gbLisboa.NorlimpApplication.domain.model.Schedule;
import com.gbLisboa.NorlimpApplication.domain.model.User;
import com.gbLisboa.NorlimpApplication.domain.repository.PaymentRepository;
import com.gbLisboa.NorlimpApplication.domain.repository.ScheduleRepository;
import com.gbLisboa.NorlimpApplication.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ScheduleService {

    private ScheduleRepository scheduleRepository;
    private UserRepository userRepository;
    private PaymentRepository paymentRepository;
    private ModelMapper modelMapper;

    public List<ScheduleModel> findAllSchedules (){
        return scheduleRepository.findAll()
                .stream()
                .map(this::toScheduleModel)
                .collect(Collectors.toList());
    }

    public ScheduleModel findSchedule(Long scheduleId){
        return scheduleRepository.findById(scheduleId)
                .map(this::toScheduleModel)
                .orElseThrow(() -> new ScheduleException("Agendamento não encontrado!"));
    }
    @Transactional
    public ScheduleModel saveSchedule(ScheduleModel scheduleModel){
        //um agendamento possui um user de referencia, possui um payment tbm, e um list de serviços
        //mas essa lista não convém adicionar agora pois ela não é obrigatória
        User user = userRepository.findById(scheduleModel.getUser())
                .orElseThrow(() -> new UserException("Usuário não encontrado!"));
        Payment payment = paymentRepository.findById(scheduleModel.getPayment())
                .orElseThrow(() -> new PaymentException("Pagamento não encontrado!"));
        Schedule schedule = new Schedule();
        schedule.setDates(scheduleModel.getDates());
        schedule.setUser(user);
        schedule.setPayment(payment);
        scheduleRepository.save(schedule);
        return modelMapper.map(schedule, ScheduleModel.class);
    }
    @Transactional
    public void deleteSchedule(Long scheduleId){
        try {
            scheduleRepository.deleteById(scheduleId);
        } catch (RuntimeException e){
            if (!scheduleRepository.existsById(scheduleId)){
                throw new ScheduleException("Agendamento não encontrado!");
            }
            throw new ScheduleException("Agendamento não excluído!");
        }
    }

    @Transactional
    public ScheduleModel updateSchedule(Long scheduleId, ScheduleModel scheduleModel){
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleException("Agendamento não encontrado!"));
        User user = userRepository.findById(scheduleModel.getUser())
                .orElseThrow(() -> new UserException("Usuário não encontrado!"));
        Payment payment = paymentRepository.findById(scheduleModel.getPayment())
                .orElseThrow(() -> new PaymentException("Pagamento não encontrado!"));

        schedule.setDates(scheduleModel.getDates());
        schedule.setUser(user);
        schedule.setPayment(payment);
        schedule.setId(scheduleId);
        Schedule scheduleUpdate = scheduleRepository.save(schedule);
        return toScheduleModel(scheduleUpdate);
    }

    private ScheduleModel toScheduleModel (Schedule schedule){
        return modelMapper.map(schedule, ScheduleModel.class);
    }
}
