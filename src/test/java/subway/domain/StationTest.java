package subway.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StationTest {

    @Test
    @DisplayName("양 역의 이름이 같다면 예외를 발생시킨다.")
    void validateSameStations_fail() {
        //given
        String name = "상행역";
        Distance distance = new Distance(10);
        Station next = new Station(name);


        //when, then
        assertThatThrownBy(() -> new Station(name, next, distance))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상행역과 하행역은 같은 이름을 가질 수 없습니다.");
    }

    @Test
    @DisplayName("올바른 값이 입력되면 정상적으로 생성된다")
    void constructor_success() {
        //given
        String name = "상행역";
        Distance distance = new Distance(10);
        Station next = new Station("하행역");


        //when, then
        assertThatCode(() -> new Station(name, next, distance))
                .doesNotThrowAnyException();
    }
}
