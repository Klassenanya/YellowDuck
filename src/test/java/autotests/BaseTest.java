package autotests;

import com.consol.citrus.TestCaseRunner;
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

import static com.consol.citrus.http.actions.HttpActionBuilder.http;
import static com.consol.citrus.validation.DelegatingPayloadVariableExtractor.Builder.fromBody;

@ContextConfiguration(classes = {EndpointConfig.class})
public class BaseTest extends TestNGCitrusSpringSupport {

    @Autowired
    protected HttpClient yellowDuckService;

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
}
