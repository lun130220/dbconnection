package com.max.dbconnection.service;

import com.max.dbconnection.dao.TestAJdbc;
import com.max.dbconnection.dao.TestBJdbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

@Service
public class JdbcService {

    @Autowired
    private TestAJdbc aJdbc;

    @Autowired
    private TestBJdbc bJdbc;

    @Autowired
    @Qualifier("datasourceA")
    private DataSource adataSource;

    @Autowired
    @Qualifier("datasourceB")
    private DataSource bdataSource;


//    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<? extends Object> getAllJdbc(String connection) {
        Connection conn = null;
        DataSource ds = null;
        try {
            if("a".equalsIgnoreCase(connection)){
                ds = adataSource;
                conn = DataSourceUtils.getConnection(adataSource);
                return aJdbc.getAll(conn);
            }else if("b".equalsIgnoreCase(connection)){
                ds = bdataSource;
                conn = DataSourceUtils.getConnection(bdataSource);
                return bJdbc.getAll(conn);
            }else{
                return aJdbc.getFake();
            }
        }finally {
//            DataSourceUtils.releaseConnection(conn, ds);
        }

    }
}
