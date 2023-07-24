package autotests.tests;

import autotests.clients.DuckPropertiesClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class DuckGetAllIdsTest extends DuckPropertiesClient {

    @Test(description = "Проверка того, что id не выводятся, т.к. созданных уточек нет, тест-кейс №1", priority = 1)
    @CitrusTest
    public void successfulGetAllIds1(@Optional @CitrusResource TestCaseRunner runner) {
        duckGetAllIds(runner);
        validateResponse( runner, HttpStatus.OK,  "[]");
    }

    @Test(description = "Проверка того, что выводится id одной существующей уточки, тест-кейс №2", priority = 1)
    @CitrusTest
    public void successfulGetAllIds2(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        try {
            duckCreate(runner, "yellow", "0.01", "rubber", "quack", "ACTIVE");
            duckIdExtract(runner);
            duckGetAllIds(runner);

            runner.variable("id", "${duckId}");
            Integer id = Integer.valueOf(context.getVariable("id"));

            ArrayList<Integer> expDuckList = new ArrayList<Integer>();
            expDuckList.add(id);
            validateResponse(runner, HttpStatus.OK, expDuckList);
        } finally {
            duckDelete(runner, "${duckId}");
        }
    }
}


