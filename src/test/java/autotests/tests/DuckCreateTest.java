package autotests.tests;

import autotests.clients.DuckPropertiesClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.validation.json.JsonPathMessageValidationContext;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

@Epic("Тестовый класс создания уточки")
@Feature("autotests.tests.DuckCreateTest")
public class DuckCreateTest extends DuckPropertiesClient {

    @Test(description = "successfulCreate", priority = 1)
    @CitrusTest
    @Description("Проверка того, что уточка с параметрами создана")
    public void successfulCreate(@Optional @CitrusResource TestCaseRunner runner) {
        duckCreate(runner, "green", "10.0", "wood", "muamua", "ACTIVE");
        duckIdExtract(runner);
        validateDuckInDatabase(runner, "${duckId}",  "green", "10.0", "wood", "muamua", "ACTIVE");
        deleteDuckInDatabase(runner);
    }

    // Валидация без БД, но с использованием метода validateResponseResourcesAndExtractId (объединены методы валидации и извлечения id)
    @Test(description = "successfulCreateNoDatabase", priority = 1)
    @CitrusTest
    @Description("Проверка того, что уточка с параметрами создана")
    public void successfulCreateNoDatabase(@Optional @CitrusResource TestCaseRunner runner) {
        duckCreate(runner, "green", "10.0", "wood", "muamua", "ACTIVE");
        validateResponseResourcesAndExtractId(runner, HttpStatus.OK, "getDuckProperties/createGreenDuck.json");
        deleteDuckInDatabase(runner);
    }

    @Test(description = "successfulEmptyCreate", priority = 1)
    @CitrusTest
    @Description("Проверка того, что пустая уточка создана")
    public void successfulEmptyCreate(@Optional @CitrusResource TestCaseRunner runner) {
        duckCreate(runner, "", "0", "", "", "UNDEFINED");
        duckIdExtract(runner);
        validateDuckInDatabase(runner, "${duckId}", "", "0.0", "", "", "UNDEFINED");
        deleteDuckInDatabase(runner);
    }
}
