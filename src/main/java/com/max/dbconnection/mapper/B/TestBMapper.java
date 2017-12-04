package com.max.dbconnection.mapper.B;

import com.max.dbconnection.entity.B.Testb;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TestBMapper {
//    @Select("select * from testb")
    @Transactional(value = "txB", propagation = Propagation.REQUIRED, readOnly = true)
    public List<Testb> getAll();
}
