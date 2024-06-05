package com.gbLisboa.NorlimpApplication.domain.service;

import com.gbLisboa.NorlimpApplication.domain.exception.TypeException;
import com.gbLisboa.NorlimpApplication.domain.model.Type;
import com.gbLisboa.NorlimpApplication.domain.repository.TypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class TypeService {

    TypeRepository typeRepository;

    public Type findType(Long typeId){
        return typeRepository.findById(typeId)
                .orElseThrow(() -> new TypeException("Tipo de serviço não encontrado no banco de dados."));
    }

    @Transactional
    public Type saveType (Type type){
        boolean typeIsSave = typeRepository.existsById(type.getId());
        if (typeIsSave){
            throw new TypeException("Tipo de serviço já está cadastrado no banco de dados.");
        }
        return typeRepository.save(type);
    }

    @Transactional
    public void deleteTypeById(Long typeId){
        boolean typeExist = typeRepository.existsById(typeId);
        if (!typeExist){
            throw new TypeException("Tipo de serviço não cadastrado no banco de dados, portanto não é possível efetuar a operação.");
        }
        typeRepository.deleteById(typeId);
    }

}


