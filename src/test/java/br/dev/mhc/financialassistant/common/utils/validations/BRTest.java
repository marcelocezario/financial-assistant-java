package br.dev.mhc.financialassistant.common.utils.validations;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BRTest {

    @Test
    public void isValidCPF_shouldValidateIfCPFIsValid() {
        var validCPFs = Arrays.asList("87555226022", "57778469049", "02369408057", "83715466022");
        validCPFs.forEach(cpf -> assertTrue(BR.isValidCPF(cpf)));

        var invalidCPFs = Arrays.asList("87555226023", "57778469040", "02369", "", null);
        invalidCPFs.forEach(cpf -> assertFalse(BR.isValidCPF(cpf)));
    }

    @Test
    public void isValidCNPJ_shouldValidateIfCNPJIsValid() {
        var validCNPJs = Arrays.asList("60735922000179", "96702738000170", "02826120000178", "14074404000134");
        validCNPJs.forEach(cnpj -> assertTrue(BR.isValidCNPJ(cnpj)));

        var invalidCNPJs = Arrays.asList("60735922000170", "96702738000171", "028261", "", null);
        invalidCNPJs.forEach(cnpj -> assertFalse(BR.isValidCNPJ(cnpj)));
    }
}
