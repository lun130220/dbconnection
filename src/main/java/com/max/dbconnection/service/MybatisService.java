package com.max.dbconnection.service;

import com.max.dbconnection.mapper.A.TestAMapper;
import com.max.dbconnection.mapper.B.TestBMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MybatisService {

    @Autowired
    private TestAMapper testAMapper;
    @Autowired
    private TestBMapper testBMapper;

    public List<? extends Object> getAllJdbc(String connection) {
        if("a".equalsIgnoreCase(connection)){
            return testAMapper.getAll();
        }else if("b".equalsIgnoreCase(connection)){
            return testBMapper.getAll();
        }else{
            return new ArrayList<>();
        }

    }

}
