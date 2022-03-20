package com.dmitriev.andrey.devsecops.db.repos;

import java.sql.PreparedStatement;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dmitriev.andrey.devsecops.db.entities.Scanning;
import com.dmitriev.andrey.devsecops.db.entities.Status;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ScanningRepo {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ScanningRepo(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createNewScan(String name, Status status) {
        jdbcTemplate.execute("INSERT INTO scanning (name, status, scan_time) VALUES (:name, :status, CURRENT_TIMESTAMP)",
                Map.of("name", name,
                        "status", status.name()),
                PreparedStatement::executeUpdate);
    }

    public void updateScan(String name, Status status, String result) {
        jdbcTemplate.execute("UPDATE scanning SET status = :status, result = :result where name = :name",
                Map.of("name", name,
                        "status", status.name(),
                        "result", result),
                PreparedStatement::executeUpdate);
    }

    public List<Scanning> getAllScan() {
        List<Scanning> result = new ArrayList<>();
        jdbcTemplate.query("SELECT id, name, status, scan_time time FROM scanning",
                rs -> {
                    result.add(new Scanning(
                            rs.getLong("id"),
                            rs.getString("name"),
                            Status.valueOf(rs.getString("status")),
                            rs.getTimestamp("scan_time").toLocalDateTime()));
                });
        return result;
    }

    public String getById(long id) {
        return jdbcTemplate.queryForObject("SELECT result FROM scanning WHERE id = :id",
                Map.of("id", id),
                String.class);
    }
}
