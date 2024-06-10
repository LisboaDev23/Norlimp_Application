package com.gbLisboa.NorlimpApplication.domain.service;

import com.gbLisboa.NorlimpApplication.api.model.ScheduleModel;
import com.gbLisboa.NorlimpApplication.domain.exception.ScheduleException;
import com.gbLisboa.NorlimpApplication.domain.model.Payment;
import com.gbLisboa.NorlimpApplication.domain.model.Schedule;
import com.gbLisboa.NorlimpApplication.domain.model.User;
import com.gbLisboa.NorlimpApplication.domain.repository.ScheduleRepository;
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
    private UserService userService;
    private PaymentService paymentService;
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
    public ScheduleModel saveSchedule(Schedule schedule){
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

    private ScheduleModel toScheduleModel (Schedule schedule){
        return modelMapper.map(schedule, ScheduleModel.class);
    }
}
