package my.photomanager.v1;

import static my.photomanager.TestConstants.INTEGRATION_TEST;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import lombok.extern.log4j.Log4j2;
import my.photomanager.controller.PhotoConfigurationController;
import my.photomanager.service.configuration.ConfigurationService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PhotoConfigurationController.class)
@Tag(INTEGRATION_TEST)
@Log4j2
class PhotoConfigurationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private ConfigurationService configurationService;

	@BeforeAll
	static void startTest() {
		log.info("start {}", PhotoConfigurationControllerTest.class.getSimpleName());
	}

	@AfterAll
	static void finishTest() {
		log.info("finish {}", PhotoConfigurationControllerTest.class.getSimpleName());
	}

	@Test
	void should_accept_get_request() throws Exception {
		// when and then
		mockMvc.perform(get("/getAll")
						.content("application/json"))
				.andExpect(status().isOk());
	}

	@Test
	void should_accept_put_request() throws Exception {
		// when and then
		mockMvc.perform(put("/update?ID=1")
						.content("application/json"))
				.andExpect(status().isOk());
	}

	@Test
	void should_throw_exception_when_put_request_contains_invalid_id() throws Exception {
		// when and then
		mockMvc.perform(put("/update?ID=1")
						.content("application/json"))
				.andExpect(status().isInternalServerError());
	}

	@Test
	void should_accept_delete_request() throws Exception {
		// when and then
		mockMvc.perform(delete("/delete?ID=1")
						.content("application/json"))
				.andExpect(status().isOk());
	}

	@Test
	void should_throw_exception_when_delete_request_contains_invalid_id() throws Exception {
		// when and then
		mockMvc.perform(put("/delete?ID=1")
						.content("application/json"))
				.andExpect(status().isInternalServerError());
	}
}