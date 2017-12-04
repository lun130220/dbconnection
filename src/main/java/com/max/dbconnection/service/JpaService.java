package com.max.dbconnection.service;

import com.max.dbconnection.entity.A.Testa;
import com.max.dbconnection.entity.B.Testb;
import com.max.dbconnection.repository.A.ARepository;
import com.max.dbconnection.repository.B.BRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JpaService {
    @Autowired
    private ARepository aRepository;

    @Autowired
    private BRepository bRepository;

    public List<? extends Object> getAlljpa(String connection) {
        if("a".equalsIgnoreCase(connection)){
            List target = new ArrayList<Testa>();
            aRepository.findAll().forEach(target::add);
            return target;
        }else if("b".equalsIgnoreCase(connection)){
            List target = new ArrayList<Testb>();
            bRepository.findAll().forEach(target::add);
            return target;
        }else{
            return new ArrayList<>();
        }

    }


}
