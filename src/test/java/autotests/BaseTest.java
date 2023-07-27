package autotests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import com.consol.citrus.validation.json.JsonPathMessageValidationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;
import static com.consol.citrus.validation.DelegatingPayloadVariableExtractor.Builder.fromBody;
import static com.consol.citrus.actions.ExecuteSQLAction.Builder.sql;
import static com.consol.citrus.actions.ExecuteSQLQueryAction.Builder.query;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.testng.annotations.Optional;

@ContextConfiguration(classes = {EndpointConfig.class})
public class BaseTest extends TestNGCitrusSpringSupport {

    @Autowired
    protected HttpClient yellowDuckService;

    @Autowired
    protected SingleConnectionDataSource testDb;

    protected void databaseUpdate(TestCaseRunner runner, String sql) {
        runner.$(sql(testDb)
                .statement(sql));
    }

    protected void extractVariables (TestCaseRunner runner, HttpClient URL, String path, String variableName) {
        runner.$(http()
                .client(URL)
                .receive()
                .response()
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract(fromBody().expression(path, variableName)));
    }

    protected void sendGetRequest(TestCaseRunner runner, HttpClient URL, String path, String nameQueryParam, String valueQueryParam) {
        runner.$(http()
                .client(URL)
                .send()
                .get(path)
                .queryParam(nameQueryParam, valueQueryParam));
    }

    protected void sendGetRequestQuack(TestCaseRunner runner, HttpClient URL, String path, String nameQueryParam1, String valueQueryParam1, String nameQueryParam2, String valueQueryParam2, String nameQueryParam3, String valueQueryParam3) {
        runner.$(http()
                .client(URL)
                .send()
                .get(path)
                .queryParam(nameQueryParam1, valueQueryParam1)
                .queryParam(nameQueryParam2, valueQueryParam2)
                .queryParam(nameQueryParam3, valueQueryParam3));
    }

    protected void sendGetRequestGetAllIds (TestCaseRunner runner, HttpClient URL, String path) {
        runner.$(http()
                .client(URL)
                .send()
                .get(path));
    }

