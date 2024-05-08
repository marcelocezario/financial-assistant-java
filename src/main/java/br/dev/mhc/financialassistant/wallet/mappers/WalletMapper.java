package br.dev.mhc.financialassistant.wallet.mappers;

import br.dev.mhc.financialassistant.common.logs.LogHelper;
import br.dev.mhc.financialassistant.currency.mappers.CurrencyMapper;
import br.dev.mhc.financialassistant.user.entities.User;
import br.dev.mhc.financialassistant.wallet.dtos.*;
import br.dev.mhc.financialassistant.wallet.dtos.WalletDTO.WalletDTOBuilder;
import br.dev.mhc.financialassistant.wallet.entities.*;
import br.dev.mhc.financialassistant.wallet.entities.Wallet.WalletBuilder;

import java.util.UUID;

public class WalletMapper {

    private final static LogHelper LOG = new LogHelper(WalletMapper.class);

    // TODO verificar se mesmo Wallets que não são CASH_WALLET vão gerar essa instância no banco
    public static Wallet toEntity(UUID walletId) {
        return CashWallet.builder().id(walletId).build();
    }

    public static Wallet toEntity(WalletDTO dto) {
        WalletBuilder builder;
        switch (dto.getType()) {
            case CASH_WALLET -> builder = toEntityCashWalletBuilder();
            case BANK_ACCOUNT -> builder = toEntityBankAccountBuilder((BankAccountDTO) dto);
            case CREDIT_CARD -> builder = toEntityCreditCardBuilder((CreditCardDTO) dto);
            case CRYPTO_WALLET -> builder = toEntityCryptoWallet();
            default -> {
                LOG.error("Handler 'toEntity' not found for wallet type: [" + dto.getType() + "]");
                builder = toEntityCashWalletBuilder();
            }
        }
        builder.id(dto.getId());
        builder.name(dto.getName());
        builder.balance(dto.getBalance());
        builder.active(dto.isActive());
        builder.createdAt(dto.getCreatedAt());
        builder.updatedAt(dto.getUpdatedAt());
        builder.currency(CurrencyMapper.toEntity(dto.getCurrency()));
        builder.user(User.builder().id(dto.getUserId()).build());
        return builder.build();
    }

    public static WalletDTO toDto(Wallet entity) {
        WalletDTOBuilder builder;
        switch (entity.getType()) {
            case CASH_WALLET -> builder = toDtoCashWalletBuilder((CashWallet) entity);
            case BANK_ACCOUNT -> builder = toDtoBankAccountBuilder((BankAccount) entity);
            case CREDIT_CARD -> builder = toDtoCreditCardBuilder((CreditCard) entity);
            case CRYPTO_WALLET -> builder = toDtoCryptoWallet((CryptoWallet) entity);
            default -> {
                LOG.error("Handler 'toDto' not found for wallet type: [" + entity.getType() + "]");
                builder = toDtoCashWalletBuilder((CashWallet) entity);
            }
        }
        builder.id(entity.getId());
        builder.name(entity.getName());
        builder.balance(entity.getBalance());
        builder.active(entity.isActive());
        builder.createdAt(entity.getCreatedAt());
        builder.updatedAt(entity.getUpdatedAt());
        builder.currency(CurrencyMapper.toDto(entity.getCurrency()));
        builder.userId(entity.getUser().getId());
        return builder.build();
    }

    private static WalletBuilder toEntityCryptoWallet() {
        return CryptoWallet.builder();
    }

    private static WalletBuilder toEntityCreditCardBuilder(CreditCardDTO dto) {
        return CreditCard.builder()
                .creditLimit(dto.getCreditLimit())
                .billingCycleDate(dto.getBillingCycleDate())
                .dueDate(dto.getDueDate());
    }

    private static WalletBuilder toEntityBankAccountBuilder(BankAccountDTO dto) {
        return BankAccount.builder()
                .creditLimit(dto.getCreditLimit())
                .interestRate(dto.getInterestRate());
    }

    private static WalletBuilder toEntityCashWalletBuilder() {
        return CashWallet.builder();
    }

    private static WalletDTOBuilder toDtoCryptoWallet(CryptoWallet entity) {
        return CryptoWalletDTO.builder();
    }

    private static WalletDTOBuilder toDtoCreditCardBuilder(CreditCard entity) {
        return CreditCardDTO.builder()
                .creditLimit(entity.getCreditLimit())
                .billingCycleDate(entity.getBillingCycleDate())
                .dueDate(entity.getDueDate());
    }

    private static WalletDTOBuilder toDtoBankAccountBuilder(BankAccount entity) {
        return BankAccountDTO.builder()
                .creditLimit(entity.getCreditLimit())
                .interestRate(entity.getInterestRate());
    }

    private static WalletDTOBuilder toDtoCashWalletBuilder(CashWallet entity) {
        return CashWalletDTO.builder();
    }

}
