package com.max.dbconnection.mapper.A;

import com.max.dbconnection.entity.A.Testa;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TestAMapper {
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    @Select("select * from testa")
    public List<Testa> getAll();

}
