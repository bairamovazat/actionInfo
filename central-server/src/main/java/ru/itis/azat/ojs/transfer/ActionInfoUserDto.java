package ru.itis.azat.ojs.transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActionInfoUserDto {

    private Long id;

    private String email;

    private String orcid;

    private String url;

    private String username;

}