    @Description("Создание уточки String'ой")
    protected void sendPostRequest(TestCaseRunner runner, HttpClient URL, String path, String body) {
        runner.$(http()
                .client(URL)
                .send()
                .post(path)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body));
    }

    @Description("Создание уточки из папки Resources")
    protected void sendPostRequestResources(TestCaseRunner runner, HttpClient URL, String path, String body) {
        runner.$(http()
                .client(URL)
                .send()
                .post(path)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ClassPathResource(body)));
    }

    @Description("Создание уточки из папки Payload")
    protected void sendPostRequest(TestCaseRunner runner, HttpClient URL, String path, Object body) {
        runner.$(http()
                .client(URL)
                .send()
                .post(path)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ObjectMappingPayloadBuilder(body, new ObjectMapper())));
    }

    protected void sendDeleteRequest(TestCaseRunner runner, HttpClient URL, String path, String nameQueryParam, String valueQueryParam) {
        runner.$(http().client(URL)
                .send()
                .delete(path)
                .queryParam(nameQueryParam, valueQueryParam));
    }

    @Description("Валидация полученного ответа String'ой")
    protected void validateResponse(TestCaseRunner runner, HttpClient URL, HttpStatus status, String body) {
        runner.$(http()
                .client(URL)
                .receive()
                .response(status)
                .message().type(MessageType.JSON)
                .body(body));
    }
    @Description("Валидация полученного ответа из папки Resources")
    protected void validateResponseResources(TestCaseRunner runner, HttpClient URL, HttpStatus status, String body) {
        runner.$(http()
                .client(URL)
                .receive()
                .response(status)
                .message().type(MessageType.JSON)
                .body(new ClassPathResource(body)));

    }

    @Description("Валидация полученного ответа из папки Payload")
    protected void validateResponse(TestCaseRunner runner, HttpClient URL, HttpStatus status, Object body) {
        runner.$(http()
                .client(URL)
                .receive()
                .response(status)
                .message().type(MessageType.JSON)
                .body(new ObjectMappingPayloadBuilder(body, new ObjectMapper())));
    }

    @Description("Валидация полученного ответа по JsonPath")
    protected void validateResponse(TestCaseRunner runner, HttpClient URL, HttpStatus status, JsonPathMessageValidationContext.Builder body) {
        runner.$(http()
                .client(URL)
                .receive()
                .response(status)
                .message().type(MessageType.JSON)
                .validate(body));
    }

    protected void validateDuckInDatabase(TestCaseRunner runner, String id, String color, String height, String material, String sound, String wingsState) {
        runner.$(query(testDb)
                .statement("SELECT * FROM DUCK WHERE ID=" + id)
                .validate("COLOR",color)
                .validate("HEIGHT",height)
                .validate("MATERIAL",material)
                .validate("SOUND",sound)
                .validate("WINGS_STATE",wingsState));
    }

    @Description("Вычисление максимального id уточки в БД")
    protected void maxDuckIdInDatabase (TestCaseRunner runner) {
        runner.$(query(testDb)
                .statement("select max(ID) as DuckMaxId from duck")
                .extract("DuckMaxId", "duckMaxId"));
    }

    @Description("Вычисление количества уточек в БД")
    protected void duckCounterInDatabase (TestCaseRunner runner) {
        runner.$(query(testDb)
                .statement("select count(*) as DuckCounter from duck")
                .extract("DuckCounter", "duckCounter"));
    }

    protected void deleteAllInDatabase (TestCaseRunner runner) {
        runner.$(sql(testDb)
               .statement("DELETE FROM DUCK"));
    }

    protected void validateResponseResourcesAndExtractId(TestCaseRunner runner, HttpClient URL, HttpStatus status, String body) {
        runner.$(http()
                .client(URL)
                .receive()
                .response(status)
                .message().type(MessageType.JSON)
                .body(new ClassPathResource(body))
                .extract(fromBody().expression("$.id", "duckId")));
    }

    protected void deleteDuckInDatabase (TestCaseRunner runner, String duckId) {
        runner.$(sql(testDb)
                .statement("DELETE FROM DUCK WHERE ID=${" + duckId + "}"));
    }

    @Description("Генерация нового (несуществующего) id утки")
    public void generateOneFakeId(TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        duckCounterInDatabase(runner);
        runner.variable("DuckCounter", "${duckCounter}");
        Integer id;

        // если в БД нет уток (DuckCounter=0), то создаем утку с id=0+1=1, иначе создаем утку с id=максимальный_id_существующей_утки+1
        if (Integer.valueOf(context.getVariable("DuckCounter")) == 0) {id = 0;}
        else {
            maxDuckIdInDatabase(runner);
            runner.variable("DuckMaxId", "${duckMaxId}");
            id = Integer.valueOf(context.getVariable("DuckMaxId"));
        }

        Integer newId = id + 1; // формируем несуществующий id утки
        String fakeId = newId.toString(); // формируем несуществующий id утки строковый

        runner.variable("fakeId", fakeId);
    }

    @Description("Генерация двух новых (несуществующих) id уток")
    public void generateTwoFakeId(TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        duckCounterInDatabase(runner);
        runner.variable("DuckCounter", "${duckCounter}");
        Integer id;

        // если в БД нет уток (DuckCounter=0), то создаем утку с id=0+1=1, иначе создаем утку с id=максимальный_id_существующей_утки+1
        if (Integer.valueOf(context.getVariable("DuckCounter")) == 0) {id = 0;}
        else {
            maxDuckIdInDatabase(runner);
            runner.variable("DuckMaxId", "${duckMaxId}");
            id = Integer.valueOf(context.getVariable("DuckMaxId"));
        }

        Integer newId1 = id + 1; // формируем несуществующий id первой утки
        Integer newId2 = id + 2; // формируем несуществующий id второй утки
        String fakeId1 = newId1.toString(); // формируем несуществующий id первой утки строковый
        String fakeId2 = newId2.toString(); // формируем несуществующий id второй утки строковый

        runner.variable("fakeId1", fakeId1);
        runner.variable("fakeId2", fakeId2);
    }
}
