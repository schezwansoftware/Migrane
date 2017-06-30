package gk.repository;

import gk.domain.Rit;
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
 * Cassandra repository for the Rit entity.
 */
@Repository
public class RitRepository {

    private final Session session;

    private final Validator validator;

    private Mapper<Rit> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    public RitRepository(Session session, Validator validator) {
        this.session = session;
        this.validator = validator;
        this.mapper = new MappingManager(session).mapper(Rit.class);
        this.findAllStmt = session.prepare("SELECT * FROM rit");
        this.truncateStmt = session.prepare("TRUNCATE rit");
    }

    public List<Rit> findAll() {
        List<Rit> ritsList = new ArrayList<>();
        BoundStatement stmt = findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                Rit rit = new Rit();
                rit.setId(row.getUUID("id"));
                rit.setName(row.getString("name"));
                return rit;
            }
        ).forEach(ritsList::add);
        return ritsList;
    }

    public Rit findOne(UUID id) {
        return mapper.get(id);
    }

    public Rit save(Rit rit) {
        if (rit.getId() == null) {
            rit.setId(UUID.randomUUID());
        }
        Set<ConstraintViolation<Rit>> violations = validator.validate(rit);
        if (violations != null && !violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        mapper.save(rit);
        return rit;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt = truncateStmt.bind();
        session.execute(stmt);
    }
}
