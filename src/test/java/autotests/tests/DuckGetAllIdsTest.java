package autotests.tests;

import autotests.clients.DuckPropertiesClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.util.ArrayList;

@Epic("Тестовый класс получения всех id уточек")
@Feature("autotests.tests.DuckGetAllIdsTest")
public class DuckGetAllIdsTest extends DuckPropertiesClient {

    @Test(description = "successfulNoGetAllIds", priority = 1)
    @CitrusTest
    @Description("Проверка того, что id не выводятся, т.к. созданных уточек нет")
    public void successfulNoGetAllIds(@Optional @CitrusResource TestCaseRunner runner) {
        deleteAllInDatabase(runner);
        duckGetAllIds(runner);
        validateResponse( runner, HttpStatus.OK,  "[]");
    }

    @Test(description = "successfulGetAllIds", priority = 1)
    @CitrusTest
    @Description("Проверка того, что выводится id одной существующей уточки")
    public void successfulGetAllIds(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        deleteAllInDatabase(runner);
        Integer id = 1;
        runner.variable("id", id);
        deleteDuckFinally(runner, "DELETE FROM DUCK WHERE ID=${id}");
        databaseUpdate(runner, "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                "values (${id}, 'yellow', 0.01, 'rubber', 'quack','FIXED');");

        duckGetAllIds(runner);
        ArrayList<Integer> expDuckList = new ArrayList<Integer>();
        expDuckList.add(id);
        validateResponse(runner, HttpStatus.OK, expDuckList);
    }
}


