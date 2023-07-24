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

    @Test(description = "Проверка того, что уточка создана, тест-кейс №1", priority = 1)
    @CitrusTest
    public void successfulCreate1(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        try {
            duckCreate(runner, "green", "10.0", "wood", "muamua", "ACTIVE"); // уточка 1
            validateResponseResources(runner, HttpStatus.OK, "getDuckProperties/createGreenDuck.json");
        } finally {
            // удаление уточки 1 с помощью создания еще одной уточки 2
            duckCreate(runner, "yellow", "0.01", "rubber", "quack", "FIXED"); // уточка 2
            duckIdExtract(runner);

            runner.variable("id", "${duckId}"); // id утки 2
            Integer newDuckId = Integer.valueOf(context.getVariable("id")); // id утки 2
            Integer oldDuckId = newDuckId - 1; // id утки 1
            duckDelete(runner, newDuckId.toString());
            duckDelete(runner, oldDuckId.toString());
        }
    }

    @Test(description = "Проверка того, что уточка создана, тест-кейс №5", priority = 1)
    @CitrusTest
    public void successfulCreate2(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        try {
            duckCreateResources(runner, "getDuckProperties/createEmptyDuck.json"); // уточка 1
            validateResponse(runner, HttpStatus.OK, JsonPathMessageValidationContext.Builder.jsonPath()
                    .expression("$.material", "")
                    .expression("$.sound", "")
                    .expression("$.color", "")
                    .expression("$.height", "0.0")
                    .expression("$.wingsState", "UNDEFINED"));
        } finally {
            // удаление уточки 1 с помощью создания еще одной уточки 2
            duckCreate(runner, "yellow", "0.01", "rubber", "quack", "FIXED"); // уточка 2
            duckIdExtract(runner);

            runner.variable("id", "${duckId}"); // id утки 2
            Integer newDuckId = Integer.valueOf(context.getVariable("id")); // id утки 2
            Integer oldDuckId = newDuckId - 1; // id утки 1
            duckDelete(runner, newDuckId.toString());
            duckDelete(runner, oldDuckId.toString());
        }
    }
}
