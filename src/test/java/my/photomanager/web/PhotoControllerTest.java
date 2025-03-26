package my.photomanager.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.google.common.collect.Lists;
import my.photomanager.filter.FilterService;
import my.photomanager.photo.DefaultPhotoFactory;
import my.photomanager.photo.Photo;
import my.photomanager.service.PhotoService;

@WebMvcTest(PhotoController.class)
class PhotoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PhotoService photoService;

    @MockitoBean
    private DefaultPhotoFactory photoFactory;

    @MockitoBean
    private FilterService filterService;

    @Test
    void shouldAcceptGetRequestWithoutParameters() throws Exception {
        when(photoService.getPhotos(anyInt(), anyInt())).thenReturn(Lists.newArrayList());

        mockMvc.perform(get("/photos").content("application/json")).andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(strings = {"page=1", "size=10", "labels=label1,label2", "page=1&size=10",
            "page=1&labels=label1,label2", "size=10&labels=label1,label2"})
    void shouldAcceptGetRequestWithParameters(String parameters) throws Exception {
        when(photoService.getPhotos(anyInt(), anyInt())).thenReturn(Lists.newArrayList());

        mockMvc.perform(get("/photos" + "?" + parameters).content("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldAcceptSaveRequest() throws Exception {
        when(photoService.savePhoto(any(Photo.class))).thenReturn(mock(Photo.class));

        mockMvc.perform(put("/save?filePath=Testfile.jpg").content("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldAcceptDeleteRequest() throws Exception {
        mockMvc.perform(delete("/delete?id=1").content("application/json"))
                .andExpect(status().isOk());
    }

}
