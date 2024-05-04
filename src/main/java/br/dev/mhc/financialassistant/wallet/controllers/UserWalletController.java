package br.dev.mhc.financialassistant.wallet.controllers;

import br.dev.mhc.financialassistant.common.constants.RouteConstants;
import br.dev.mhc.financialassistant.common.utils.URIUtils;
import br.dev.mhc.financialassistant.wallet.dtos.WalletDTO;
import br.dev.mhc.financialassistant.wallet.services.interfaces.ICreateWalletService;
import br.dev.mhc.financialassistant.wallet.services.interfaces.IFindWalletByIdAndUserIdService;
import br.dev.mhc.financialassistant.wallet.services.interfaces.IFindWalletsByUserIdService;
import br.dev.mhc.financialassistant.wallet.services.interfaces.IUpdateWalletService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = RouteConstants.USERS_ROUTE + "/{userId}" + RouteConstants.WALLETS_ROUTE)
public class UserWalletController {

    private final ICreateWalletService createWallet;
    private final IUpdateWalletService updateWalletService;
    private final IFindWalletsByUserIdService findWalletsByUserService;
    private final IFindWalletByIdAndUserIdService findWalletByIdAndUserIdService;

    public UserWalletController(ICreateWalletService createWallet, IUpdateWalletService updateWalletService, IFindWalletsByUserIdService findWalletsByUserService, IFindWalletByIdAndUserIdService findWalletByIdAndUserIdService) {
        this.createWallet = createWallet;
        this.updateWalletService = updateWalletService;
        this.findWalletsByUserService = findWalletsByUserService;
        this.findWalletByIdAndUserIdService = findWalletByIdAndUserIdService;
    }

    @PostMapping
    ResponseEntity<WalletDTO> create(@RequestBody @Valid WalletDTO walletDTO) {
        walletDTO = createWallet.create(walletDTO);
        return ResponseEntity.created(URIUtils.buildUri(walletDTO.getId())).body(walletDTO);
    }

    @PutMapping(value = "/{id}")
    ResponseEntity<WalletDTO> update(@RequestBody @Valid WalletDTO walletDTO) {
        walletDTO = updateWalletService.update(walletDTO);
        return ResponseEntity.ok(walletDTO);
    }

    @GetMapping
    ResponseEntity<List<WalletDTO>> getByUser(@PathVariable Long userId, @RequestParam(value = "onlyActive", defaultValue = "true") boolean onlyActive) {
        return ResponseEntity.ok(findWalletsByUserService.find(userId, onlyActive));
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<WalletDTO> getById(@PathVariable Long userId, @PathVariable Long id) {
        return ResponseEntity.ok(findWalletByIdAndUserIdService.find(id, userId));
    }
}
