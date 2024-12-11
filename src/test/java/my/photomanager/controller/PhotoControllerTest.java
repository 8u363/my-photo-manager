package my.photomanager.controller;

import static my.photomanager.TestConstants.INTEGRATION_TEST;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import lombok.extern.log4j.Log4j2;
import my.photomanager.service.photo.PhotoService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PhotoController.class)
@Tag(INTEGRATION_TEST)
@Log4j2
class PhotoControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockitoBean
	private PhotoService photoService;

	@BeforeAll
	static void startTest() {
		log.info("start {}", PhotoControllerTest.class.getSimpleName());
	}

	@AfterAll
	static void finishTest() {
		log.info("finish {}", PhotoControllerTest.class.getSimpleName());
	}

	@Nested
	class ControllerGetterTests {

		private final String GETTER_ENDPOINT = "/photos";

		@Test
		void should_accept_request_without_parameters() throws Exception {
			// given
			when(photoService.getPhotos(anyInt(), anyInt(), anyList())).thenReturn(Page.empty());

			// when and then
			mockMvc.perform(get(GETTER_ENDPOINT)
							.content("application/json"))
					.andExpect(status().isOk());
		}

		@ParameterizedTest
		@ValueSource(strings = {"page=1", "size=10", "labels=label1,label2", "page=1&size=10", "page=1&labels=label1,label2", "size=10&labels=label1,label2"})
		void should_accept_request_with_parameters(String parameters) throws Exception {
			// given
			when(photoService.getPhotos(anyInt(), anyInt(), anyList())).thenReturn(Page.empty());

			// when and then
			mockMvc.perform(get(GETTER_ENDPOINT + "?" + parameters)
							.content("application/json"))
					.andExpect(status().isOk());
		}

		@Test
		@Disabled
		void should_return_photo_dto_by_id() {
			// TODO Not yet implemented
		}
	}

	@Nested
	class ControllerUpdateTests {

		@Test
		@Disabled
		void test_update_photo_by_id() {
			throw new UnsupportedOperationException("Not yet implemented");
		}

		@Test
		@Disabled
		void test_update_photo_by_unknown_id() {
			throw new UnsupportedOperationException("Not yet implemented");
		}
	}

	@Nested
	class ControllerDeleteTests {

		@Test
		@Disabled
		void test_delete_photo_by_id() {
			throw new UnsupportedOperationException("Not yet implemented");
		}

		@Test
		@Disabled
		void test_delete_photo_by_unknown_id() {
			throw new UnsupportedOperationException("Not yet implemented");
		}
	}
}