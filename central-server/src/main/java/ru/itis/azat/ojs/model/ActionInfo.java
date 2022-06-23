package ru.itis.azat.ojs.model;


import lombok.*;
import ru.itis.azat.ojs.transfer.ActionInfoDto;

import javax.persistence.*;

@Entity
@Table(name = "action_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class ActionInfo {


//		<db-attribute name="context_id" type="BIGINT"/>
//
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ojsId;

    private String type;

    private String action;

    private String params;

    @Column(columnDefinition="varchar")
    private String payload;

    private Long date;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "user_id")
    private ActionInfoUser user;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "context_id")
    private ActionInfoContext context;

    public void update(ActionInfoDto actionInfoDto) {
        this.ojsId = actionInfoDto.getId();
        this.type = actionInfoDto.getType();
        this.action = actionInfoDto.getAction();
        this.params = actionInfoDto.getParams();
        this.date = actionInfoDto.getDate();
        this.payload = actionInfoDto.getPayload();
    }

}
