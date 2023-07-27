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

import static com.consol.citrus.actions.ExecuteSQLAction.Builder.sql;
import static com.consol.citrus.container.FinallySequence.Builder.doFinally;

public class DuckSwimTest extends DuckActionsClient {

    @Test(description = "Проверка того, что уточка поплыла", priority = 1)
    @CitrusTest
    public void successfulSwimSqlInTest(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        generateOneFakeId(runner, context);
        deleteDuckFinally(runner, "DELETE FROM DUCK WHERE ID=${fakeId}");
        databaseUpdate(runner, "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                "values (${fakeId}, 'yellow', 0.01, 'rubber', 'quack','ACTIVE');");

        duckSwim(runner, "${fakeId}");
        validateResponse(runner, HttpStatus.OK, JsonPathMessageValidationContext.Builder.jsonPath()
                .expression("$.message", "I'm swimming"));
    }

    @Test(description = "Проверка того, что уточка поплыла", priority = 1)
    @CitrusTest
    public void successfulSwimSqlFile(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        generateOneFakeId(runner, context);
        runner.$(doFinally().actions(runner.$(sql(testDb)
                .sqlResource("getDuckProperties/deleteFakeId.sql"))));
        databaseUpdate(runner, "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                "values (${fakeId}, 'yellow', 0.01, 'rubber', 'quack','ACTIVE');");

        duckSwim(runner, "${fakeId}");
        validateResponse(runner, HttpStatus.OK, JsonPathMessageValidationContext.Builder.jsonPath()
                .expression("$.message", "I'm swimming"));
    }
}

