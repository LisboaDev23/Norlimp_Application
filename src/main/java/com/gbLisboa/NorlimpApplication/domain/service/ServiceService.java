package com.gbLisboa.NorlimpApplication.domain.service;

import com.gbLisboa.NorlimpApplication.api.model.ServiceModel;
import com.gbLisboa.NorlimpApplication.domain.exception.ScheduleException;
import com.gbLisboa.NorlimpApplication.domain.exception.ServiceException;
import com.gbLisboa.NorlimpApplication.domain.exception.TypeException;
import com.gbLisboa.NorlimpApplication.domain.model.Schedule;
import com.gbLisboa.NorlimpApplication.domain.model.Type;
import com.gbLisboa.NorlimpApplication.domain.repository.ScheduleRepository;
import com.gbLisboa.NorlimpApplication.domain.repository.ServiceRepository;
import com.gbLisboa.NorlimpApplication.domain.repository.TypeRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ServiceService {

    private ServiceRepository serviceRepository;
    private ModelMapper modelMapper;
    private TypeRepository typeRepository;
    private ScheduleRepository scheduleRepository;


    public List<ServiceModel> findAllServices(){
        return serviceRepository.findAll()
                .stream()
                .map(service -> modelMapper.map(service, ServiceModel.class))
                .collect(Collectors.toList());
    }

    public ServiceModel findService(Long serviceId){
        return serviceRepository.findById(serviceId)
                .map(service -> modelMapper.map(service, ServiceModel.class))
                .orElseThrow(() -> new ServiceException("Serviço não encontrado!"));
    }

    @Transactional
    public ServiceModel saveService(ServiceModel serviceModel){
        String nameServiceRequest = serviceRepository.findByNameService(serviceModel.getNameService())
                .toString();
        boolean nameServiceFoundOnDataBase = serviceRepository.findByNameService(nameServiceRequest).isPresent();
        //if nameService is present on db, throw new Exception
        if (nameServiceFoundOnDataBase){
            throw new ServiceException("Serviço já se encontra cadastrado no banco de dados.");
        }
        Type type = typeRepository.findById(serviceModel.getType().getId())
                .orElseThrow(() -> new TypeException("Tipo de serviço não encontrado!"));

        Schedule schedule = scheduleRepository.findById(serviceModel.getSchedule().getId())
                .orElseThrow(() -> new ScheduleException("Agendamento não encontrado!"));

        com.gbLisboa.NorlimpApplication.domain.model.Service service = new com.gbLisboa.NorlimpApplication.domain.model.Service();
        service.setNameService(serviceModel.getNameService());
        service.setDescription(serviceModel.getDescription());
        service.setType(type);
        service.setSchedule(schedule);
        serviceRepository.save(service);
        schedule.getServiceRequest().add(service);
        type.getServiceList().add(service);
        return modelMapper.map(service, ServiceModel.class);
    }

    @Transactional
    public void deleteService (Long serviceId){
        try{
            serviceRepository.deleteById(serviceId);
        } catch (RuntimeException e){
            if (!serviceRepository.existsById(serviceId)){
                throw new ServiceException("Serviço não encontrado!");
            }
            throw new ServiceException("Não foi possível excluír o serviço!");
        }
    }

    @Transactional
    public ServiceModel updateService(Long serviceId,
                                      ServiceModel serviceModel){
        com.gbLisboa.NorlimpApplication.domain.model.Service service =
                serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ServiceException("Serviço não encontrado!"));
        Type type = typeRepository.findById(serviceModel.getType().getId())
                .orElseThrow(() -> new TypeException("Tipo de serviço não encontrado!"));

        Schedule schedule = scheduleRepository.findById(serviceModel.getSchedule().getId())
                .orElseThrow(() -> new ScheduleException("Agendamento não encontrado!!"));

        if (!service.getNameService().equals(serviceModel.getNameService())){
            if (serviceRepository.findByNameService(serviceModel.getNameService()).isPresent()){
                throw new ServiceException("Serviço com nome já cadastrado, tente novamente!");
            }
            service.setNameService(serviceModel.getNameService());
        }
        service.setDescription(serviceModel.getDescription());
        service.setType(type);
        service.setSchedule(schedule);
        service.setId(serviceId);
        com.gbLisboa.NorlimpApplication.domain.model.Service serviceUpdate = serviceRepository.save(service);
        return toServiceModel(serviceUpdate);
    }

    private ServiceModel toServiceModel(com.gbLisboa.NorlimpApplication.domain.model.Service service){
        return modelMapper.map(service, ServiceModel.class);
    }

}
