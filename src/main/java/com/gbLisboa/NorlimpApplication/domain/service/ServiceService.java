package com.gbLisboa.NorlimpApplication.domain.service;

import com.gbLisboa.NorlimpApplication.api.model.ServiceModel;
import com.gbLisboa.NorlimpApplication.domain.exception.ServiceException;
import com.gbLisboa.NorlimpApplication.domain.model.Schedule;
import com.gbLisboa.NorlimpApplication.domain.model.Type;
import com.gbLisboa.NorlimpApplication.domain.repository.ServiceRepository;
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
    public ServiceModel saveService(com.gbLisboa.NorlimpApplication.domain.model.Service service){
        String nameServiceRequest = service.getNameService();
        boolean nameServiceFoundOnDataBase = serviceRepository.findByNameService(nameServiceRequest).isPresent();
        if (nameServiceFoundOnDataBase){
            throw new ServiceException("Serviço já se encontra cadastrado no banco de dados.");
        }
        serviceRepository.save(service);
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
                                      com.gbLisboa.NorlimpApplication.domain.model.Service service){
        com.gbLisboa.NorlimpApplication.domain.model.Service serviceInDataBase = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ServiceException("Serviço não encontrado!"));
        service.setId(serviceId);
        serviceInDataBase.setNameService(service.getNameService());
        serviceInDataBase.setType(service.getType());
        serviceInDataBase.setSchedule(service.getSchedule());
        serviceInDataBase.setDescription(service.getDescription());

        ServiceModel serviceModel = serviceRepository.findById(serviceId)
                .map(s -> modelMapper.map(s, ServiceModel.class))
                .orElseThrow(() -> new ServiceException("Serviço não encontrado!"));
        serviceRepository.save(service);
        return serviceModel;
    }


}
