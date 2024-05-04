package br.dev.mhc.financialassistant.wallet.services.impl;

import br.dev.mhc.financialassistant.exceptions.AppValidationException;
import br.dev.mhc.financialassistant.wallet.dtos.WalletDTO;
import br.dev.mhc.financialassistant.wallet.repositories.WalletRepository;
import br.dev.mhc.financialassistant.wallet.services.interfaces.ICreateWalletService;
import br.dev.mhc.financialassistant.wallet.services.interfaces.IWalletValidatorService;
import org.springframework.stereotype.Service;

import static br.dev.mhc.financialassistant.wallet.mappers.WalletMapper.toDto;
import static br.dev.mhc.financialassistant.wallet.mappers.WalletMapper.toEntity;
import static java.util.Objects.requireNonNull;

@Service
public class CreateWalletServiceImpl implements ICreateWalletService {

    private final WalletRepository repository;
    private final IWalletValidatorService validatorService;

    public CreateWalletServiceImpl(WalletRepository repository, IWalletValidatorService validatorService) {
        this.repository = repository;
        this.validatorService = validatorService;
    }

    @Override
    public WalletDTO create(WalletDTO walletDTO) {
        requireNonNull(walletDTO);
        walletDTO.setId(null);
        walletDTO.setActive(true);
        validatorService.validate(walletDTO).isValidOrThrow(AppValidationException::new);
        var wallet = toEntity(walletDTO);
        wallet = repository.save(wallet);
        return toDto(wallet);
    }

}
