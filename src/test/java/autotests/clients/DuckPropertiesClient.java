package autotests.clients;

import autotests.BaseTest;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.validation.json.JsonPathMessageValidationContext;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DuckPropertiesClient extends BaseTest {

    public void duckIdExtract (TestCaseRunner runner) {
        extractVariables (runner, yellowDuckService, "$.id", "duckId");
    }

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

    @Description("Создание уточки из папки Resources")
    public void duckCreateResources(TestCaseRunner runner, String expectedPayload) {
        sendPostRequestResources(runner, yellowDuckService, "/api/duck/create", expectedPayload);
    }

    @Description("Создание уточки из папки Payload")
    public void duckCreate(TestCaseRunner runner, Object duckProperties) {
        sendPostRequest(runner, yellowDuckService, "/api/duck/create", duckProperties);
    }

    public void duckDelete(TestCaseRunner runner, String id) {
        sendDeleteRequest(runner, yellowDuckService, "/api/duck/delete", "id", id);
    }

    public void duckGetAllIds(TestCaseRunner runner) {
        runner.$(http()
                .client(yellowDuckService)
                .send()
                .get("/api/duck/getAllIds"));
    }

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

    @Description("Валидация полученного ответа String'ой")
    public void validateResponse(TestCaseRunner runner, HttpStatus status, String response) {
        validateResponse(runner, yellowDuckService, status, response);
    }

    @Description("Валидация полученного ответа из папки Resources")
    public void validateResponseResources(TestCaseRunner runner, HttpStatus status, String expectedPayload) {
        validateResponseResources(runner, yellowDuckService, status, expectedPayload);
    }

    @Description("Валидация полученного ответа из папки Payload")
    public void validateResponse(TestCaseRunner runner, HttpStatus status, Object duckProperties) {
        validateResponse(runner, yellowDuckService, status, duckProperties);
    }

    @Description("Валидация полученного ответа по JsonPath")
    public void validateResponse(TestCaseRunner runner, HttpStatus status, JsonPathMessageValidationContext.Builder body) {
        validateResponse(runner, yellowDuckService,  status, body);
    }
}