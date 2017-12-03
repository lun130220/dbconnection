package com.max.dbconnection.service;

import com.max.dbconnection.dao.TestAJdbc;
import com.max.dbconnection.dao.TestBJdbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class JdbcTempleService {
    @Autowired
    private TestAJdbc aJdbc;

    @Autowired
    private TestBJdbc bJdbc;

    public List<? extends Object> getAllJdbc(String connection) {
        if("a".equalsIgnoreCase(connection)){
            return aJdbc.getAllTem();
        }else if("b".equalsIgnoreCase(connection)){
            return bJdbc.getAllTem();
        }else{
            return aJdbc.getFake();
        }

    }

}
