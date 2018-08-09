package com.ssp.table;

import liquibase.integration.spring.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.core.io.*;
import org.springframework.util.*;

import javax.sql.*;
import java.util.*;

@Configuration
public class LiquibaseConfig {

   @Autowired
   private DataSource dataSource;

    @Autowired
    private ResourceLoader resourceLoader;

    @Bean
    public SpringLiquibase liquibase() throws Exception {
        //      Locate change log file
        String changelogFile = "classpath:/db/liquibase-changelog.xml";
        Resource resource = resourceLoader.getResource(changelogFile);

        Assert.state(resource.exists(), "Unable to find file: " + resource.getFilename());
        // Configure Liquibase
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog(changelogFile);
        liquibase.setDataSource(dataSource);
        liquibase.setShouldRun(true);
        if(liquibase.isDropFirst())
        liquibase.setDropFirst(true);

        // Verbose logging
        Map<String, String> params = new HashMap<>();
        params.put("verbose", "true");
        return liquibase;
    }
}
