package mvc.spring.restmvc.web;

import lombok.extern.slf4j.Slf4j;
import mvc.spring.restmvc.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/games")
@Slf4j
public class GameController {

    @Autowired
    private GameService service;
}
