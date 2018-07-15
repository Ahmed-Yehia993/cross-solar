package com.crossover.techtrial.controller;

import com.crossover.techtrial.model.HourlyElectricity;
import com.crossover.techtrial.model.Panel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;


/**
 * PanelControllerTest class will test all APIs in PanelController.java.
 * @author Crossover
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PanelControllerTest {
  
  MockMvc mockMvc;
  
  @Mock
  private PanelController panelController;
  
  @Autowired
  private TestRestTemplate template;

  @Before
  public void setup() throws Exception {
    mockMvc = MockMvcBuilders.standaloneSetup(panelController).build();
  }

  @Test
  public void testPanelShouldBeNotRegisteredDueToSerialNumberLength() throws Exception {
    HttpEntity<Object> panel = getHttpEntity(
        "{\"serial\": \"232323\", \"longitude\": \"54.123232\"," 
            + " \"latitude\": \"54.123232\",\"brand\":\"tesla\" }");
    ResponseEntity<Panel> response = template.postForEntity(
        "/api/register", panel, Panel.class);
    Assert.assertEquals(400,response.getStatusCode().value());
  }
  @Test
  public void testPanelShouldBeNotRegisteredDueToLongitude() throws Exception {
    HttpEntity<Object> panel = getHttpEntity(
            "{\"serial\": \"232323\", \"longitude\": \"54.13232\","
                    + " \"latitude\": \"54.123232\",\"brand\":\"tesla\" }");
    ResponseEntity<Panel> response = template.postForEntity(
            "/api/register", panel, Panel.class);
    Assert.assertEquals(400,response.getStatusCode().value());
  }

  @Test
  public void testPanelShouldBeNotRegisteredDueToLatitude() throws Exception {
    HttpEntity<Object> panel = getHttpEntity(
            "{\"serial\": \"232323\", \"longitude\": \"54.123232\","
                    + " \"latitude\": \"54.12232\",\"brand\":\"tesla\" }");
    ResponseEntity<Panel> response = template.postForEntity(
            "/api/register", panel, Panel.class);
    Assert.assertEquals(400,response.getStatusCode().value());
  }
  @Test
  public void testPanelShouldBeRegistered() throws Exception {
    HttpEntity<Object> panel = getHttpEntity(
            "{\"serial\": \"1234567890123457\", \"longitude\": \"54.123232\","
                    + " \"latitude\": \"54.123232\",\"brand\":\"tesla\" }");
    ResponseEntity<Panel> response = template.postForEntity(
            "/api/register", panel, Panel.class);
    Assert.assertEquals(202,response.getStatusCode().value());
  }

  @Test
  public void testPanelShouldBeNotRegisteredDueToDuplicateSerialNumber() throws Exception {
    HttpEntity<Object> panel = getHttpEntity(
            "{\"serial\": \"1234567890123457\", \"longitude\": \"54.123232\","
                    + " \"latitude\": \"54.123232\",\"brand\":\"tesla\" }");
    ResponseEntity<Panel> response = template.postForEntity(
            "/api/register", panel, Panel.class);
    Assert.assertEquals(400,response.getStatusCode().value());
  }

  @Test
  public void testSaveHourlyElectricity() throws Exception {
    HttpEntity<Object> hourlyElectricity = getHttpEntity(
            "{\n" +
                    "\t\"generatedElectricity\":\"70\",\n" +
                    "\t\"readingAt\":\"2016-01-25T21:34:55\"\n" +
                    "}");
    ResponseEntity<HourlyElectricity> response = template.postForEntity(
            "/api/panels/1234567890123457/hourly", hourlyElectricity, HourlyElectricity.class);
    Assert.assertEquals(200,response.getStatusCode().value());
  }

  @Test
  public void testSaveHourlyElectricityFaildDueToSerialNumberNotFound() throws Exception {
    HttpEntity<Object> hourlyElectricity = getHttpEntity(
            "{\n" +
                    "\t\"generatedElectricity\":\"70\",\n" +
                    "\t\"readingAt\":\"2016-01-25T21:34:55\"\n" +
                    "}");
    ResponseEntity<HourlyElectricity> response = template.postForEntity(
            "/api/panels/12345678923457/hourly", hourlyElectricity, HourlyElectricity.class);
    Assert.assertEquals(404,response.getStatusCode().value());
  }

  @Test
  public void testGetHourlyElectricityFaildDueToSerialNumberNotFound() throws Exception {
    ResponseEntity<HourlyElectricity> response = template.getForEntity(
            "/api/panels/1234567890123457/hourly", HourlyElectricity.class);
    Assert.assertEquals(200,response.getStatusCode().value());
  }

  @Test
  public void testGetHourlyElectricityPassed() throws Exception {
    ResponseEntity<HourlyElectricity> response = template.getForEntity(
            "/api/panels/12345678923457/hourly", HourlyElectricity.class);
    Assert.assertEquals(404,response.getStatusCode().value());
  }

  private HttpEntity<Object> getHttpEntity(Object body) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return new HttpEntity<Object>(body, headers);
  }
}
