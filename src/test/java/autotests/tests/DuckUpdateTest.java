package autotests.tests;

import autotests.clients.DuckPropertiesClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.validation.json.JsonPathMessageValidationContext;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckUpdateTest extends DuckPropertiesClient {
    @Test(description = "Проверка того, что уточка обновлена, тест-кейс №1", priority = 1)
    @CitrusTest
    public void successfulUpdate1(@Optional @CitrusResource TestCaseRunner runner) {
        try {
            duckCreateResources(runner, "getDuckProperties/createEmptyDuck.json");
            duckIdExtract(runner);
            duckUpdate(runner, "green", "11", "${duckId}", "plastic", "quack", "FIXED");
            String duckId = "${duckId}";
            validateResponse(runner, HttpStatus.OK, JsonPathMessageValidationContext.Builder.jsonPath()
                    .expression("$.message", "Duck with id = " + duckId + " is updated"));
        } finally {
            duckDelete(runner, "${duckId}");
        }
    }
}

