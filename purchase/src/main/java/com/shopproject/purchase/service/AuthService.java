package com.shopproject.purchase.service;

/**
 * —ервис отвечает за авторизацию
 */
public interface AuthService {
    /**
     * ћетод получает токен использу€ пароль/логин технического пользовател€
     * @return “окен
     */
    String getTokenByTechnicalUser();
}
