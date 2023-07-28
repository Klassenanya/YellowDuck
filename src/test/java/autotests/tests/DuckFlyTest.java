package autotests.tests;

import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.validation.json.JsonPathMessageValidationContext;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Flaky;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

@Epic("Тестовый класс, где уточка летает")
@Feature("autotests.tests.DuckFlyTest")
public class DuckFlyTest extends DuckActionsClient {

    @Flaky
    @Test(description = "successfulFly", priority = 1)
    @CitrusTest
    @Description("Проверка того, что уточка полетела")
    public void successfulFly(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        generateFakeId(runner, context);
        deleteDuckFinally(runner, "DELETE FROM DUCK WHERE ID=${fakeId}");
        databaseUpdate(runner, "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                "values (${fakeId}, 'yellow', 0.01, 'rubber', 'quack','ACTIVE');");

        duckFly(runner, "${fakeId}");
        validateResponse(runner, HttpStatus.OK, JsonPathMessageValidationContext.Builder.jsonPath()
                .expression("$.message", "I'm flying"));
    }

    @Flaky
    @Test(description = "notSuccessfulFly", priority = 1)
    @CitrusTest
    @Description("Проверка того, что уточка не полетела")
    public void notSuccessfulFly(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        generateFakeId(runner, context);
        deleteDuckFinally(runner, "DELETE FROM DUCK WHERE ID=${fakeId}");
        databaseUpdate(runner, "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                "values (${fakeId}, 'yellow', 0.01, 'rubber', 'quack','FIXED');");

        duckFly(runner, "${fakeId}");
        validateResponse(runner, HttpStatus.OK, JsonPathMessageValidationContext.Builder.jsonPath()
                .expression("$.message", "I can't fly"));
    }
}


