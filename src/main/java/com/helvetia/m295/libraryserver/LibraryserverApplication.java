package com.helvetia.m295.libraryserver;

import com.helvetia.m295.libraryserver.model.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.sql.init.SqlDataSourceScriptDatabaseInitializer;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationProperties;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * Klasse mit Einstiegsmethode main
 *
 * @author Simon Fäs
 * @version 1.0.0
 */
@SpringBootApplication
public class LibraryserverApplication {

    /**
     * Einstiegsmethode
     * Startet Applikation
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(LibraryserverApplication.class, args);
    }

    @Bean
    SqlDataSourceScriptDatabaseInitializer dataSourceScriptDatabaseInitializer(DataSource dataSource, SqlInitializationProperties properties, UserRepository repository) {
        // This bean ensures the database is only initialized when empty
        return new SqlDataSourceScriptDatabaseInitializer(dataSource, properties) {
            @Override
            public boolean initializeDatabase() {
                if (repository.count() == 0L) {
                    return super.initializeDatabase();
                }
                return false;
            }
        };
    }

}
