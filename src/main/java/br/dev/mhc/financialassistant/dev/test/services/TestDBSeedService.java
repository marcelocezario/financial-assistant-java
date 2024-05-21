package br.dev.mhc.financialassistant.dev.test.services;

import br.dev.mhc.financialassistant.category.entities.Category;
import br.dev.mhc.financialassistant.category.repositories.CategoryRepository;
import br.dev.mhc.financialassistant.currency.repositories.CurrencyRepository;
import br.dev.mhc.financialassistant.security.services.interfaces.IEncryptPasswordService;
import br.dev.mhc.financialassistant.transaction.entities.Transaction;
import br.dev.mhc.financialassistant.transaction.entities.TransactionCategory;
import br.dev.mhc.financialassistant.transaction.enums.TransactionType;
import br.dev.mhc.financialassistant.transaction.mappers.TransactionMapper;
import br.dev.mhc.financialassistant.transaction.services.interfaces.ICreateTransactionService;
import br.dev.mhc.financialassistant.user.entities.User;
import br.dev.mhc.financialassistant.user.enums.UserRole;
import br.dev.mhc.financialassistant.user.repositories.UserRepository;
import br.dev.mhc.financialassistant.wallet.entities.BankAccount;
import br.dev.mhc.financialassistant.wallet.entities.CashWallet;
import br.dev.mhc.financialassistant.wallet.entities.CreditCard;
import br.dev.mhc.financialassistant.wallet.entities.CryptoWallet;
import br.dev.mhc.financialassistant.wallet.repositories.WalletRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Profile("test")
@Service
public class TestDBSeedService {

    private final IEncryptPasswordService encryptPasswordService;
    private final UserRepository userRepository;
    private final CurrencyRepository currencyRepository;
    private final CategoryRepository categoryRepository;
    private final WalletRepository walletRepository;
    private final ICreateTransactionService createTransactionService;

    public TestDBSeedService(IEncryptPasswordService encryptPasswordService, UserRepository userRepository, CurrencyRepository currencyRepository, CategoryRepository categoryRepository, WalletRepository walletRepository, ICreateTransactionService createTransactionService) {
        this.encryptPasswordService = encryptPasswordService;
        this.userRepository = userRepository;
        this.currencyRepository = currencyRepository;
        this.categoryRepository = categoryRepository;
        this.walletRepository = walletRepository;
        this.createTransactionService = createTransactionService;
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

        var currencyBRL = currencyRepository.findByCodeIgnoreCase("BRL").get();
        var currencyUSD = currencyRepository.findByCodeIgnoreCase("USD").get();
        var currencyBTC = currencyRepository.findByCodeIgnoreCase("BTC").get();
        var currencyJPY = currencyRepository.findByCodeIgnoreCase("JPY").get();
        var currencyCHF = currencyRepository.findByCodeIgnoreCase("CHF").get();

        userRepository.findAll().forEach(user -> {
            var categories = Arrays
                    .stream(getCategoryNames())
                    .map(name -> Category.builder()
                            .name(name)
                            .color(getRandomHexColor())
                            .icon(getRandomIcon())
                            .user(user)
                            .active(new Random().nextBoolean())
                            .build())
                    .toList();
            categoryRepository.saveAll(categories);

            var wallets = Arrays.asList(
                    CashWallet.builder().name("Money").balance(BigDecimal.ZERO).currency(currencyBRL).active(true).user(user).build(),
                    CashWallet.builder().name("Money USD").balance(BigDecimal.ZERO).currency(currencyUSD).active(true).user(user).build(),
                    CashWallet.builder().name("Money JPY").balance(new BigDecimal("500.00")).currency(currencyJPY).active(true).user(user).build(),
                    BankAccount.builder().name("Nubank").balance(BigDecimal.TEN).currency(currencyBRL).creditLimit(new BigDecimal("0.00")).interestRate(new BigDecimal("8.00")).active(true).user(user).build(),
                    BankAccount.builder().name("Inter").balance(new BigDecimal("150.00")).currency(currencyBRL).creditLimit(new BigDecimal("500.00")).interestRate(new BigDecimal("8.00")).active(true).user(user).build(),
                    BankAccount.builder().name("Inter USD").balance(new BigDecimal("500.00")).currency(currencyUSD).creditLimit(new BigDecimal("1000.00")).interestRate(new BigDecimal("8.00")).active(true).user(user).build(),
                    BankAccount.builder().name("Iene").balance(new BigDecimal("500.00")).currency(currencyJPY).creditLimit(new BigDecimal("1000.00")).interestRate(new BigDecimal("8.00")).active(true).user(user).build(),
                    BankAccount.builder().name("Franco Suíço").balance(new BigDecimal("500.00")).currency(currencyCHF).creditLimit(new BigDecimal("1000.00")).interestRate(new BigDecimal("8.00")).active(true).user(user).build(),
                    CreditCard.builder().name("Nubank 1234").balance(new BigDecimal("-460.00")).currency(currencyBRL).creditLimit(new BigDecimal("2000.00")).billingCycleDate(10).dueDate(20).active(true).user(user).build(),
                    CryptoWallet.builder().name("Bitcoin").balance(new BigDecimal("0.15689")).currency(currencyBTC).active(true).user(user).active(true).user(user).build()
            );
            walletRepository.saveAll(wallets);

            wallets.forEach(wallet -> {
                var transaction1 = Transaction.builder()
                        .amount(new BigDecimal("100.00"))
                        .moment(LocalDate.now().atStartOfDay())
                        .notes("Only test transaction")
                        .type(TransactionType.CREDIT.getCod())
                        .currentInstallment(1)
                        .wallet(wallet)
                        .user(user)
                        .method(wallet.getAvailableTransactionMethods().getFirst().getCod())
                        .build();
                transaction1.setCategories(Set.of(TransactionCategory.builder().amount(transaction1.getAmount()).transaction(transaction1).category(categories.getFirst()).build()));
                var transaction1Dto = TransactionMapper.toDto(transaction1);
                createTransactionService.create(transaction1Dto);

                var transaction2 = Transaction.builder()
                        .amount(new BigDecimal("50.00"))
                        .moment(LocalDate.now().atStartOfDay())
                        .notes("Only test transaction")
                        .type(TransactionType.DEBIT.getCod())
                        .currentInstallment(1)
                        .wallet(wallet)
                        .user(user)
                        .method(wallet.getAvailableTransactionMethods().getFirst().getCod())
                        .build();
                transaction2.setCategories(Set.of(TransactionCategory.builder().amount(transaction2.getAmount()).transaction(transaction2).category(categories.getLast()).build()));
                createTransactionService.create(TransactionMapper.toDto(transaction2));
            });
        });
    }

    private String[] getCategoryNames() {
        return new String[]{"Groceries", "Housing", "Transportation", "Healthcare",
                "Education", "Entertainment", "Clothing and Accessories", "Personal Expenses", "Income",
                "Investments", "Taxes", "Debts and Loans", "Savings", "Gifts and Donations", "Technology",
                "Insurance", "Pets", "Travel", "Financial Services", "Others"};
    }

    private String getRandomHexColor() {
        Random random = new Random();
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);
        return String.format("#%02X%02X%02X", red, green, blue);
    }

    private String getRandomIcon() {
        Random random = new Random();
        int number = random.nextInt(10);
        String[] icons = {"home", "apartment", "favorite", "account_balance", "construction", "savings",
                "local_gas_station", "school", "flight", "restaurant_menu"};
        return icons[number];
    }

}
