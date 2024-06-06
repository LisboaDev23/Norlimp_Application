package com.gbLisboa.NorlimpApplication.domain.service;

import com.gbLisboa.NorlimpApplication.domain.exception.ScheduleException;
import com.gbLisboa.NorlimpApplication.domain.model.Payment;
import com.gbLisboa.NorlimpApplication.domain.model.Schedule;
import com.gbLisboa.NorlimpApplication.domain.model.User;
import com.gbLisboa.NorlimpApplication.domain.repository.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class ScheduleService {

    private ScheduleRepository scheduleRepository;
    private UserService userService;
    private PaymentService paymentService;

    public Schedule findSchedule(Long scheduleId){
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleException("Agendamento não encontrado no banco de dados!"));
    }

    @Transactional
    public Schedule saveSchedule(Schedule schedule){
        boolean scheduleIsSave = scheduleRepository.existsById(schedule.getId());
        if (scheduleIsSave){
            throw new ScheduleException("Agendamento já cadastrado no banco de dados.");
        }
        User user = userService.findUser(schedule.getUser().getId());
        schedule.setUser(user);
        Payment payment = paymentService.findPayment(schedule.getPayment().getId());
        schedule.setPayment(payment);
        return scheduleRepository.save(schedule);
    }

    @Transactional
    public void deleteScheduleById(Long scheduleId){
        boolean scheduleExist = scheduleRepository.existsById(scheduleId);
        if (!scheduleExist){
            throw new ScheduleException("O agendamento não existe, logo não é possível excluí-lo do banco de dados");
        }
        scheduleRepository.deleteById(scheduleId);
    }


}
