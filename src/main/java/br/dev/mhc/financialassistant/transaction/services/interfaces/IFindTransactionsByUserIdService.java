package br.dev.mhc.financialassistant.transaction.services.interfaces;

import br.dev.mhc.financialassistant.transaction.dtos.TransactionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IFindTransactionsByUserIdService {

    Page<TransactionDTO> findPageable(Long userId, Pageable pageable);
}
