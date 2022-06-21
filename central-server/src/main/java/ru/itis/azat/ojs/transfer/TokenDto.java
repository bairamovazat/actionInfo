package ru.itis.azat.ojs.transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.azat.ojs.model.Token;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenDto {

    private Long id;

    private String name;

    private String value;

    public static TokenDto from(Token token) {
        return TokenDto.builder()
                .id(token.getId())
                .name(token.getName())
                .value(token.getValue())
                .build();
    }
}
