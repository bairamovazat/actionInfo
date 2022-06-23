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
public class ActionInfoContextDto {

    private Long entityId;

    private Long id;

    private String urlPath;

    private Boolean enabled;

    private String primaryLocale;

    private Map<String, String> acronym;

    private Map<String, String> description;

    private Map<String, String> librarianInformation;

    private Map<String, String> name;

}