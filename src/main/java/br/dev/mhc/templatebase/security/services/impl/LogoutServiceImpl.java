package br.dev.mhc.templatebase.security.services.impl;

import br.dev.mhc.templatebase.common.logs.LogHelper;
import br.dev.mhc.templatebase.security.LogoutRequest;
import br.dev.mhc.templatebase.security.LogoutRequestRepository;
import br.dev.mhc.templatebase.security.services.interfaces.ILogoutService;
import br.dev.mhc.templatebase.security.utils.SecurityUtils;
import org.springframework.stereotype.Service;

@Service
public class LogoutServiceImpl implements ILogoutService {

    private final LogHelper LOG = new LogHelper(LogoutServiceImpl.class);

    private final LogoutRequestRepository logoutRequestRepository;

    public LogoutServiceImpl(LogoutRequestRepository logoutRequestRepository) {
        this.logoutRequestRepository = logoutRequestRepository;
    }

    @Override
    public void logout() {
        var username = SecurityUtils.getUsernameAuthenticatedUser();
        var logoutRequest = new LogoutRequest(username);
        logoutRequestRepository.save(logoutRequest);
    }
}
