package gk.repository;

import gk.domain.Emp;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.*;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Cassandra repository for the Emp entity.
 */
@Repository
public class EmpRepository {

    private final Session session;

    private final Validator validator;

    private Mapper<Emp> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    public EmpRepository(Session session, Validator validator) {
        this.session = session;
        this.validator = validator;
        this.mapper = new MappingManager(session).mapper(Emp.class);
        this.findAllStmt = session.prepare("SELECT * FROM emp");
        this.truncateStmt = session.prepare("TRUNCATE emp");
    }

    public List<Emp> findAll() {
        List<Emp> empsList = new ArrayList<>();
        BoundStatement stmt = findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                Emp emp = new Emp();
                emp.setId(row.getUUID("id"));
                emp.setName(row.getString("name"));
                emp.setAge(row.getInt("age"));
                return emp;
            }
        ).forEach(empsList::add);
        return empsList;
    }

    public Emp findOne(UUID id) {
        return mapper.get(id);
    }

    public Emp save(Emp emp) {
        if (emp.getId() == null) {
            emp.setId(UUID.randomUUID());
        }
        Set<ConstraintViolation<Emp>> violations = validator.validate(emp);
        if (violations != null && !violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        mapper.save(emp);
        return emp;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt = truncateStmt.bind();
        session.execute(stmt);
    }
}
