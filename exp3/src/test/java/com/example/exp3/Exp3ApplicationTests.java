package com.example.exp3;

import com.example.exp3.service.CsvDataService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("JavadocReference")
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.NONE,classes = {CsvDataService.class})
class Exp3ApplicationTests {

    @Autowired
    CsvDataService coronaVirusDataService;

    @Test
    void contextLoads() {
        assertTrue(coronaVirusDataService.getAllRegionstats().size()>100);
    }

}
