package br.dev.mhc.financialassistant.transaction.mappers;

import br.dev.mhc.financialassistant.category.entities.Category;
import br.dev.mhc.financialassistant.transaction.dtos.TransactionDTO;
import br.dev.mhc.financialassistant.transaction.entities.Transaction;
import br.dev.mhc.financialassistant.user.entities.User;
import br.dev.mhc.financialassistant.wallet.mappers.WalletMapper;

import java.util.stream.Collectors;

public class TransactionMapper {

    public static Transaction toEntity(TransactionDTO dto) {
        var entity = Transaction.builder()
                .id(dto.getId())
                .amount(dto.getAmount())
                .moment(dto.getMoment())
                .notes(dto.getNotes())
                .type(dto.getType().getCod())
                .method(dto.getMethod().getCod())
                .currentInstallment(dto.getCurrentInstallment())
                .active(dto.isActive())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .user(new User(dto.getUserId()))
                .wallet(WalletMapper.toEntity(dto.getWalletId()))
                .build();
        dto.getCategories()
                .forEach(category -> entity.addCategory(new Category(category.getCategoryId()), category.getAmount()));
        return entity;
    }

    public static TransactionDTO toDto(Transaction entity) {
        return TransactionDTO.builder()
                .id(entity.getId())
                .amount(entity.getAmount())
                .moment(entity.getMoment())
                .notes(entity.getNotes())
                .type(entity.getType())
                .method(entity.getMethod())
                .currentInstallment(entity.getCurrentInstallment())
                .active(entity.isActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .userId(entity.getUser().getId())
                .walletId(entity.getWallet().getId())
                .categories(entity.getCategories().stream().map(TransactionCategoryMapper::toDto).collect(Collectors.toSet()))
                .build();
    }
}
