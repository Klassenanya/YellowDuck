package autotests.tests;

import autotests.clients.DuckPropertiesClient;
import autotests.payloads.DuckProperties;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.springframework.http.HttpStatus;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

@Epic("Тестовый класс создания уточки параметризированный")
@Feature("autotests.tests.DuckCreateParamTest")
public class DuckCreateParamTest extends DuckPropertiesClient {
    DuckProperties duckProperties1 = new DuckProperties().color("pink").height(10).material("wood").sound("muamua").wingsState("ACTIVE");
    DuckProperties duckProperties2 = new DuckProperties().color("blue").height(12).material("plastic").sound("woof").wingsState("FIXED");
    DuckProperties duckProperties3 = new DuckProperties().color("green").height(0.05).material("metal").sound("moo").wingsState("UNDEFINED");
    DuckProperties duckProperties4 = new DuckProperties().color("yellow").height(1.8).material("glass").sound("dzin").wingsState("ACTIVE");
    DuckProperties duckProperties5 = new DuckProperties().color("red").height(200).material("fabric").sound("sh").wingsState("FIXED");

    @Test(dataProvider = "duckList", description = "createDuckList", priority = 1)
    @CitrusTest
    @CitrusParameters({"duckProperties", "expectedPayload", "status", "runner"})
    @Description("Проверка того, что уточка создана (параметризованный тест с 5 уточками)")
    public void createDuckList(Object duckProperties, HttpStatus status, String expectedPayload, @Optional @CitrusResource TestCaseRunner runner) {
        duckCreate(runner, duckProperties);
        validateResponseResources(runner, status, expectedPayload);
    }

    @DataProvider (name = "duckList")
    public Object[][] testData() {
        return new Object[][]{
                {duckProperties1, HttpStatus.OK, "ParamTest/createPinkDuck.json", null},
                {duckProperties2, HttpStatus.OK, "ParamTest/createBlueDuck.json", null},
                {duckProperties3, HttpStatus.OK, "ParamTest/createGreenDuck.json", null},
                {duckProperties4, HttpStatus.OK, "ParamTest/createYellowDuck.json", null},
                {duckProperties5, HttpStatus.OK, "ParamTest/createRedDuck.json", null}};

    }
}


