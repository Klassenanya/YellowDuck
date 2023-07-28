package autotests.clients;

import autotests.BaseTest;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.validation.json.JsonPathMessageValidationContext;
import io.qameta.allure.Step;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;

import static com.consol.citrus.actions.ExecuteSQLAction.Builder.sql;
import static com.consol.citrus.container.FinallySequence.Builder.doFinally;

public class DuckActionsClient extends BaseTest {

    @Step("Извлечение id уточки")
    public void duckIdExtract (TestCaseRunner runner) {
        extractVariables (runner, yellowDuckService, "$.id", "duckId");
    }

    @Step("Создание уточки")
    @Description("Создание уточки String'ой")
    public void duckCreate(TestCaseRunner runner, String color, String height, String material, String sound, String wingsState) {
        sendPostRequest(runner, yellowDuckService,"/api/duck/create","{\n" +
                "  \"color\": \"" + color + "\",\n" +
                "  \"height\": " + height + ",\n" +
                "  \"material\": \"" + material + "\",\n" +
                "  \"sound\": \"" + sound + "\",\n" +
                "  \"wingsState\": \"" + wingsState + "\"\n" +
                "}");
    }

    @Step("Создание уточки")
    @Description("Создание уточки из папки Resources")
    public void duckCreateResources(TestCaseRunner runner, String expectedPayload) {
        sendPostRequestResources(runner, yellowDuckService, "/api/duck/create", expectedPayload);
    }

    @Step("Создание уточки")
    @Description("Создание уточки из папки Payload")
    public void duckCreate(TestCaseRunner runner, Object duckProperties) {
        sendPostRequest(runner, yellowDuckService, "/api/duck/create", duckProperties);
    }

    @Step("Уточка полетела")
    public void duckFly(TestCaseRunner runner, String id) {
        sendGetRequest(runner, yellowDuckService, "/api/duck/action/fly", "id", id);
    }

    @Step("Получение свойств уточки")
    public void duckProperties(TestCaseRunner runner, String id) {
        sendGetRequest(runner, yellowDuckService, "/api/duck/action/properties", "id", id);
    }

    @Step("Уточка крякает")
    public void duckQuack(TestCaseRunner runner, String id, String repetitionCount, String soundCount) {
        sendGetRequestQuack(runner, yellowDuckService, "/api/duck/action/quack", "id", id, "repetitionCount", repetitionCount, "soundCount", soundCount);
    }

    @Step("Уточка поплыла")
    public void duckSwim(TestCaseRunner runner, String id) {
        sendGetRequest(runner, yellowDuckService, "/api/duck/action/swim", "id", id);
    }

    @Step("Валидация полученного ответа")
    @Description("Валидация полученного ответа String'ой")
    public void validateResponse(TestCaseRunner runner, HttpStatus status, String response) {
        validateResponse(runner, yellowDuckService, status, response);
    }

    @Step("Валидация полученного ответа")
    @Description("Валидация полученного ответа из папки Resources")
    public void validateResponseResources(TestCaseRunner runner, HttpStatus status, String expectedPayload) {
        validateResponseResources(runner, yellowDuckService, status, expectedPayload);
    }

    @Step("Валидация полученного ответа")
    @Description("Валидация полученного ответа из папки Payload")
    public void validateResponse(TestCaseRunner runner, HttpStatus status, Object duckProperties) {
        validateResponse(runner, yellowDuckService, status, duckProperties);
    }

    @Step("Валидация полученного ответа")
    @Description("Валидация полученного ответа по JsonPath")
    public void validateResponse(TestCaseRunner runner, HttpStatus status, JsonPathMessageValidationContext.Builder body) {
        validateResponse(runner, yellowDuckService,  status, body);
    }

    @Step("Удаление уточки finally")
    public void deleteDuckFinally (TestCaseRunner runner, String sql) {
        runner.$(doFinally().actions(runner.$(sql(testDb)
                .statement(sql))));
    }

    @Step("Удаление уточки finally")
    public void deleteDuckFinallySqlFile (TestCaseRunner runner) {
        runner.$(doFinally().actions(runner.$(sql(testDb)
                .sqlResource("getDuckProperties/deleteFakeId.sql"))));
    }
}