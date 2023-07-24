package autotests.tests;

import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckFlyTest extends DuckActionsClient {

    @Test (description = "Проверка того, что уточка полетела, тест-кейс №1", priority = 1)
    @CitrusTest
    public void successfulFly1(@Optional @CitrusResource TestCaseRunner runner) {
       try {
           duckCreate(runner, "yellow", "0.01", "rubber", "quack", "ACTIVE");
           duckIdExtract(runner);
           duckFly(runner, "${duckId}");
           validateResponse(runner, HttpStatus.OK, "{\n" +
                "  \"message\": \"I'm flying\"\n" +
                "}");
       } finally {
           duckDelete(runner, "${duckId}");
       }
    }

    @Test (description = "Проверка того, что уточка не полетела, тест-кейс №2", priority = 1)
    @CitrusTest
    public void successfulFly2(@Optional @CitrusResource TestCaseRunner runner) {
        try {
            duckCreate(runner, "yellow", "0.01", "rubber", "quack", "FIXED");
            duckIdExtract(runner);
            duckFly(runner, "${duckId}");
            validateResponse(runner, HttpStatus.OK, "{\n" +
                    "  \"message\": \"I can't fly\"\n" +
                    "}");
        } finally {
            duckDelete(runner, "${duckId}");
        }
    }
}


