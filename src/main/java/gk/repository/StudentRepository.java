package gk.repository;

import gk.domain.Student;
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
 * Cassandra repository for the Student entity.
 */
@Repository
public class StudentRepository {

    private final Session session;

    private final Validator validator;

    private Mapper<Student> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    public StudentRepository(Session session, Validator validator) {
        this.session = session;
        this.validator = validator;
        this.mapper = new MappingManager(session).mapper(Student.class);
        this.findAllStmt = session.prepare("SELECT * FROM student");
        this.truncateStmt = session.prepare("TRUNCATE student");
    }

    public List<Student> findAll() {
        List<Student> studentsList = new ArrayList<>();
        BoundStatement stmt = findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                Student student = new Student();
                student.setId(row.getUUID("id"));
                student.setName(row.getString("name"));
                student.setAge(row.getInt("age"));
                return student;
            }
        ).forEach(studentsList::add);
        return studentsList;
    }

    public Student findOne(UUID id) {
        return mapper.get(id);
    }

    public Student save(Student student) {
        if (student.getId() == null) {
            student.setId(UUID.randomUUID());
        }
        Set<ConstraintViolation<Student>> violations = validator.validate(student);
        if (violations != null && !violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        mapper.save(student);
        return student;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt = truncateStmt.bind();
        session.execute(stmt);
    }
}
