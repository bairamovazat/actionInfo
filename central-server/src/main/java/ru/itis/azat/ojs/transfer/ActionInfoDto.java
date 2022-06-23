package ru.itis.azat.ojs.transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.azat.ojs.model.ActionInfoContext;
import ru.itis.azat.ojs.model.ActionInfoUser;
import ru.itis.azat.ojs.model.User;

import javax.persistence.*;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActionInfoDto {

    private Long entityId;

    private Long id;

    private String type;

    private String action;

    private Long actionId;

    private String params;

    private String payload;

    private Long date;

    private ActionInfoUserDto user;

    private ActionInfoContextDto context;

}
