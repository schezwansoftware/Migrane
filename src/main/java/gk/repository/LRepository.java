package gk.repository;

import gk.domain.L;
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
 * Cassandra repository for the L entity.
 */
@Repository
public class LRepository {

    private final Session session;

    private final Validator validator;

    private Mapper<L> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    public LRepository(Session session, Validator validator) {
        this.session = session;
        this.validator = validator;
        this.mapper = new MappingManager(session).mapper(L.class);
        this.findAllStmt = session.prepare("SELECT * FROM l");
        this.truncateStmt = session.prepare("TRUNCATE l");
    }

    public List<L> findAll() {
        List<L> lsList = new ArrayList<>();
        BoundStatement stmt = findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                L l = new L();
                l.setId(row.getUUID("id"));
                l.setLength(row.getInt("length"));
                l.setBreadth(row.getInt("breadth"));
                l.setArea(row.getDouble("area"));
                return l;
            }
        ).forEach(lsList::add);
        return lsList;
    }

    public L findOne(UUID id) {
        return mapper.get(id);
    }

    public L save(L l) {
        if (l.getId() == null) {
            l.setId(UUID.randomUUID());
        }
        Set<ConstraintViolation<L>> violations = validator.validate(l);
        if (violations != null && !violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        mapper.save(l);
        return l;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt = truncateStmt.bind();
        session.execute(stmt);
    }
}
