package com.example.solution.service;

import com.example.solution.model.entity.Token;
import com.example.solution.model.json.TokenJson;

import java.util.Optional;

public interface TokenService {

    Optional<Token> getToken(String clientId);

    Token save(TokenJson token);
}
