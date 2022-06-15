package ru.itis.azat.ojs.transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActionInfoDto {
    private Long id;

    private String type;

    private String action;

    private String params;

    private String payload;

    private Long date;

    private Long user_id;

    private Map<String, Object> user;

    private Long context_id;

    private Map<String, Object> context;

}
