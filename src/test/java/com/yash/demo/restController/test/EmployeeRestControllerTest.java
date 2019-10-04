package com.yash.demo.restController.test;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

import java.io.StringWriter;
import java.util.Arrays;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import com.google.gson.Gson;
import com.yash.demo.model.Employee;
import com.yash.demo.restController.EmployeeRestController;
import com.yash.demo.service.EmployeeService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = EmployeeRestController.class)
public class EmployeeRestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployeeService employeeServiceMock;

	@Test
	public void shouldReturnListOfAllEmployeeInJSONandXMLWhengetAllEmployeeIsInvoked() throws Exception {

		Employee firstEmp = new Employee(123, "Shankar", "Engineer", 1234);
		Employee secondEmp = new Employee(124, "Shashi", "Engineer", 1234);

		when(employeeServiceMock.getAllEmployee()).thenReturn(Arrays.asList(firstEmp, secondEmp));

		RequestBuilder requestBuilder1 = get("/employee").accept(org.springframework.http.MediaType.APPLICATION_JSON);
		RequestBuilder requestBuilder2 = get("/employee").accept(org.springframework.http.MediaType.APPLICATION_XML);

		mockMvc.perform(requestBuilder1).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].empId", is(123))).andExpect(jsonPath("$[0].empName", is("Shankar")))
				.andExpect(jsonPath("$[0].empDept", is("Engineer"))).andExpect(jsonPath("$[0].empSalary", is(1234)))
				.andExpect(jsonPath("$[0].empId", is(123))).andExpect(jsonPath("$[0].empName", is("Shankar")))
				.andExpect(jsonPath("$[0].empDept", is("Engineer"))).andExpect(jsonPath("$[0].empSalary", is(1234)));

		mockMvc.perform(requestBuilder2).andExpect(status().isOk()).andExpect(xpath("List/item/empId").string("123"));

		verify(employeeServiceMock, times(2)).getAllEmployee();
	}

	@Test
	public void shouldReturnObjectOfEmployeeWhenFindEmployeeByEmpIdIsInvokedWithValidEmployeeID() throws Exception {
		Employee firstEmp = new Employee(123, "Shankar", "Engineer", 1234);

		RequestBuilder requestBuilder1 = get("/employee/123")
				.accept(org.springframework.http.MediaType.APPLICATION_JSON);
		RequestBuilder requestBuilder2 = get("/employee/123")
				.accept(org.springframework.http.MediaType.APPLICATION_XML);

		when(employeeServiceMock.findByID(123)).thenReturn(firstEmp);

		mockMvc.perform(requestBuilder1).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(jsonPath("empId", is(123)))
				.andExpect(jsonPath("empName", is("Shankar"))).andExpect(jsonPath("empDept", is("Engineer")))
				.andExpect(jsonPath("empSalary", is(1234)));

		mockMvc.perform(requestBuilder2).andExpect(status().isOk()).andExpect(xpath("Employee/empId").string("123"));

		verify(employeeServiceMock, times(2)).findByID(123);
	}

	@Test
	public void shouldSaveEmployeeWhenSaveEmployeeInJSONFormatIsInvoked() throws Exception {

		Employee firstEmp = new Employee(null, "Shankar", "Engineer", 1234);

		Gson gson = new Gson();
		String json = gson.toJson(firstEmp);

		mockMvc.perform(post("/employee").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isCreated());
	}

	@Test
	public void shouldSaveEmployeeWhenSaveEmployeeInXMLFormatIsInvoked() throws Exception {

		Employee firstEmp = new Employee(null, "Shankar", "Engineer", 1234);

		JAXBContext jaxbContext = JAXBContext.newInstance(Employee.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		StringWriter sw = new StringWriter();
		jaxbMarshaller.marshal(firstEmp, sw);
		String xmlContent = sw.toString();

		mockMvc.perform(post("/employee").contentType(MediaType.APPLICATION_XML).content(xmlContent))
				.andExpect(status().isCreated());
	}

	@Test
	public void shouldUpdateEmployeeValueWhenUpdateEmployeeIsInvokedForJSON() throws Exception {

		Employee employee = new Employee(null, "Shankar", "Engineer", 1234);

		Gson gson = new Gson();
		String json = gson.toJson(employee);

		when(employeeServiceMock.updateEmployeeByID(employee, 123)).thenReturn(1);

		mockMvc.perform(put("/employee/123").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isOk());

	}

	@Test
	public void shouldThrowExceptionWhenUpdateEmployeeIsInvokedForJSONWithInvalidEmployeeID() throws Exception {

		Employee employee = new Employee(null, "Shankar", "Engineer", 1234);

		Gson gson = new Gson();
		String json = gson.toJson(employee);

		when(employeeServiceMock.updateEmployeeByID(employee, 123)).thenReturn(0);

		mockMvc.perform(put("/employee/123").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(jsonPath("status", is(404)))
				.andExpect(jsonPath("errorMsg", is("no data associate with employee id :123")))
				.andExpect(status().isNotFound());

	}

	@Test
	public void shouldUpdateEmployeeValueWhenUpdateEmployeeIsInvokedForXML() throws Exception {

		Employee employee = new Employee(null, "Shankar", "Engineer", 1234);

		JAXBContext jaxbContext = JAXBContext.newInstance(Employee.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		StringWriter sw = new StringWriter();
		jaxbMarshaller.marshal(employee, sw);
		String xmlContent = sw.toString();

		when(employeeServiceMock.updateEmployeeByID(employee, 123)).thenReturn(1);

		mockMvc.perform(put("/employee/123").contentType(MediaType.APPLICATION_XML).content(xmlContent))
				.andExpect(status().isOk());

	}
	
	@Test
	public void shouldDeleteEmployeeDataWhenDeleteEmployeeByIdIsInvokedWithExistingEmployeeId() throws Exception {
		doNothing().when(employeeServiceMock).deleteEmployeeByID(123);
		
		mockMvc.perform(delete("/employee/123").contentType(MediaType.APPLICATION_XML)).andExpect(status().isNoContent());
	}

}
