package com.gbLisboa.NorlimpApplication.domain.service;

import com.gbLisboa.NorlimpApplication.domain.exception.ServiceException;
import com.gbLisboa.NorlimpApplication.domain.model.Schedule;
import com.gbLisboa.NorlimpApplication.domain.model.Type;
import com.gbLisboa.NorlimpApplication.domain.repository.ServiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class ServiceService {

    ServiceRepository serviceRepository;
    ScheduleService scheduleService;
    TypeService typeService;

    public com.gbLisboa.NorlimpApplication.domain.model.Service findService(Long serviceId){
        return serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ServiceException("Serviço não encontrado no banco de dados."));
    }

    @Transactional
    public com.gbLisboa.NorlimpApplication.domain.model.Service saveService(com.gbLisboa.NorlimpApplication.domain.model.Service service){
        boolean serviceInUse = serviceRepository.existsById(service.getId());
        if (serviceInUse){
            throw new ServiceException("Serviço já cadastrado no banco de dados.");
        }
        Schedule schedule = scheduleService.findSchedule(service.getSchedule().getId());
        service.setSchedule(schedule);
        Type type = typeService.findType(service.getType().getId());
        service.setType(type);
        return serviceRepository.save(service);
    }

    @Transactional
    public void deleteServiceById (Long serviceId){
        boolean serviceExist = serviceRepository.existsById(serviceId);
        if (!serviceExist){
            throw new ServiceException("Serviço não está cadastrado no banco de dados, logo não tem como excluí-lo.");
        }
        serviceRepository.deleteById(serviceId);
    }



}
