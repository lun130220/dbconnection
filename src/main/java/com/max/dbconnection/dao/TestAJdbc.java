package com.max.dbconnection.dao;

import com.max.dbconnection.entity.A.Testa;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TestAJdbc {

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<Testa> getFake() {
        return new ArrayList<>();
    }


    @Autowired
    JdbcTemplate jdbcTemA;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<Testa> getAllTem() {
        String sql = "select * from testa";
        return jdbcTemA.query(sql, (rs, i)->{
            Testa testa = new Testa();
            testa.setId(rs.getLong("id"));
            testa.setName(rs.getString("name"));
            testa.setValue(rs.getString("value"));
            return testa;
        });
    }


    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<Testa> getAll(Connection connection) {
        String sql = "select * from testa";
        List result = new ArrayList<Testa>();

        try(
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
        ){
            while(rs.next()){
                Testa testa = new Testa();
                testa.setId(rs.getLong("id"));
                testa.setName(rs.getString("name"));
                testa.setValue(rs.getString("value"));
                result.add(testa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return result;
    }


}
