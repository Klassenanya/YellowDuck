package autotests.tests;

import autotests.clients.DuckActionsClient;
import autotests.payloads.DuckProperties;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckPropertiesTest extends DuckActionsClient {

    @Test(description = "Проверка свойств уточки, тест-кейс №1", priority = 1)
    @CitrusTest
    public void successfulProperties1(@Optional @CitrusResource TestCaseRunner runner) {
        try {
            DuckProperties duckProperties1 = new DuckProperties().color("yellow").height(0.01).material("rubber").sound("quack").wingsState("ACTIVE");
            duckCreate(runner, duckProperties1);
            duckIdExtract(runner);
            duckProperties(runner, "${duckId}");
            validateResponseResources(runner, HttpStatus.OK, "getDuckProperties/createYellowDuckActive.json");
        } finally {
            duckDelete(runner, "${duckId}");
        }
    }

    @Test(description = "Проверка свойств уточки, тест-кейс №2", priority = 1)
    @CitrusTest
    public void successfulProperties2(@Optional @CitrusResource TestCaseRunner runner) {
        try {
            DuckProperties duckProperties2 = new DuckProperties().color("yellow").height(0.01).material("rubber").sound("quack").wingsState("FIXED");
            duckCreate(runner, duckProperties2);
            duckIdExtract(runner);
            duckProperties(runner, "${duckId}");
            validateResponseResources(runner, HttpStatus.OK, "getDuckProperties/createYellowDuckFixed.json");
        } finally {
            duckDelete(runner, "${duckId}");
        }
    }

    @Test(description = "Проверка свойств уточки, тест-кейс №3", priority = 1)
    @CitrusTest
    public void successfulProperties3(@Optional @CitrusResource TestCaseRunner runner) {
        try {
            DuckProperties duckProperties3 = new DuckProperties().color("yellow").height(0.01).material("rubber").sound("quack").wingsState("UNDEFINED");
            duckCreate(runner, duckProperties3);
            duckIdExtract(runner);
            duckProperties(runner, "${duckId}");
            validateResponseResources(runner, HttpStatus.OK, "getDuckProperties/createYellowDuckUndefined.json");
        } finally {
            duckDelete(runner, "${duckId}");
        }
    }

    @Test(description = "Проверка свойств уточки, тест-кейс №13", priority = 1)
    @CitrusTest
    public void successfulProperties4(@Optional @CitrusResource TestCaseRunner runner) {
        try {
            DuckProperties duckProperties4 = new DuckProperties().color("yellow").height(0.01).material("metal").sound("quack").wingsState("ACTIVE");
            duckCreate(runner, duckProperties4);
            duckIdExtract(runner);
            duckProperties(runner, "${duckId}");
            validateResponseResources(runner, HttpStatus.OK, "getDuckProperties/createYellowDuckMetal.json");
        } finally {
            duckDelete(runner, "${duckId}");
        }
    }
}

