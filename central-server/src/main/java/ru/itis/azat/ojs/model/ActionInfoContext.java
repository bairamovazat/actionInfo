package ru.itis.azat.ojs.model;

import lombok.*;
import ru.itis.azat.ojs.transfer.ActionInfoContextDto;

import javax.persistence.*;

@Entity
@Table(name = "action_info_context")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class ActionInfoContext {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ojsId;

    private String urlPath;

    private Boolean enabled;

    private String primaryLocale;

    @Column(columnDefinition="varchar")
    private String acronym;

    @Column(columnDefinition="varchar")
    private String description;

    @Column(columnDefinition="varchar")
    private String librarianInformation;

    private String name;

    public void update(ActionInfoContextDto context) {
        this.ojsId = context.getId();
        this.urlPath = context.getUrlPath();
        this.enabled = context.getEnabled();
        this.primaryLocale = context.getPrimaryLocale();
        this.acronym = context.getAcronym().get(this.primaryLocale);
        this.description = context.getDescription().get(this.primaryLocale);
        this.librarianInformation = context.getLibrarianInformation().get(this.primaryLocale);
        this.name = context.getName().get(this.primaryLocale);
    }

}
