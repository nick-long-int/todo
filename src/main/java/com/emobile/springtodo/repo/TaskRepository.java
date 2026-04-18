package com.emobile.springtodo.repo;

import com.emobile.springtodo.exception.NotFoundException;
import com.emobile.springtodo.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TaskRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<Task> ROW_MAPPER = (ResultSet rs, int rowNum) ->
        new Task(rs.getString("id"),
            rs.getString("title"),
            rs.getString("description"),
            rs.getString("status"));

    public List<Task> findAll(int limit, int offset) {
        return jdbcTemplate.query(
            "select * from task order by id limit ? offset ?"
            , ROW_MAPPER, limit, offset);
    }

    public Task findById(String id) {
        try {
            return jdbcTemplate.queryForObject("select * from task where id = ?", ROW_MAPPER, id);
        } catch (DataAccessException e) {
            throw new NotFoundException("Task with id " + id + " not found");
        }
    }

    public Task save(Task task) {
        if (task.getId() == null) {
            task.setId(UUID.randomUUID().toString());
            jdbcTemplate.update("insert into task values (?,?,?,?)",
                task.getId(), task.getTitle(), task.getDescription(), task.getStatus());
        } else {
            jdbcTemplate.update("update task set title = ?, description = ?, status = ? where id = ?",
                task.getTitle(), task.getDescription(), task.getStatus(), task.getId());
        }

        return task;
    }

    public void deleteById(String id) {
        jdbcTemplate.update("delete from task where id = ?", id);
    }
}
