package com.mindex.challenge.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import com.mindex.challenge.data.ReportingStructure;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {

    private String reportingStructureUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        reportingStructureUrl = "http://localhost:" + port + "/reportingStructure/{id}";
    }

    @Test
    public void testGetReportingStructure() {
        // John Lennon
        String testEmployeeId1 = "16a596ae-edd3-4847-99fe-c4518e82c86f";
        // Ringo Starr
        String testEmployeeId2 = "03aa1462-ffa9-4978-901b-7c001562cf6f";

        ReportingStructure testReportingStructure = restTemplate
                .getForEntity(reportingStructureUrl, ReportingStructure.class, testEmployeeId1)
                .getBody();
        ReportingStructure testReportingStructure2 = restTemplate
                .getForEntity(reportingStructureUrl, ReportingStructure.class, testEmployeeId2)
                .getBody();

        assertNotNull(testReportingStructure);
        assertEquals(4, testReportingStructure.getNumberOfReports());
        assertNotNull(testReportingStructure2);
        assertEquals(2, testReportingStructure2.getNumberOfReports());
    }
}
