package subway.domain;

import java.util.Objects;

public class Station {

    public static final Station emptyStation = new Station("");
    private Long id;
    private String name;
    private Station next = emptyStation;
    private Distance distance;

    public Station() {
    }

    public Station(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Station(String name) {
        this.name = name;
    }

    public Station(Long id, String name, Station next, Distance distance) {
        validateSameStations(name, next);
        this.id = id;
        this.name = name;
        this.next = next;
        this.distance = distance;
    }

    public Station(String name, Station next, Distance distance) {
        validateSameStations(name, next);
        this.name = name;
        this.next = next;
        this.distance = distance;
    }

    public boolean isDownEndStation() {
        if (next.equals(Station.emptyStation)) {
            return true;
        }
        return false;
    }

    private void validateSameStations(String name, Station next) {
        if (next.name.equals(name)) {
            throw new IllegalArgumentException("상행역과 하행역은 같은 이름을 가질 수 없습니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Station getNext() {
        return next;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return Objects.equals(name, station.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
