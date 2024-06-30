package br.dev.mhc.financialassistant.transaction.mappers;

import br.dev.mhc.financialassistant.transaction.dtos.TransactionDTO;
import br.dev.mhc.financialassistant.transaction.entities.Transaction;
import br.dev.mhc.financialassistant.transaction.entities.TransactionParent;
import br.dev.mhc.financialassistant.user.entities.User;
import br.dev.mhc.financialassistant.wallet.mappers.WalletMapper;

import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class TransactionMapper {

    public static Transaction toEntity(TransactionDTO dto) {
        var entity = Transaction.builder()
                .id(dto.getId())
                .amount(dto.getAmount())
                .dueDate(dto.getDueDate())
                .paymentMoment(dto.getPaymentMoment())
                .notes(dto.getNotes())
                .type(dto.getType().getCod())
                .method(dto.getMethod().getCod())
                .currentInstallment(dto.getCurrentInstallment())
                .active(dto.isActive())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .user(new User(dto.getUserId()))
                .wallet(WalletMapper.toEntity(dto.getWallet()))
                .parent(isNull(dto.getParent()) ? null : TransactionParentMapper.toEntity(dto.getParent()))
                .build();
        entity.setCategories(dto.getCategories().stream().map(TransactionCategoryMapper::toEntity).collect(Collectors.toSet()));
        return entity;
    }

    public static TransactionDTO toDto(Transaction entity) {
        return TransactionDTO.builder()
                .id(entity.getId())
                .amount(entity.getAmount())
                .dueDate(entity.getDueDate())
                .paymentMoment(entity.getPaymentMoment())
                .notes(entity.getNotes())
                .type(entity.getType())
                .method(entity.getMethod())
                .currentInstallment(entity.getCurrentInstallment())
                .active(entity.isActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .userId(entity.getUser().getId())
                .wallet(WalletMapper.toDto(entity.getWallet()))
                .parent(TransactionParentMapper.toDto(entity.getParent()))
                .categories(entity.getCategories().stream().map(TransactionCategoryMapper::toDto).collect(Collectors.toSet()))
                .build();
    }
}
