package subway.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import subway.dao.LineDao;
import subway.dao.StationDao;
import subway.dto.LineRequest;
import subway.dto.LineResponse;
import subway.entity.LineEntity;

import java.util.List;
import java.util.stream.Collectors;
import subway.entity.StationEntity;

@Transactional
@Service
public class LineService {

    public static final int MIN_DISTANCE_VALUE = 1;
    private final StationDao stationDao;
    private final LineDao lineDao;

    public LineService(StationDao stationDao, LineDao lineDao) {
        this.stationDao = stationDao;
        this.lineDao = lineDao;
    }

    public Long saveLine(LineRequest request) {
        validateNewLine(request);

        LineEntity newLine = new LineEntity(request.getName(), request.getColor(), null);
        Long newLineId = lineDao.insert(newLine);
        Long upEndStationId = insertEndStations(request, newLineId);
        lineDao.updateHeadStation(newLineId, upEndStationId);
        return newLineId;
    }

    private void validateNewLine(LineRequest request) {
        validateExistLine(request);
        validateSameStationName(request);
        validatePositiveDistance(request);
    }

    private void validateExistLine(LineRequest request) {
        if(lineDao.isExist(request.getName())){
            throw new IllegalArgumentException("이미 같은 이름의 노선이 존재합니다");
        }
    }

    private static void validateSameStationName(LineRequest request) {
        if(request.getUpStation().equals(request.getDownStation())){
            throw new IllegalArgumentException("상행역과 하행역은 같은 이름을 가질 수 없습니다.");
        }
    }

    private static void validatePositiveDistance(LineRequest request) {
        if(request.getDistance()< MIN_DISTANCE_VALUE){
            throw new IllegalArgumentException("거리는 양의 정수여야 합니다");
        }
    }

    private Long insertEndStations(LineRequest request, Long newLineId) {
        StationEntity downEndStation = new StationEntity(request.getDownStation(), 0L, null,
            newLineId);
        Long downEndStationId = stationDao.insert(downEndStation);

        StationEntity upEndStation = new StationEntity(request.getUpStation(), downEndStationId,
            request.getDistance(), newLineId);
        return stationDao.insert(upEndStation);
    }
    public List<LineResponse> findLineResponses() {
        List<LineEntity> persistLines = lineDao.findAll();
        return persistLines.stream()
            .map(LineResponse::of)
            .collect(Collectors.toList());
    }

    public LineResponse findLineResponseById(Long id) {
        LineEntity persistLine = lineDao.findById(id);
        return LineResponse.of(persistLine);
    }

//    public void updateLine(Long id, LineRequest lineUpdateRequest) {
//        lineDao.update(new Line(id, lineUpdateRequest.getName(), lineUpdateRequest.getColor()));
//    }

    public void deleteLineById(Long id) {
        stationDao.deleteByLineId(id);
        lineDao.deleteById(id);
    }

}
