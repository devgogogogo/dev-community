package com.fastcampus.devcommunity.common.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Profile("load") // load 프로필에서만 작동
@Configuration
@RequiredArgsConstructor
public class DummySqlRunner {

    private final DataSource dataSource;

    @PostConstruct
    public void runSql() throws Exception {

        var conn = dataSource.getConnection();
        var stmt = conn.createStatement();

        // posts 데이터가 이미 존재하면 실행 안함
        var rs = stmt.executeQuery("SELECT COUNT(*) FROM posts");

        if (rs.next() && rs.getInt(1) > 0) {
            System.out.println(">>> Dummy SQL skipped (posts already exist).");
            return;
        }

        System.out.println(">>> Executing insert_dummy_posts.sql ...");

        ResourceDatabasePopulator populator = new ResourceDatabasePopulator(
                new ClassPathResource("db/load/insert_dummy_posts.sql")
        );

        populator.execute(dataSource);

        System.out.println(">>> Dummy SQL execution completed.");
    }
}

