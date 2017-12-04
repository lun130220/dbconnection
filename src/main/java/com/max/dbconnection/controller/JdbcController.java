package com.max.dbconnection.controller;

import com.max.dbconnection.service.JdbcService;
import com.max.dbconnection.service.JdbcTempleService;
import com.max.dbconnection.service.JpaService;
import com.max.dbconnection.service.MybatisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class JdbcController {

    @Autowired
    private JdbcService jdbcService;

    @Autowired
    private JdbcTempleService jdbcTempleService;

    @Autowired
    private MybatisService mybatisService;

    @Autowired
    private JpaService jpaService;


    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @RequestMapping(value = "/getAllJdbc/{connection}",
            method = RequestMethod.GET, produces = "application/json")
    public List<? extends Object> getAllJdbc(@PathVariable(name = "connection")String connection){
        logger.info("Jdbc: "+connection);
        return jdbcService.getAllJdbc(connection);

    }
    @RequestMapping(value = "/getAllJdbcTemp/{connection}",
            method = RequestMethod.GET, produces = "application/json")
    public List<? extends Object> getAllJdbcTemp(@PathVariable(name = "connection")String connection){
        logger.info("JdbcTemple: "+connection);
        return jdbcTempleService.getAllJdbc(connection);

    }

    @RequestMapping(value = "/getAllJdbcMy/{connection}",
            method = RequestMethod.GET, produces = "application/json")
    public List<? extends Object> getAllJdbcMy(@PathVariable(name = "connection")String connection){
        logger.info("Mybatis: "+connection);
        return mybatisService.getAllJdbc(connection);

    }

    @RequestMapping(value = "/getAllJpa/{connection}",
            method = RequestMethod.GET, produces = "application/json")
    public List<? extends Object> getAllJpa(@PathVariable(name = "connection")String connection){
        logger.info("Jpa: "+connection);
        return jpaService.getAlljpa(connection);

    }


}
