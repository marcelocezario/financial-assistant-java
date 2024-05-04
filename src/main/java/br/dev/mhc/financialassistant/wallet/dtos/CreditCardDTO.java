package br.dev.mhc.financialassistant.wallet.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class CreditCardDTO extends WalletDTO {

    private BigDecimal creditLimit;
    private Integer billingCycleDate;
    private Integer dueDate;

}
