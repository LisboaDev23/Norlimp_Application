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
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private ScheduleService scheduleService;
    private ScheduleRepository scheduleRepository;

    @GetMapping("/list")
    public List<ScheduleModel> findAll(){
        return scheduleService.findAllSchedules();
    }
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleModel> find(@PathVariable Long scheduleId){
        if (!scheduleRepository.existsById(scheduleId)){
            return ResponseEntity.notFound().build();
        }
        ScheduleModel scheduleFound = scheduleService.findSchedule(scheduleId);
        return ResponseEntity.ok(scheduleFound);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public ScheduleModel register(@Valid @RequestBody ScheduleModel scheduleModel){
        return scheduleService.saveSchedule(scheduleModel);
    }
    @DeleteMapping("/delete/{scheduleId}")
    public ResponseEntity<Void> delete (@PathVariable Long scheduleId){
        if (!scheduleRepository.existsById(scheduleId)){
            return ResponseEntity.notFound().build();
        }
        scheduleService.deleteSchedule(scheduleId);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/update/{scheduleId}")
    public ResponseEntity<ScheduleModel> update (@PathVariable Long scheduleId,
                                                         @Valid @RequestBody ScheduleModel scheduleModel){
        if (!scheduleRepository.existsById(scheduleId)){
            return ResponseEntity.notFound().build();
        }
        ScheduleModel scheduleUpdate = scheduleService.updateSchedule(scheduleId, scheduleModel);
        return ResponseEntity.ok(scheduleUpdate);
    }
}
