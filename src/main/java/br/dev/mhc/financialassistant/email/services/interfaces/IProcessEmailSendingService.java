package br.dev.mhc.financialassistant.email.services.interfaces;

import br.dev.mhc.financialassistant.email.EmailDTO;

public interface IProcessEmailSendingService {

    void process(EmailDTO emailDTO);
}
