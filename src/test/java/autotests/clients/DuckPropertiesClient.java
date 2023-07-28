package autotests.clients;

import autotests.BaseTest;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.validation.json.JsonPathMessageValidationContext;
import io.qameta.allure.Step;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;

import static com.consol.citrus.actions.ExecuteSQLAction.Builder.sql;
import static com.consol.citrus.container.FinallySequence.Builder.doFinally;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckPropertiesClient extends BaseTest {

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

    @Step("Удаление уточки")
    public void duckDelete(TestCaseRunner runner, String id) {
        sendDeleteRequest(runner, yellowDuckService, "/api/duck/delete", "id", id);
    }

    @Step("Получение всех id уточек")
    public void duckGetAllIds(TestCaseRunner runner) {
        sendGetRequestGetAllIds(runner, yellowDuckService, "/api/duck/getAllIds");
    }

    @Step("Обновление уточки")
    public void duckUpdate(TestCaseRunner runner, String color, String height, String id, String material, String sound, String wingsState) {
        runner.$(http().client(yellowDuckService)
                .send()
                .put("/api/duck/update")
                .queryParam("color", color)
                .queryParam("height", height)
                .queryParam("id", id)
                .queryParam("material", material)
                .queryParam("sound", sound)
                .queryParam("wingsState", wingsState));
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

    @Step("Валидация и извлечение id уточки")
    public void validateResponseResourcesAndExtractId(TestCaseRunner runner, HttpStatus status, String response) {
        validateResponseResourcesAndExtractId(runner, yellowDuckService, status, response);
    }
}