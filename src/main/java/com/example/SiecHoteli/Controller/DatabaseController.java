package com.example.SiecHoteli.Controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

@RestController
@RequestMapping("/api/v1/db")
public class DatabaseController {

    private final DataSource dataSource;

    public DatabaseController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostMapping("/create_db")
    public String createDatabase() throws IOException, SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("DROP DATABASE siechoteli;");
        jdbcTemplate.execute("CREATE DATABASE siechoteli;");
        jdbcTemplate.execute("USE siechoteli;");

        ClassPathResource resource = new ClassPathResource("siechoteli_scheme.sql");
        BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
        String line;
        StringBuilder script = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            script.append(line);
        }
        reader.close();

        ScriptUtils.executeSqlScript(dataSource.getConnection(), resource);

        return "Database created and objects initialized successfully!";
    }
}
