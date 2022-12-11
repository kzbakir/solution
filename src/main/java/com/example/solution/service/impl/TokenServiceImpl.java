package com.example.solution.service.impl;

import com.example.solution.model.entity.Token;
import com.example.solution.model.json.TokenJson;
import com.example.solution.repository.TokenRepository;
import com.example.solution.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;

    @Override
    public Optional<Token> getToken(String clientId) {
        return tokenRepository.findByClientId(clientId);
    }

    @Override
    public Token save(TokenJson token) {
        tokenRepository.deleteByClientId(token.getClientId());
        var savedToken = new Token();
        savedToken.setAccessToken(token.getAccessToken());
        savedToken.setRefreshToken(token.getRefreshToken());
        savedToken.setClientId(token.getClientId());
        var calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, token.getExpiresIn().intValue());
        savedToken.setExpiresDate(calendar.getTime());
        return tokenRepository.save(savedToken);
    }
}
