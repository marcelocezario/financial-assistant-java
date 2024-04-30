package br.dev.mhc.financialassistant.security.services.impl;

import br.dev.mhc.financialassistant.common.logs.LogHelper;
import br.dev.mhc.financialassistant.security.entities.LogoutRequest;
import br.dev.mhc.financialassistant.security.repositories.LogoutRequestRepository;
import br.dev.mhc.financialassistant.security.services.interfaces.ILogoutService;
import br.dev.mhc.financialassistant.security.utils.SecurityUtils;
import org.springframework.stereotype.Service;

@Service
public class LogoutServiceImpl implements ILogoutService {

    private static final LogHelper LOG = new LogHelper(LogoutServiceImpl.class);

    private final LogoutRequestRepository logoutRequestRepository;

    public LogoutServiceImpl(LogoutRequestRepository logoutRequestRepository) {
        this.logoutRequestRepository = logoutRequestRepository;
    }

    @Override
    public void logout() {
        var username = SecurityUtils.getUsernameAuthenticatedUser();
        LOG.debug("Logout requested", username);
        var logoutRequest = new LogoutRequest(username);
        logoutRequestRepository.save(logoutRequest);
    }
}
