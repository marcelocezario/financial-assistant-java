package br.dev.mhc.financialassistant.wallet.services.impl;

import br.dev.mhc.financialassistant.category.entities.Category;
import br.dev.mhc.financialassistant.exceptions.ResourceNotFoundException;
import br.dev.mhc.financialassistant.wallet.repositories.WalletRepository;
import br.dev.mhc.financialassistant.wallet.services.interfaces.IDeleteWalletService;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

@Service
public class DeleteWalletServiceImpl implements IDeleteWalletService {

    private final WalletRepository repository;

    public DeleteWalletServiceImpl(WalletRepository repository) {
        this.repository = repository;
    }

    @Override
    public void delete(UUID id, UUID userId) {
        requireNonNull(id);
        requireNonNull(userId);
        var wallet = repository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException(id, Category.class));
        repository.delete(wallet);
    }
}
