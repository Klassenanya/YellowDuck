package autotests.tests;

import autotests.clients.DuckPropertiesClient;
import autotests.payloads.DuckAnswerMessage;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Flaky;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

@Epic("Тестовый класс удаления уточки")
@Feature("autotests.tests.DuckDeleteTest")
public class DuckDeleteTest extends DuckPropertiesClient {

    @Test(description = "successfulExistDelete", priority = 1)
    @CitrusTest
    @Description("Проверка того, что существующая уточка удалена")
    public void successfulExistDelete(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        generateFakeId(runner, context);
        deleteDuckFinally(runner, "DELETE FROM DUCK WHERE ID=${fakeId}");
        databaseUpdate(runner, "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                "values (${fakeId}, 'yellow', 0.01, 'rubber', 'quack','ACTIVE');");

        duckDelete(runner, "${fakeId}");
        DuckAnswerMessage duckAnswerMessage1 = new DuckAnswerMessage().message("Duck is deleted");
        validateResponse(runner, HttpStatus.OK, duckAnswerMessage1);
    }

    @Flaky
    @Test(description = "notSuccessfulNotExistDelete", priority = 1)
    @CitrusTest
    @Description("Проверка того, что несуществующая уточка не удалена")
    public void notSuccessfulNotExistDelete(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        runner.variable("duckId", "123456789");
        deleteDuckInDatabase(runner); // предварительно удаляем из БД утку с таким id

        duckDelete(runner, "${duckId}");
        DuckAnswerMessage duckAnswerMessage1 = new DuckAnswerMessage().message("Duck with id = ${duckId} is not found");
        validateResponse(runner, HttpStatus.OK, duckAnswerMessage1);
    }
}


