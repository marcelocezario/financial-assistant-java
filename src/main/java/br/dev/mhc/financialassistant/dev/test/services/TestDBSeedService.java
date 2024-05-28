package br.dev.mhc.financialassistant.dev.test.services;

import br.dev.mhc.financialassistant.category.entities.Category;
import br.dev.mhc.financialassistant.category.repositories.CategoryRepository;
import br.dev.mhc.financialassistant.common.utils.TestUtils;
import br.dev.mhc.financialassistant.common.utils.Utils;
import br.dev.mhc.financialassistant.currency.repositories.CurrencyRepository;
import br.dev.mhc.financialassistant.transaction.entities.Transaction;
import br.dev.mhc.financialassistant.transaction.entities.TransactionCategory;
import br.dev.mhc.financialassistant.transaction.enums.TransactionMethod;
import br.dev.mhc.financialassistant.transaction.enums.TransactionType;
import br.dev.mhc.financialassistant.transaction.repositories.TransactionRepository;
import br.dev.mhc.financialassistant.user.entities.User;
import br.dev.mhc.financialassistant.user.repositories.UserRepository;
import br.dev.mhc.financialassistant.wallet.entities.*;
import br.dev.mhc.financialassistant.wallet.enums.WalletType;
import br.dev.mhc.financialassistant.wallet.repositories.WalletRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Profile("test")
@Service
public class TestDBSeedService {

    private final UserRepository userRepository;
    private final CurrencyRepository currencyRepository;
    private final CategoryRepository categoryRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    public TestDBSeedService(UserRepository userRepository, CurrencyRepository currencyRepository, CategoryRepository categoryRepository, WalletRepository walletRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.currencyRepository = currencyRepository;
        this.categoryRepository = categoryRepository;
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
    }

    public void databaseSeeding() {
        userRepository.findAll().forEach(this::seedUserData);
    }

