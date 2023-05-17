package subway.dto;

public class LineRequest {

    private String name;
    private String color;
    private String upStation;
    private String downStation;
    private int distance;

    public LineRequest() {
    }

    public LineRequest(String name, String color, String upStation, String downStation,
        int distance) {
        this.name = name;
        this.color = color;
        this.upStation = upStation;
        this.downStation = downStation;
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public String getUpStation() {
        return upStation;
    }

    public String getDownStation() {
        return downStation;
    }

    public int getDistance() {
        return distance;
    }
}
