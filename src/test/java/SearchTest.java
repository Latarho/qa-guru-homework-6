import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Selenide.*;

public class SearchTest {
    @BeforeEach
    void setUp() {
        open("https://minaexplorer.com/");
    }

    @AfterEach
    void closedBrowser() {
        closeWebDriver();
    }

    @ValueSource(strings = {"B62qqBdM9mcTk1otWiRPfBBF3uJ51BRGH67GG23HakzfhcmDNjwUYVw",
                            "B62qmWbgvGV1MwxS6rJEG3BSbT2T8b5DP2Theb2CaBduBXGJz6qfCqb",
                            "B62qov9yv8TayLteD6SDXvxyYtmn3KkUoozAbs47fVo9JZSpcynbzTz"})
    @ParameterizedTest(name = "Проверка отображения результатов поиска для кошелька PublicKey {0}")
    void searchTestSimpleParams(String expectedPublicKey) {
        $x("//*[@name=\"search\"]").setValue(expectedPublicKey).pressEnter();
        String actualPublicKey= $x("//table[@class=\"table table-striped\"]//td").getText();

        assert(actualPublicKey.equals(expectedPublicKey));
    }

    @CsvSource(value = {
            "B62qqBdM9mcTk1otWiRPfBBF3uJ51BRGH67GG23HakzfhcmDNjwUYVw, Account Overview ",
            "B62qmWbgvGV1MwxS6rJEG3BSbT2T8b5DP2Theb2CaBduBXGJz6qfCqb, Account Overview ",
            "B62qov9yv8TayLteD6SDXvxyYtmn3KkUoozAbs47fVo9JZSpcynbzTz, Account Overview "
    })
    @ParameterizedTest(name = "Проверка отображения результатов поиска для кошелька PublicKey {0}")
    void searchTestCsvParams(String expectedPublicKey, String tableName) {
        $x("//*[@name=\"search\"]").setValue(expectedPublicKey).pressEnter();
        $$x("//*[@class=\"card-title\"]").find(Condition.text(tableName));
        String actualPublicKey = $x("//table[@class=\"table table-striped\"]//td").getText();

        assert (actualPublicKey.equals(expectedPublicKey));
    }

    static Stream<Arguments> argumentsTestDataProvider() {
        return Stream.of(
                Arguments.of("B62qqBdM9mcTk1otWiRPfBBF3uJ51BRGH67GG23HakzfhcmDNjwUYVw", " Account Overview "),
                Arguments.of("B62qmWbgvGV1MwxS6rJEG3BSbT2T8b5DP2Theb2CaBduBXGJz6qfCqb", " Account Overview "),
                Arguments.of("B62qov9yv8TayLteD6SDXvxyYtmn3KkUoozAbs47fVo9JZSpcynbzTz", " Account Overview ")
        );
    }

    @MethodSource(value = "argumentsTestDataProvider")
    @ParameterizedTest(name = "Проверка отображения результатов поиска для кошелька PublicKey {0}")
    void searchTestStreamParams(String expectedPublicKey, String tableName) {
        $x("//*[@name=\"search\"]").setValue(expectedPublicKey).pressEnter();
        $$x("//*[@class=\"card-title\"]").find(Condition.text(tableName));
        String actualPublicKey = $x("//table[@class=\"table table-striped\"]//td").getText();

        assert (actualPublicKey.equals(expectedPublicKey));
    }
}