package br.dev.mhc.templatebase.email.services.interfaces;

import br.dev.mhc.templatebase.email.EmailDTO;

public interface IProcessEmailSendingService {

    void process(EmailDTO emailDTO);
}
