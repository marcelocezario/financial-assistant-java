package br.dev.mhc.templatebase.security.services.interfaces;

import br.dev.mhc.templatebase.security.dtos.ForgotPasswordRequestDTO;

public interface IForgotPasswordService {

    void forgotPassword(ForgotPasswordRequestDTO forgotPasswordRequestDTO);
}
