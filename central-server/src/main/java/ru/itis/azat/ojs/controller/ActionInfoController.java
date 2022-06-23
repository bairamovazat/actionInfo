package ru.itis.azat.ojs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.azat.ojs.repository.ActionInfoRepository;
import ru.itis.azat.ojs.model.ActionInfo;
import ru.itis.azat.ojs.service.ActionInfoService;
import ru.itis.azat.ojs.transfer.ActionInfoDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/plugin/api")
public class ActionInfoController {


    @Autowired
    private ActionInfoService actionInfoService;

    @PostMapping(value = "/action-info")
    public ResponseEntity<String> confirmEmail(@RequestBody List<ActionInfoDto> actionInfoDtoList) {

        actionInfoService.addAll(actionInfoDtoList);

        return ResponseEntity.status(201).build();
    }

}
