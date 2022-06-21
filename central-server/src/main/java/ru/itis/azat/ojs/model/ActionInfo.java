package ru.itis.azat.ojs.model;


import lombok.*;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private String action;

    private Long action_id;

    private String params;

    @Lob
    private String payload;

    private Long date;

    private Long user_id;

    private Long context_id;

}
