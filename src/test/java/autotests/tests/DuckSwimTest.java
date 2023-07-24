package autotests.tests;

import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.validation.json.JsonPathMessageValidationContext;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckSwimTest extends DuckActionsClient {

    @Test(description = "Проверка того, что уточка поплыла, тест-кейс №1", priority = 1)
    @CitrusTest
    public void successfulSwim1(@Optional @CitrusResource TestCaseRunner runner) {
        try {
            duckCreateResources(runner, "getDuckProperties/createYellowDuckActive.json");
            duckIdExtract(runner);
            duckSwim(runner, "${duckId}");
            validateResponse(runner, HttpStatus.OK, JsonPathMessageValidationContext.Builder.jsonPath()
                    .expression("$.message", "I'm swimming"));
        } finally {
            duckDelete(runner, "${duckId}");
        }
    }
}

