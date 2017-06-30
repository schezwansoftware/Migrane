package gk.repository;

import gk.domain.Harsh;
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
 * Cassandra repository for the Harsh entity.
 */
@Repository
public class HarshRepository {

    private final Session session;

    private final Validator validator;

    private Mapper<Harsh> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    public HarshRepository(Session session, Validator validator) {
        this.session = session;
        this.validator = validator;
        this.mapper = new MappingManager(session).mapper(Harsh.class);
        this.findAllStmt = session.prepare("SELECT * FROM harsh");
        this.truncateStmt = session.prepare("TRUNCATE harsh");
    }

    public List<Harsh> findAll() {
        List<Harsh> harshesList = new ArrayList<>();
        BoundStatement stmt = findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                Harsh harsh = new Harsh();
                harsh.setId(row.getUUID("id"));
                harsh.setArc(row.getString("arc"));
                return harsh;
            }
        ).forEach(harshesList::add);
        return harshesList;
    }

    public Harsh findOne(UUID id) {
        return mapper.get(id);
    }

    public Harsh save(Harsh harsh) {
        if (harsh.getId() == null) {
            harsh.setId(UUID.randomUUID());
        }
        Set<ConstraintViolation<Harsh>> violations = validator.validate(harsh);
        if (violations != null && !violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        mapper.save(harsh);
        return harsh;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt = truncateStmt.bind();
        session.execute(stmt);
    }
}
