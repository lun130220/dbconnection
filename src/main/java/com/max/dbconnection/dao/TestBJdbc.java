package com.max.dbconnection.dao;

import com.max.dbconnection.entity.Testb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TestBJdbc {

    @Autowired
    @Qualifier("jdbcTemB")
    JdbcTemplate jdbcTemB;

    @Transactional(value = "txB", propagation = Propagation.REQUIRED, readOnly = true)
    public List<Testb> getAllTem() {
        String sql = "select * from testb";
        return jdbcTemB.query(sql, (rs, i)->{
            Testb testb = new Testb();
            testb.setId(rs.getLong("id"));
            testb.setName(rs.getString("name"));
            testb.setValue(rs.getString("value"));
            return testb;
        });
    }


    @Transactional(value = "txB", propagation = Propagation.REQUIRED, readOnly = true)
    public List<Testb> getAll(Connection connection) {
        String sql = "select * from testb";
        List result = new ArrayList<Testb>();

        try(
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
        ){
            while(rs.next()){
                Testb testb = new Testb();
                testb.setId(rs.getLong("id"));
                testb.setName(rs.getString("name"));
                testb.setValue(rs.getString("value"));
                result.add(testb);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return result;
    }


}
