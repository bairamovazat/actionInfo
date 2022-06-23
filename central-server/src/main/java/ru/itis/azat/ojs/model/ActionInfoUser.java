package ru.itis.azat.ojs.model;


import lombok.*;
import ru.itis.azat.ojs.transfer.ActionInfoUserDto;

import javax.persistence.*;

@Entity
@Table(name = "action_info_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class ActionInfoUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ojsId;

    private String email;

    private String orcid;

    private String url;

    private String username;

    public void update(ActionInfoUserDto actionInfoUserDto) {
        this.ojsId = actionInfoUserDto.getId();
        this.email = actionInfoUserDto.getEmail();
        this.orcid = actionInfoUserDto.getOrcid();
        this.url = actionInfoUserDto.getUrl();
        this.username = actionInfoUserDto.getUsername();
    }

}
