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

@Epic("Тестовый класс обновления уточки")
@Feature("autotests.tests.DuckUpdateTest")
public class DuckUpdateTest extends DuckPropertiesClient {

    @Test(description = "successfulUpdate", priority = 1)
    @CitrusTest
    @Description("Проверка того, что уточка обновлена")
    public void successfulUpdate(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        generateFakeId(runner, context);
        deleteDuckFinally(runner, "DELETE FROM DUCK WHERE ID=${fakeId}");
        databaseUpdate(runner, "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                "values (${fakeId}, '', 0, '', '','UNDEFINED');");

        duckUpdate(runner, "green", "11", "${fakeId}", "plastic", "quack", "FIXED");
        validateResponse(runner, HttpStatus.OK, JsonPathMessageValidationContext.Builder.jsonPath()
                .expression("$.message", "Duck with id = ${fakeId} is updated"));
        validateDuckInDatabase(runner, "${fakeId}",  "green", "11.0", "plastic", "quack", "FIXED");
    }
}

