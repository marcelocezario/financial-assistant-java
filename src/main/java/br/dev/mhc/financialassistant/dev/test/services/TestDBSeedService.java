package br.dev.mhc.financialassistant.dev.test.services;

import br.dev.mhc.financialassistant.category.entities.Category;
import br.dev.mhc.financialassistant.category.repositories.CategoryRepository;
import br.dev.mhc.financialassistant.currency.repositories.CurrencyRepository;
import br.dev.mhc.financialassistant.security.services.interfaces.IEncryptPasswordService;
import br.dev.mhc.financialassistant.user.entities.User;
import br.dev.mhc.financialassistant.user.enums.UserRole;
import br.dev.mhc.financialassistant.user.repositories.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Profile("test")
@Service
public class TestDBSeedService {

    private final IEncryptPasswordService encryptPasswordService;
    private final UserRepository userRepository;
    private final CurrencyRepository currencyRepository;
    private final CategoryRepository categoryRepository;

    public TestDBSeedService(IEncryptPasswordService encryptPasswordService, UserRepository userRepository, CurrencyRepository currencyRepository, CategoryRepository categoryRepository) {
        this.encryptPasswordService = encryptPasswordService;
        this.userRepository = userRepository;
        this.currencyRepository = currencyRepository;
        this.categoryRepository = categoryRepository;
    }

    public void databaseSeeding() {
        List<User> users = new ArrayList<>();

        users.add(User.builder()
                .nickname("Basic")
                .email("basic@mhc.dev.br")
                .password(encryptPasswordService.encrypt("Basic@123"))
                .active(true)
                .role(UserRole.BASIC.getCod())
                .build()
        );
        userRepository.saveAll(users);


        userRepository.findAll().forEach(user -> {
            var categories = Arrays
                    .stream(getCategoryNames())
                    .map(name -> Category.builder().name(name).user(user).active(new Random().nextBoolean()).build())
                    .toList();
            categoryRepository.saveAll(categories);
        });
    }

    private String[] getCategoryNames() {
        return new String[]{"Groceries", "Housing", "Transportation", "Healthcare",
                "Education", "Entertainment", "Clothing and Accessories", "Personal Expenses", "Income",
                "Investments", "Taxes", "Debts and Loans", "Savings", "Gifts and Donations", "Technology",
                "Insurance", "Pets", "Travel", "Financial Services", "Others"};
    }

}
