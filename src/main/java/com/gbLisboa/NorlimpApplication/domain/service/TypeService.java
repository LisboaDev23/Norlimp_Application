package com.gbLisboa.NorlimpApplication.domain.service;

import com.gbLisboa.NorlimpApplication.api.model.TypeModel;
import com.gbLisboa.NorlimpApplication.domain.exception.TypeException;
import com.gbLisboa.NorlimpApplication.domain.model.Type;
import com.gbLisboa.NorlimpApplication.domain.repository.TypeRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TypeService {

    private TypeRepository typeRepository;
    private ModelMapper modelMapper;

    public List<TypeModel> findAllTypes (){
        return typeRepository.findAll()
                .stream()
                .map(this::toMapModel)
                .collect(Collectors.toList());
    }

    public TypeModel findType(Long typeId){
        return  typeRepository.findById(typeId)
                .map(this::toMapModel)
                .orElseThrow(() -> new TypeException("Tipo de serviço não encontrado!"));
    }

    @Transactional
    public TypeModel saveType (TypeModel typeModel){
        if (typeRepository.findByNameType(typeModel.getNameType()).isPresent()){
            throw new TypeException("Nome do tipo de serviço já cadastrado!");
        }
        Type type = new Type();
        type.setNameType(typeModel.getNameType());
        type.setDescription(typeModel.getDescription());
        typeRepository.save(type);
        return modelMapper.map(type, TypeModel.class);
    }

    @Transactional
    public void deleteType (Long typeId){
        try{
            typeRepository.deleteById(typeId);
        } catch (RuntimeException e){
            if (!typeRepository.existsById(typeId)){
                throw new TypeException("Tipo de serviço não encontrado!");
            }
            throw new TypeException("Tipo não excluído!");
        }
    }

    @Transactional
    public TypeModel updateModel (Long typeId, TypeModel typeModel){
        Type type = typeRepository.findById(typeId)
                .orElseThrow(() -> new TypeException("Tipo de serviço não encontrado!"));
        if (!type.getNameType().equals(typeModel.getNameType())){
            if (typeRepository.findByNameType(typeModel.getNameType()).isPresent()){
                throw new TypeException("Nome do tipo de serviço já cadastrado!");
            }
            type.setNameType(typeModel.getNameType());
        }
        type.setDescription(typeModel.getDescription());
        type.setId(typeId);
        Type typeUpdate = typeRepository.save(type);
        return toMapModel(typeUpdate);
    }

    private TypeModel toMapModel (Type type){
        return modelMapper.map(type, TypeModel.class);
    }
}