    private void seedUserData(User user) {
        var categories = categoryRepository.saveAll(getCategoryList(user));
        List<Wallet> wallets = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            wallets.add(getRandomWallet(user));
        }
        wallets = walletRepository.saveAll(wallets);
        List<Transaction> transactions = new ArrayList<>();
        for (Wallet wallet : wallets) {
            for (int i = 0; i < 100; i++) {
                transactions.add(getRandomTransaction(wallet, categories));
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
        String[] icons = {"search", "home", "account_circle", "settings", "done", "info", "close", "check_circle",
                "delete", "menu", "person", "expand_more", "add", "visibility", "shopping_cart", "edit", "favorite",
                "email", "description", "logout", "arrow_back", "favorite_border", "chevron_right", "lock",
                "location_on", "notifications", "schedule", "local_shipping", "search", "language", "call",
                "file_download", "arrow_forward_ios", "home", "arrow_back_ios", "groups", "cancel", "help_outline",
                "arrow_drop_down", "face", "manage_accounts", "play_arrow", "place", "verified", "menu", "more_vert",
                "add_circle_outline", "filter_alt", "thumb_up", "close", "event", "star", "fingerprint", "dashboard",
                "people", "list", "content_copy", "login", "add_circle", "visibility_off", "check_circle_outline",
                "arrow_forward", "chevron_left", "calendar_today", "send", "settings", "check_box", "share", "warning",
                "highlight_off", "navigate_next", "help", "phone", "paid", "task_alt", "question_answer", "check",
                "expand_less", "clear", "date_range", "article", "error", "photo_camera", "more_horiz",
                "check_box_outline_blank", "open_in_new", "image", "shopping_bag", "person_outline", "school",
                "lightbulb", "refresh", "file_upload", "done", "expand_more", "person", "perm_identity",
                "credit_card", "history", "trending_up", "support_agent", "account_balance", "delete_outline",
                "attach_money", "person_add", "public", "check_circle", "save", "mail", "error_outline",
                "report_problem", "fact_check", "radio_button_unchecked", "verified_user", "assignment", "link",
                "favorite", "play_circle_filled", "emoji_events", "remove", "star_rate", "add", "apps", "business",
                "delete", "download", "filter_list", "arrow_right_alt", "chat", "account_balance_wallet", "payments",
                "menu_book", "account_circle", "folder", "keyboard_arrow_down", "mail", "autorenew", "build",
                "videocam", "view_list", "print", "work", "store", "analytics", "radio_button_checked", "phone_iphone",
                "play_circle", "tune", "delete_forever", "today", "grid_view", "arrow_upward", "east", "inventory_2",
                "mail_outline", "admin_panel_settings", "mic", "calendar_month", "group", "picture_as_pdf", "lock_open",
                "volume_up", "watch_later", "grade", "receipt_long", "local_offer", "update", "room", "badge",
                "savings", "code", "map", "info", "light_mode", "circle", "receipt", "inventory", "add_shopping_cart",
                "contact_support", "arrow_back", "category", "edit_note", "insights", "power_settings_new", "star",
                "campaign", "format_list_bulleted", "chevron_right", "star_border", "pause", "warning_amber",
                "remove_circle_outline", "restart_alt", "wifi", "arrow_back_ios_new", "done_all", "pets", "storefront",
                "sort", "mode_edit"};
        final int index = TestUtils.generateRandomInteger(0, icons.length - 1);
        return icons[index];
    }

    private List<Category> getCategoryList(User user) {
        return Arrays.stream(getCategoryNames()).map(
                        name -> Category.builder()
                                .name(name)
                                .color(getRandomHexColor())
                                .icon(getRandomIcon())
                                .user(user)
                                .active(new Random().nextBoolean())
                                .build())
                .toList();
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

    private Transaction getRandomTransaction(Wallet wallet, List<Category> categories) {
        Transaction transaction = Transaction.builder()
                .amount(TestUtils.generateRandomBigDecimal(10, 1500, 2))
                .moment(TestUtils.generateRandomLocalDateTime(2023, 2024))
                .notes(TestUtils.generateLoremIpsum(TestUtils.generateRandomInteger(0, 20)))
                .type(getRandomTransactionType().getCod())
                .currentInstallment(1)
                .wallet(wallet)
                .user(wallet.getUser())
                .method(getRandomTransactionMethod(wallet).getCod())
                .build();
        Set<TransactionCategory> transactionCategories = new HashSet<>();
        var amountDiff = transaction.getAmount();
        do {
            var randomBigDecimal = TestUtils.generateRandomBigDecimal(1, 1000, 2);
            var amount = randomBigDecimal.compareTo(amountDiff) > 0 ? amountDiff : randomBigDecimal;
            final var category = new AtomicReference<>(categories.get(TestUtils.generateRandomInteger(0, categories.size() - 1)));
            while (transactionCategories.stream().map(TransactionCategory::getCategory).anyMatch(c -> c.equals(category.get()))) {
                category.set(categories.get(TestUtils.generateRandomInteger(0, categories.size() - 1)));
            }
            var transactionCategory = TransactionCategory.builder()
                    .amount(amount)
                    .transaction(transaction)
                    .category(category.get())
                    .build();
            transactionCategories.add(transactionCategory);
            amountDiff = transaction.getAmount().subtract(
                    transactionCategories.stream().map(TransactionCategory::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add)
            );
        } while (amountDiff.compareTo(BigDecimal.ZERO) > 0);
        transaction.setCategories(transactionCategories);
        return transaction;
    }

    private TransactionType getRandomTransactionType() {
        final int RANDOM_INDEX = TestUtils.generateRandomInteger(0, TransactionType.values().length - 1);
        return TransactionType.values()[RANDOM_INDEX];
    }

    private TransactionMethod getRandomTransactionMethod(Wallet wallet) {
        final int RANDOM_INDEX = TestUtils.generateRandomInteger(0, wallet.getAvailableTransactionMethods().size() - 1);
        return wallet.getAvailableTransactionMethods().get(RANDOM_INDEX);
    }

}
