package br.dev.mhc.financialassistant.dev.test.services;

import br.dev.mhc.financialassistant.category.entities.Category;
import br.dev.mhc.financialassistant.category.repositories.CategoryRepository;
import br.dev.mhc.financialassistant.common.enums.ClassificationType;
import br.dev.mhc.financialassistant.common.utils.TestUtils;
import br.dev.mhc.financialassistant.common.utils.Utils;
import br.dev.mhc.financialassistant.currency.repositories.CurrencyRepository;
import br.dev.mhc.financialassistant.featuresrequests.entities.FeatureRequest;
import br.dev.mhc.financialassistant.featuresrequests.repositories.FeatureRequestRepository;
import br.dev.mhc.financialassistant.transaction.entities.Transaction;
import br.dev.mhc.financialassistant.transaction.entities.TransactionCategory;
import br.dev.mhc.financialassistant.transaction.entities.TransactionParent;
import br.dev.mhc.financialassistant.transaction.enums.TransactionMethod;
import br.dev.mhc.financialassistant.transaction.repositories.TransactionParentRepository;
import br.dev.mhc.financialassistant.transaction.repositories.TransactionRepository;
import br.dev.mhc.financialassistant.user.entities.User;
import br.dev.mhc.financialassistant.user.repositories.UserRepository;
import br.dev.mhc.financialassistant.wallet.entities.*;
import br.dev.mhc.financialassistant.wallet.enums.WalletType;
import br.dev.mhc.financialassistant.wallet.repositories.WalletRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Profile("test")
@Service
public class TestDBSeedService {

    private final UserRepository userRepository;
    private final CurrencyRepository currencyRepository;
    private final CategoryRepository categoryRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionParentRepository transactionParentRepository;
    private final FeatureRequestRepository featureRequestRepository;

    public TestDBSeedService(UserRepository userRepository, CurrencyRepository currencyRepository, CategoryRepository categoryRepository, WalletRepository walletRepository, TransactionRepository transactionRepository, TransactionParentRepository transactionParentRepository, FeatureRequestRepository featureRequestRepository) {
        this.userRepository = userRepository;
        this.currencyRepository = currencyRepository;
        this.categoryRepository = categoryRepository;
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
        this.transactionParentRepository = transactionParentRepository;
        this.featureRequestRepository = featureRequestRepository;
    }

    public void databaseSeeding() {
        userRepository.findAll().forEach(this::seedUserData);
        seedFeatureRequestsData();
    }

    private void seedFeatureRequestsData() {
        List<FeatureRequest> featureRequests = new ArrayList<>();
        var request1 = FeatureRequest.builder()
                .description("Habilitar envio de e-mail")
                .rating(5)
                .developed(false)
                .approved(false)
                .active(false)
                .build();
        request1.addTranslation(
                "pt-br",
                "Habilitar envio de e-mail",
                "Habilitar envio de e-mail para criação de usuário e recuperação de senha"
        );
        request1.addTranslation(
                "en-us",
                "Enable email sending",
                "Enable email sending for user creation and password recovery"
        );
        featureRequests.add(request1);

        var request2 = FeatureRequest.builder()
                .description("Teste de aprovação")
                .rating(0)
                .developed(false)
                .approved(false)
                .active(true)
                .build();
        request2.addTranslation("pt-br", "Teste aprovação", "Testando aprovação");
        request2.addTranslation("en-us", "Testing approval", "Testing approval");
        featureRequests.add(request2);

        featureRequests = featureRequestRepository.saveAll(featureRequests);
    }

