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

@AllArgsConstructor
@RestController
@RequestMapping("/service")
public class ServiceController {

    private ServiceRepository serviceRepository;
    private ServiceService serviceService;
    private ModelMapper modelMapper;

    @GetMapping
    public List<Service> findAllServices(){
        return serviceRepository.findAll();
    }
    @GetMapping("/{serviceId}")
    public ResponseEntity<ServiceModel> findService(@PathVariable Long serviceId){
        return serviceRepository.findById(serviceId)
                .map(service -> modelMapper.map(service, ServiceModel.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Service registerService(@Valid @RequestBody Service service){
        return serviceService.saveService(service);
    }
    @DeleteMapping("/{serviceId}")
    public ResponseEntity<Void> deleteService(@PathVariable Long serviceId){
        if (!serviceRepository.existsById(serviceId)){
            return ResponseEntity.notFound().build();
        }
        serviceRepository.deleteById(serviceId);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{serviceId}")
    public ResponseEntity<ServiceModel> updateService(@PathVariable Long serviceId,
                                                      @Valid @RequestBody Service service){
        if (!serviceRepository.existsById(serviceId)){
            return ResponseEntity.notFound().build();
        }
        service.setId(serviceId);
        serviceService.saveService(service);
        return serviceRepository.findById(service.getId())
                .map(s -> modelMapper.map(s, ServiceModel.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
