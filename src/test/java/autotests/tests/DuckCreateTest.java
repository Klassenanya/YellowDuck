package autotests.tests;

import autotests.clients.DuckPropertiesClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.validation.json.JsonPathMessageValidationContext;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckCreateTest extends DuckPropertiesClient {

    @Test(description = "Проверка того, что уточка с параметрами создана", priority = 1)
    @CitrusTest
    public void successfulCreate(@Optional @CitrusResource TestCaseRunner runner) {
        duckCreate(runner, "green", "10.0", "wood", "muamua", "ACTIVE");
        duckIdExtract(runner);
        validateDuckInDatabase(runner, "${duckId}",  "green", "10.0", "wood", "muamua", "ACTIVE");
        deleteDuckInDatabase(runner, "${duckId}");
    }

    // Валидация без БД, но с использованием метода validateResponseResourcesAndExtractId (объединены методы валидации и извлечения id)
    @Test(description = "Проверка того, что уточка с параметрами создана", priority = 1)
    @CitrusTest
    public void successfulCreateNoDatabase(@Optional @CitrusResource TestCaseRunner runner) {
        duckCreate(runner, "green", "10.0", "wood", "muamua", "ACTIVE");
        deleteDuckFinally(runner, "DELETE FROM DUCK WHERE ID=${duckId}");
        validateResponseResourcesAndExtractId(runner, HttpStatus.OK, "getDuckProperties/createGreenDuck.json");
    }

    @Test(description = "Проверка того, что пустая уточка создана", priority = 1)
    @CitrusTest
    public void successfulEmptyCreate(@Optional @CitrusResource TestCaseRunner runner) {
        duckCreate(runner, "", "0", "", "", "UNDEFINED");
        duckIdExtract(runner);
        validateDuckInDatabase(runner, "${duckId}", "", "0.0", "", "", "UNDEFINED");
        deleteDuckInDatabase(runner, "${duckId}");
    }
}
