package subway.ui;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import subway.application.PathService;
import subway.dto.LineResponse;
import subway.dto.PathRequest;
import subway.dto.PathResponse;

@RestController
@RequestMapping("/path")
public class PathController {

    private final PathService pathService;

    public PathController(PathService pathService) {
        this.pathService = pathService;
    }

    @GetMapping
    public ResponseEntity<PathResponse> findPath(@RequestBody PathRequest request) {
        return ResponseEntity.ok(pathService.findPath(request));
    }
}
