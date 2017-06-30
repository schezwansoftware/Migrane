package gk.repository;

import gk.domain.Ar;
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
 * Cassandra repository for the Ar entity.
 */
@Repository
public class ArRepository {

    private final Session session;

    private final Validator validator;

    private Mapper<Ar> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    public ArRepository(Session session, Validator validator) {
        this.session = session;
        this.validator = validator;
        this.mapper = new MappingManager(session).mapper(Ar.class);
        this.findAllStmt = session.prepare("SELECT * FROM ar");
        this.truncateStmt = session.prepare("TRUNCATE ar");
    }

    public List<Ar> findAll() {
        List<Ar> arsList = new ArrayList<>();
        BoundStatement stmt = findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                Ar ar = new Ar();
                ar.setId(row.getUUID("id"));
                ar.setName(row.getString("name"));
                return ar;
            }
        ).forEach(arsList::add);
        return arsList;
    }

    public Ar findOne(UUID id) {
        return mapper.get(id);
    }

    public Ar save(Ar ar) {
        if (ar.getId() == null) {
            ar.setId(UUID.randomUUID());
        }
        Set<ConstraintViolation<Ar>> violations = validator.validate(ar);
        if (violations != null && !violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        mapper.save(ar);
        return ar;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt = truncateStmt.bind();
        session.execute(stmt);
    }
}
