package autotests.tests;

import autotests.clients.DuckPropertiesClient;
import autotests.payloads.DuckAnswerMessage;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckDeleteTest extends DuckPropertiesClient {

    @Test(description = "Проверка того, что существующая уточка удалена, тест-кейс №1", priority = 1)
    @CitrusTest
    public void successfulDelete1(@Optional @CitrusResource TestCaseRunner runner) {
        try {
            duckCreate(runner, "yellow", "0.01", "rubber", "quack", "ACTIVE");
            duckIdExtract(runner);
            duckDelete(runner, "${duckId}");
            DuckAnswerMessage duckAnswerMessage1 = new DuckAnswerMessage().message("Duck is deleted");
            validateResponse(runner, HttpStatus.OK, duckAnswerMessage1);
        } finally {
            duckDelete(runner, "${duckId}");
        }
    }

    @Test(description = "Проверка того, что несуществующая уточка не удалена, тест-кейс №2", priority = 1)
    @CitrusTest
    public void successfulDelete2(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        try {
            duckCreate(runner, "yellow", "0.01", "rubber", "quack", "ACTIVE");
            duckIdExtract(runner);

            runner.variable("id", "${duckId}");
            Integer id = Integer.valueOf(context.getVariable("id"));
            Integer newId = id + 100; // формируем несуществующий id утки
            String fakeId = newId.toString(); // формируем несуществующий id утки
            duckDelete(runner, fakeId);

            DuckAnswerMessage duckAnswerMessage1 = new DuckAnswerMessage().message("Duck with id = " + fakeId + " is not found");
            validateResponse(runner, HttpStatus.OK, duckAnswerMessage1);
        } finally {
            duckDelete(runner, "${duckId}");
        }
    }
}


