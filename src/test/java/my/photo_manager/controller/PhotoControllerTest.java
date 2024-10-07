package my.photo_manager.controller;

import my.photo_manager.services.PhotoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PhotoController.class)
class PhotoControllerTest {

    @MockBean
    private PhotoService photoService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void canGetAllPhotos() throws Exception {
        mockMvc.perform(get("/photo/getAll"))
                .andExpect(status().isOk());
    }

}