package ru.itis.azat.ojs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.azat.ojs.repository.ActionInfoRepository;
import ru.itis.azat.ojs.model.ActionInfo;
import ru.itis.azat.ojs.transfer.ActionInfoDto;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 10.11.2017
 * RegistrationController
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
@RestController
@RequestMapping("/api")

public class ActionInfoController {


    @Autowired
    private ActionInfoRepository actionInfoRepository;

    @PostMapping(value = "/action-info/{journalId}")
    public ResponseEntity<String> confirmEmail(@PathVariable("journalId") String journalId, @RequestBody List<ActionInfoDto> actionInfoDtoList) {

        actionInfoRepository.saveAll(actionInfoDtoList.stream().map(actionInfoDto ->
                ActionInfo.builder()
                        .action_id(actionInfoDto.getId())
                        .type(actionInfoDto.getType())
                        .action(actionInfoDto.getAction())
                        .params(actionInfoDto.getParams())
                        .payload(actionInfoDto.getPayload())
                        .user_id(actionInfoDto.getUser_id())
                        .context_id(actionInfoDto.getContext_id())
                        .date(actionInfoDto.getDate())
                        .build()
        ).collect(Collectors.toList()));

        return ResponseEntity.ok("OK");
    }

}