    private void seedUserData(User user) {
        var categories = categoryRepository.saveAll(getCategoryList(user));
        List<Wallet> wallets = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            wallets.add(getRandomWallet(user));
        }
        wallets = walletRepository.saveAll(wallets).stream().filter(Wallet::isActive).toList();
        List<Transaction> transactions = new ArrayList<>();
        for (Wallet wallet : wallets) {
            for (int i = 0; i < 20; i++) {
                var parent = transactionParentRepository.save(getRandomTransactionParent());
                var randomTransactions = getRandomTransactions(wallet, categories, parent);
                transactions.addAll(randomTransactions);
            }
        }
        transactions = transactionRepository.saveAll(transactions);
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
        String[] icons = {"home", "restaurant", "pets", "electric_bolt", "checkroom", "water_drop", "school",
                "directions_bike", "directions_bus", "directions_car", "flight", "clean_hands", "photo_camera",
                "headphones", "audiotrack", "power", "sports_esports", "health_and_safety", "volunteer_activism",
                "celebration", "sports_bar", "local_bar", "favorite", "shopping_cart", "menu_book", "redeem",
                "self_improvement", "stroller", "child_care", "savings", "account_balance", "credit_card", "money",
                "sports_motorsports", "grade", "key", "rocket_launch", "recycling", "interests", "cell_tower",
                "credit_card", "payments", "handyman", "factory", "fastfood", "construction", "sports_score",
                "smartphone", "computer", "balance", "apartment", "icecream", "family_restroom", "cast",
                "ondemand_video"};
        final int index = TestUtils.generateRandomInteger(0, icons.length - 1);
        return icons[index];
    }

    private List<Category> getCategoryList(User user) {
        return Arrays.stream(getCategoryNames()).map(name -> {
            var classificationType = ClassificationType.toEnum(TestUtils.generateRandomInteger(1, ClassificationType.values().length));
            return Category.builder()
                    .name(name + " " + classificationType.name().charAt(0))
                    .color(getRandomHexColor())
                    .icon(getRandomIcon())
                    .user(user)
                    .type(classificationType.getCod())
                    .active(new Random().nextBoolean())
                    .build();
        }).toList();
    }

    private Category getRandomCategory(User user) {
        final int randomIndex = TestUtils.generateRandomInteger(0, getCategoryNames().length - 1);
        return Category.builder()
                .name(getCategoryNames()[randomIndex] + UUID.randomUUID())
                .color(getRandomHexColor())
                .icon(getRandomIcon())
                .user(user)
                .active(true)
                .build();
    }

    private Wallet getRandomWallet(User user) {
        var currencies = Arrays.asList(
                currencyRepository.findByCodeIgnoreCase("BRL").orElse(null),
                currencyRepository.findByCodeIgnoreCase("BRL").orElse(null),
                currencyRepository.findByCodeIgnoreCase("BRL").orElse(null),
                currencyRepository.findByCodeIgnoreCase("BRL").orElse(null),
                currencyRepository.findByCodeIgnoreCase("BRL").orElse(null),
                currencyRepository.findByCodeIgnoreCase("BRL").orElse(null),
                currencyRepository.findByCodeIgnoreCase("BRL").orElse(null),
                currencyRepository.findByCodeIgnoreCase("BRL").orElse(null),
                currencyRepository.findByCodeIgnoreCase("BRL").orElse(null),
                currencyRepository.findByCodeIgnoreCase("USD").orElse(null),
                currencyRepository.findByCodeIgnoreCase("BTC").orElse(null),
                currencyRepository.findByCodeIgnoreCase("JPY").orElse(null),
                currencyRepository.findByCodeIgnoreCase("CHF").orElse(null)
        );

        final int RANDOM_TYPE_INDEX = TestUtils.generateRandomInteger(0, WalletType.values().length - 1);
        Wallet.WalletBuilder builder;
        WalletType type = WalletType.values()[RANDOM_TYPE_INDEX];
        switch (type) {
            case CASH_WALLET -> builder = CashWallet.builder();
            case CRYPTO_WALLET -> builder = CryptoWallet.builder();
            case BANK_ACCOUNT ->
                    builder = BankAccount.builder().creditLimit(new BigDecimal(1000)).interestRate(new BigDecimal(8));
            case CREDIT_CARD ->
                    builder = CreditCard.builder().creditLimit(new BigDecimal(1000)).billingCycleDate(10).dueDate(20);
            default -> builder = CashWallet.builder().id(null);
        }
        final int RANDOM_CURRENCY_INDEX = TestUtils.generateRandomInteger(0, currencies.size() - 1);
        var currency = currencies.get(RANDOM_CURRENCY_INDEX);
        return builder
                .balance(TestUtils.generateRandomBigDecimal(0, 1000, 2))
                .currency(currency)
                .name(Utils.capitalizeFirstLetter(type.name() + " " + currency.getCode() + " " + TestUtils.generateRandomInteger(0, 1000)))
                .active(new Random().nextBoolean())
                .user(user)
                .build();
    }

    private TransactionParent getRandomTransactionParent() {
        var now = LocalDateTime.now();
        var parent = TransactionParent.builder()
                .eventMoment(TestUtils.generateRandomLocalDateTime(2018, 11, now.getYear(), now.getMonthValue()))
                .notes(TestUtils.generateLoremIpsum(TestUtils.generateRandomInteger(0, 20)))
                .active(true)
                .build();
        if (parent.getEventMoment().plusMonths(3).isAfter(now)) {
            parent.setTotalOfInstallments(1);
        } else {
            parent.setTotalOfInstallments(TestUtils.generateRandomInteger(1, 3));
        }
        return parent;
    }

    private List<Transaction> getRandomTransactions(Wallet wallet, List<Category> categories, TransactionParent parent) {
        List<Transaction> transactions = new ArrayList<>();

        var amount = TestUtils.generateRandomBigDecimal(10, 1500, 2);
        var dueDate = parent.getEventMoment().toLocalDate();
        var paymentMoment = parent.getEventMoment();
        var notes = TestUtils.generateLoremIpsum(TestUtils.generateRandomInteger(0, 20));
        var type = getRandomClassificationType();
        var user = wallet.getUser();
        var method = getRandomTransactionMethod(wallet).getCod();
        var valueCategories = buildTransactionCategories(amount, type, categories);

        for (int i = 1; i <= parent.getTotalOfInstallments(); i++) {
            Transaction transaction = Transaction.builder()
                    .amount(amount)
                    .dueDate(dueDate)
                    .paymentMoment(paymentMoment)
                    .notes(notes)
                    .type(type.getCod())
                    .currentInstallment(i)
                    .wallet(wallet)
                    .user(user)
                    .method(method)
                    .parent(parent)
                    .active(true)
                    .build();
            transaction.setCategories(valueCategories.entrySet().stream()
                    .map(entry -> TransactionCategory.builder()
                            .amount(entry.getValue())
                            .transaction(transaction)
                            .category(entry.getKey())
                            .build())
                    .collect(Collectors.toSet())
            );
            transactions.add(transaction);
            dueDate = dueDate.plusMonths(1L);
            paymentMoment = paymentMoment.plusMonths(1L);
        }

        return transactions;
    }

    private Map<Category, BigDecimal> buildTransactionCategories(BigDecimal amount, ClassificationType type, List<Category> categories) {
        if (categories.stream().noneMatch(c -> c.getType().equals(type))) {
            throw new RuntimeException("Unable to generate categories of the specified type");
        }
        Map<Category, BigDecimal> valueCategories = new HashMap<>();
        var amountDiff = amount;
        do {
            final var category = categories.get(TestUtils.generateRandomInteger(0, categories.size() - 1));
            BigDecimal randomAmount;
            if (category.getType().equals(type)) {
                var randomBigDecimal = TestUtils.generateRandomBigDecimal(1, Math.max(amount.multiply(BigDecimal.TWO).intValue(), 1000), 2);
                if (valueCategories.containsKey(category)) {
                    randomBigDecimal = randomBigDecimal.add(valueCategories.get(category));
                }
                randomAmount = randomBigDecimal.compareTo(amountDiff) > 0 ? amountDiff : randomBigDecimal;
            } else {
                randomAmount = valueCategories.getOrDefault(category, TestUtils.generateRandomBigDecimal(1, 100, 2));
            }
            valueCategories.put(category, randomAmount);
            amountDiff = amount.subtract(valueCategories.entrySet().stream()
                    .map(entry -> entry.getKey().getType().equals(type) ? entry.getValue() : entry.getValue().negate())
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
            );
        } while (amountDiff.compareTo(BigDecimal.ZERO) != 0);
        return valueCategories;
    }

    private ClassificationType getRandomClassificationType() {
        final int RANDOM_INDEX = TestUtils.generateRandomInteger(0, ClassificationType.values().length - 1);
        return ClassificationType.values()[RANDOM_INDEX];
    }

    private TransactionMethod getRandomTransactionMethod(Wallet wallet) {
        final int RANDOM_INDEX = TestUtils.generateRandomInteger(0, wallet.getAvailableTransactionMethods().size() - 1);
        return wallet.getAvailableTransactionMethods().get(RANDOM_INDEX);
    }

}
