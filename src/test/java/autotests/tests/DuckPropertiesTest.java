package autotests.tests;

import autotests.clients.DuckActionsClient;
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

@Epic("Тестовый класс получения свойств уточки")
@Feature("autotests.tests.DuckPropertiesTest")
public class DuckPropertiesTest extends DuckActionsClient {

    @Flaky
    @Test(description = "successfulPropertiesActive", priority = 1)
    @CitrusTest
    @Description("Проверка свойств уточки, wingsState=ACTIVE")
    public void successfulPropertiesActive(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        generateFakeId(runner, context);
        deleteDuckFinally(runner, "DELETE FROM DUCK WHERE ID=${fakeId}");
        databaseUpdate(runner, "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                "values (${fakeId}, 'yellow', 0.01, 'rubber', 'quack','ACTIVE');");

        duckProperties(runner, "${fakeId}");
        validateResponseResources(runner, HttpStatus.OK, "getDuckProperties/createYellowDuckActive.json");
    }

    @Flaky
    @Test(description = "successfulPropertiesFixed", priority = 1)
    @CitrusTest
    @Description("Проверка свойств уточки, wingsState=FIXED")
    public void successfulPropertiesFixed(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        generateFakeId(runner, context);
        deleteDuckFinally(runner, "DELETE FROM DUCK WHERE ID=${fakeId}");
        databaseUpdate(runner, "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                "values (${fakeId}, 'yellow', 0.01, 'rubber', 'quack','FIXED');");

        duckProperties(runner, "${fakeId}");
        validateResponseResources(runner, HttpStatus.OK, "getDuckProperties/createYellowDuckFixed.json");
    }

    @Flaky
    @Test(description = "successfulPropertiesUndefined", priority = 1)
    @CitrusTest
    @Description("Проверка свойств уточки, wingsState=UNDEFINED")
    public void successfulPropertiesUndefined(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        generateFakeId(runner, context);
        deleteDuckFinally(runner, "DELETE FROM DUCK WHERE ID=${fakeId}");
        databaseUpdate(runner, "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                "values (${fakeId}, 'yellow', 0.01, 'rubber', 'quack','UNDEFINED');");

        duckProperties(runner, "${fakeId}");
        validateResponseResources(runner, HttpStatus.OK, "getDuckProperties/createYellowDuckUndefined.json");
    }

    @Flaky
    @Test(description = "successfulPropertiesMetal", priority = 1)
    @CitrusTest
    @Description("Проверка свойств уточки, material=metal")
    public void successfulPropertiesMetal(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        generateFakeId(runner, context);
        deleteDuckFinally(runner, "DELETE FROM DUCK WHERE ID=${fakeId}");
        databaseUpdate(runner, "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                "values (${fakeId}, 'yellow', 0.01, 'metal', 'quack','ACTIVE');");

        duckProperties(runner, "${fakeId}");
        validateResponseResources(runner, HttpStatus.OK, "getDuckProperties/createYellowDuckMetal.json");
    }
}

