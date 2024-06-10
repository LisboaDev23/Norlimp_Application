package com.gbLisboa.NorlimpApplication.api.controller;

import com.gbLisboa.NorlimpApplication.api.model.ServiceModel;
import com.gbLisboa.NorlimpApplication.domain.model.Service;
import com.gbLisboa.NorlimpApplication.domain.repository.ServiceRepository;
import com.gbLisboa.NorlimpApplication.domain.service.ServiceService;
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
@RequestMapping("/service")
public class ServiceController {

    private ServiceRepository serviceRepository;
    private ServiceService serviceService;
    private ModelMapper modelMapper;

    @GetMapping("/listServices")
    public List<ServiceModel> findAll(){
        return serviceService.findAllServices();
    }
    @GetMapping("/{serviceId}")
    public ResponseEntity<ServiceModel> find(@PathVariable Long serviceId){
        if (!serviceRepository.existsById(serviceId)){
            return ResponseEntity.notFound().build();
        }
        ServiceModel serviceFound = serviceService.findService(serviceId);
        return ResponseEntity.ok(serviceFound);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public ResponseEntity<ServiceModel> register(@Valid @RequestBody Service service){
        ServiceModel serviceCreated = serviceService.saveService(service);
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceCreated);
    }
    @DeleteMapping("/delete/{serviceId}")
    public ResponseEntity<Void> delete(@PathVariable Long serviceId){
        if (!serviceRepository.existsById(serviceId)){
            return ResponseEntity.notFound().build();
        }
        serviceService.deleteService(serviceId);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/update/{serviceId}")
    public ResponseEntity<ServiceModel> update(@PathVariable Long serviceId,
                                                      @Valid @RequestBody Service service){
        if (!serviceRepository.existsById(serviceId)){
            return ResponseEntity.notFound().build();
        }
        ServiceModel serviceUpdate = serviceService.updateService(serviceId,service);
        return ResponseEntity.ok(serviceUpdate);
    }

    private ServiceModel toServiceModel(Service service){
        return modelMapper.map(service, ServiceModel.class);
    }
}
