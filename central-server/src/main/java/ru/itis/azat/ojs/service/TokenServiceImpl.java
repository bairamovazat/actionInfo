package ru.itis.azat.ojs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.azat.ojs.forms.TokenForm;
import ru.itis.azat.ojs.model.Token;
import ru.itis.azat.ojs.model.User;
import ru.itis.azat.ojs.repository.TokenRepository;
import ru.itis.azat.ojs.transfer.TokenDto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private EmailService emailService;
    @Autowired
    private AuthenticationService authenticationService;

    private String emailMessage = "Здравствуйте! Токен создан. Имя: %s, токен: %s";

    @Override
    public Token createNewToken(String tokenName) {
        User user = authenticationService.getCurrentUser();
        if (user == null) {
            return null;
        }
        Token token = Token.builder()
                .name(tokenName)
                .value(getUUID())
                .user(user)
                .build();
        token = tokenRepository.save(token);

        emailService.sendMailAsync(String.format(emailMessage, token.getName(), token.getValue()), "Создание токена", user.getEmail());

        return token;
    }

    @Override
    public Token createNewToken(TokenForm tokenForm) {
        return createNewToken(tokenForm.getName());
    }

    @Override
    public List<TokenDto> getTokenListForCurrentUser() {
        return tokenRepository.findAllByUser(authenticationService.getCurrentUser())
                .stream()
                .map(TokenDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteToken(TokenDto tokenDto) {
        Token token = tokenRepository.findById(tokenDto.getId()).orElse(null);
        if (token != null && token.getUser().getId().equals(authenticationService.getCurrentUser().getId())) {
            tokenRepository.deleteById(token.getId());
        }
    }

    public String getUUID() {
        return UUID.randomUUID().toString();
    }
}
