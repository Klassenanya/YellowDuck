package autotests.tests;

import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.validation.json.JsonPathMessageValidationContext;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckFlyTest extends DuckActionsClient {

    @Test(description = "Проверка того, что уточка полетела, тест-кейс №1", priority = 1)
    @CitrusTest
    public void successfulFly(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        generateOneFakeId(runner, context);
        deleteDuckFinally(runner, "DELETE FROM DUCK WHERE ID=${fakeId}");
        databaseUpdate(runner, "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                "values (${fakeId}, 'yellow', 0.01, 'rubber', 'quack','ACTIVE');");

        duckFly(runner, "${fakeId}");
        validateResponse(runner, HttpStatus.OK, JsonPathMessageValidationContext.Builder.jsonPath()
                .expression("$.message", "I'm flying"));
    }

    @Test(description = "Проверка того, что уточка не полетела, тест-кейс №2", priority = 1)
    @CitrusTest
    public void notSuccessfulFly(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        generateOneFakeId(runner, context);
        deleteDuckFinally(runner, "DELETE FROM DUCK WHERE ID=${fakeId}");
        databaseUpdate(runner, "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                "values (${fakeId}, 'yellow', 0.01, 'rubber', 'quack','FIXED');");

        duckFly(runner, "${fakeId}");
        validateResponse(runner, HttpStatus.OK, JsonPathMessageValidationContext.Builder.jsonPath()
                .expression("$.message", "I can't fly"));
    }
}


