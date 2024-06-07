package com.gbLisboa.NorlimpApplication.api.controller;

import com.gbLisboa.NorlimpApplication.api.model.ScheduleModel;
import com.gbLisboa.NorlimpApplication.domain.model.Schedule;
import com.gbLisboa.NorlimpApplication.domain.repository.ScheduleRepository;
import com.gbLisboa.NorlimpApplication.domain.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private ScheduleService scheduleService;
    private ScheduleRepository scheduleRepository;
    private ModelMapper modelMapper;

    @GetMapping
    public List<Schedule> findAllSchedules(){
        return scheduleRepository.findAll();
    }
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleModel> findSchedule(@PathVariable Long scheduleId){
        return scheduleRepository.findById(scheduleId)
                .map(schedule -> modelMapper.map(schedule, ScheduleModel.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Schedule registerSchedule(@Valid @RequestBody Schedule schedule){
        return scheduleService.saveSchedule(schedule);
    }
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule (@PathVariable Long scheduleId){
        if (!scheduleRepository.existsById(scheduleId)){
            return ResponseEntity.notFound().build();
        }
        scheduleRepository.deleteById(scheduleId);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{scheduleId}")
    public ResponseEntity<ScheduleModel> updateSchedule (@PathVariable Long scheduleId,
                                                         @Valid @RequestBody Schedule schedule){
        if (!scheduleRepository.existsById(scheduleId)){
            return ResponseEntity.notFound().build();
        }
        schedule.setId(scheduleId);
        scheduleService.saveSchedule(schedule);
        return scheduleRepository.findById(schedule.getId())
                .map(s -> modelMapper.map(s, ScheduleModel.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
