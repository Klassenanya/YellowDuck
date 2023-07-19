package autotests;

import autotests.payloads.DuckProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.core.io.ClassPathResource;

public class Main {

    public static void main(String[] args) {

        //Создаем объект duckProperties класса DuckProperties
        DuckProperties duckProperties = new DuckProperties().color("gray").height(2.22).material("plastic").sound("crya-crya").wingsState("ACTIVE");

        //устанавливаем значения свойств объекта

        //Передаем объект в метод преобразования объекта класса в Json
        convertAndPrintToJson(duckProperties);
    }

    //метод для преобразования объекта класса в Json
    public static void convertAndPrintToJson(Object jsonBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
            String json = objectWriter.writeValueAsString(jsonBody);

            System.out.println(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}