package my.photomanager.filter.timeStampFilter;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

class TimeStampFilterTest {

    @Test
    void shouldActivateFilter() {
        var timeStampFilter = TimeStampFilter.builder().build();

        assertThat(timeStampFilter.isActive()).isFalse();
        timeStampFilter.setActive();
        assertThat(timeStampFilter.isActive()).isTrue();
    }

    @Test
    void shouldDeactivateFilter() {
        var timeStampFilter = TimeStampFilter.builder().build();

        assertThat(timeStampFilter.isActive()).isFalse();
        timeStampFilter.setInActive();
        assertThat(timeStampFilter.isActive()).isFalse();
    }

}
