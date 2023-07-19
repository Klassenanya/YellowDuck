package autotests.clients;

import autotests.EndpointConfig;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import java.awt.*;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;



@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckActionsClient extends TestNGCitrusSpringSupport {
    @Autowired
    protected HttpClient yellowDuckService;

    public void duckFly(TestCaseRunner runner, String id) {
        runner.$(http().client(yellowDuckService)
                .send()
                .get("/api/duck/action/fly")
                .queryParam("id", id));
    }

    public void duckProperties(TestCaseRunner runner, String id) {
        runner.$(http().client(yellowDuckService)
                .send()
                .get("/api/duck/action/properties")
                .queryParam("id", id));
    }

    public void duckQuack(TestCaseRunner runner, String id, String repetitionCount, String soundCount) {
        runner.$(http().client(yellowDuckService)
                .send()
                .get("/api/duck/action/quack")
                .queryParam("id", id)
                .queryParam("repetitionCount", repetitionCount)
                .queryParam("soundCount", soundCount));
    }

    public void duckSwim(TestCaseRunner runner, String id) {
        runner.$(http().client(yellowDuckService)
                .send()
                .get("/api/duck/action/swim")
                .queryParam("id", id));
    }

    //Валидация ответа String'ой
    @Description("Валидация полученного ответа")
    public void validateResponseS(TestCaseRunner runner) {
        runner.$(http()
                .client(yellowDuckService)
                .receive()
                .response(HttpStatus.OK)
                .message().type(MessageType.JSON)
                .body("{\n" +
                        " \"color\": \"yellow\",\n" +
                        " \"height\": 10,\n" +
                        " \"material\": \"plastic\",\n" +
                        " \"sound\": \"crya-crya\",\n" +
                        " \"wingsState\": \"ACTIVE\"\n" +
                        "}"));
    }

    //Валидация ответа из папки Resources
    @Description("Валидация полученного ответа")
    public void validateResponseR(TestCaseRunner runner) {
        runner.$(http()
                .client(yellowDuckService)
                .receive()
                .response(HttpStatus.OK)
                .message().type(MessageType.JSON)
                .body(new ClassPathResource("getDuckProperties/createYellowDuck.json")));
    }

    //Валидация ответа из папки Payload
    @Description("Валидация полученного ответа")
    public void validateResponseP(TestCaseRunner runner, Object duckProperties) {
        runner.$(http()
                .client(yellowDuckService)
                .receive()
                .response(HttpStatus.OK)
                .message().type(MessageType.JSON)
                .body(new ObjectMappingPayloadBuilder(duckProperties, new ObjectMapper())));
    }
}