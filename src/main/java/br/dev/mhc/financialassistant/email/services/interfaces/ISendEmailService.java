package br.dev.mhc.financialassistant.email.services.interfaces;

import br.dev.mhc.financialassistant.email.EmailDTO;

public interface ISendEmailService {

    void send(EmailDTO emailDTO);
}
