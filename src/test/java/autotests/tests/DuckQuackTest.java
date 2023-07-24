package autotests.tests;

import autotests.clients.DuckActionsClient;
import autotests.payloads.DuckAnswerQuack;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DuckQuackTest extends DuckActionsClient {

    @Test(description = "Проверка того, что уточка крякает, тест-кейс №1", priority = 1)
    @CitrusTest
    public void successfulQuack1(@Optional @CitrusResource TestCaseRunner runner) {
        try {
            duckCreate(runner, "yellow", "0.01", "rubber", "quack", "ACTIVE");
            duckIdExtract(runner);
            duckQuack(runner, "${duckId}", "0", "2");
            DuckAnswerQuack duckAnswerQuack1 = new DuckAnswerQuack().sound("");
            validateResponse(runner, HttpStatus.OK, duckAnswerQuack1);
        } finally {
            duckDelete(runner, "${duckId}");
        }
    }

    @Test(description = "Проверка того, что уточка крякает, тест-кейс №4 и №15 (чётный и нечётный)", invocationCount = 2, priority = 1)
    @CitrusTest
    public void successfulQuack2(@Optional @CitrusResource TestCaseRunner runner) {
        try {
            duckCreate(runner, "yellow", "0.01", "rubber", "quack", "ACTIVE");
            duckIdExtract(runner);
            duckQuack(runner, "${duckId}", "3", "2");
            DuckAnswerQuack duckAnswerQuack2 = new DuckAnswerQuack().sound("quack-quack, quack-quack, quack-quack");
            validateResponse(runner, HttpStatus.OK, duckAnswerQuack2);
        } finally {
            duckDelete(runner, "${duckId}");
        }
    }

    @Test(description = "Проверка того, что уточка крякает, тест-кейс №5 и №16 (чётный и нечётный)", invocationCount = 2, priority = 1)
    @CitrusTest
    public void successfulQuack3(@Optional @CitrusResource TestCaseRunner runner) {
        try {
            duckCreate(runner, "yellow", "0.01", "rubber", "quack", "ACTIVE");
            duckIdExtract(runner);
            duckQuack(runner, "${duckId}", "2", "3");
            DuckAnswerQuack duckAnswerQuack3 = new DuckAnswerQuack().sound("quack-quack-quack, quack-quack-quack");
            validateResponse(runner, HttpStatus.OK, duckAnswerQuack3);
        } finally {
            duckDelete(runner, "${duckId}");
        }
    }

    @Test(description = "Проверка того, что уточка крякает, тест-кейс №6 и №17 (чётный и нечётный)", invocationCount = 2, priority = 1)
    @CitrusTest
    public void successfulQuack4(@Optional @CitrusResource TestCaseRunner runner) {
        try {
            duckCreate(runner, "yellow", "0.01", "rubber", "quack", "ACTIVE");
            duckIdExtract(runner);
            duckQuack(runner, "${duckId}", "3", "3");
            DuckAnswerQuack duckAnswerQuack4 = new DuckAnswerQuack().sound("quack-quack-quack, quack-quack-quack, quack-quack-quack");
            validateResponse(runner, HttpStatus.OK, duckAnswerQuack4);
        } finally {
            duckDelete(runner, "${duckId}");
        }
    }

    @Test(description = "Проверка того, что уточка крякает, тест-кейс №7", priority = 1)
    @CitrusTest
    public void successfulQuack5(@Optional @CitrusResource TestCaseRunner runner) {
        try {
            duckCreate(runner, "yellow", "0.01", "rubber", "quack", "ACTIVE");
            duckIdExtract(runner);
            duckQuack(runner, "${duckId}", "3", "0");
            DuckAnswerQuack duckAnswerQuack5 = new DuckAnswerQuack().sound("");
            validateResponse(runner, HttpStatus.OK, duckAnswerQuack5);
        } finally {
            duckDelete(runner, "${duckId}");
        }
    }
}
