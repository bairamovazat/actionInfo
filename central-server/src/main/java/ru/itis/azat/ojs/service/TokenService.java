package ru.itis.azat.ojs.service;

import ru.itis.azat.ojs.forms.TokenForm;
import ru.itis.azat.ojs.model.Token;
import ru.itis.azat.ojs.transfer.TokenDto;

import java.util.List;

public interface TokenService {
    Token createNewToken(String name);

    Token createNewToken(TokenForm tokenForm);

    List<TokenDto> getTokenListForCurrentUser();

    void deleteToken(TokenDto tokenDto);
}
