package br.dev.mhc.financialassistant.security.services.interfaces;

import br.dev.mhc.financialassistant.security.dtos.ForgotPasswordRequestDTO;

public interface IForgotPasswordService {

    void forgotPassword(ForgotPasswordRequestDTO forgotPasswordRequestDTO);
}
