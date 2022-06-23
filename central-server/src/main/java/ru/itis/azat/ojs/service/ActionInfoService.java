package ru.itis.azat.ojs.service;

import ru.itis.azat.ojs.transfer.ActionInfoDto;

import java.util.List;

public interface ActionInfoService {
    void addAll(List<ActionInfoDto> actionInfoDtoList);
}
