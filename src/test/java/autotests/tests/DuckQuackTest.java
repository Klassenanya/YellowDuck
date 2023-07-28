package autotests.tests;

import autotests.clients.DuckActionsClient;
import autotests.payloads.DuckAnswerQuack;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Flaky;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

@Epic("Тестовый класс, где уточка крякает")
@Feature("autotests.tests.DuckQuackTest")
public class DuckQuackTest extends DuckActionsClient {

    @Test(description = "successfulQuackZeroRepetitionCount", priority = 1)
    @CitrusTest
    @Description("Проверка того, что уточка крякает: repetitionCount=0, soundCount=2")
    public void successfulQuackZeroRepetitionCount(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        generateFakeId(runner, context);
        deleteDuckFinally(runner, "DELETE FROM DUCK WHERE ID=${fakeId}");
        databaseUpdate(runner, "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                "values (${fakeId}, 'yellow', 0.01, 'rubber', 'quack','ACTIVE');");

        duckQuack(runner, "${fakeId}", "0", "2");
        DuckAnswerQuack duckAnswerQuack = new DuckAnswerQuack().sound("");
        validateResponse(runner, HttpStatus.OK, duckAnswerQuack);
    }

    @Flaky
    @Test(description = "successfulQuackDifferentOdd32", priority = 1)
    @CitrusTest
    @Description("Проверка того, что уточка крякает: repetitionCount=3, soundCount=2, нечётный id")
    public void successfulQuackDifferentOdd32(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        deleteAllInDatabase(runner);
        runner.variable("id", 1);
        deleteDuckFinally(runner, "DELETE FROM DUCK WHERE ID=${id}");
        databaseUpdate(runner, "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                "values (${id}, 'yellow', 0.01, 'rubber', 'quack','ACTIVE');");

        duckQuack(runner, "${id}", "3", "2");
        DuckAnswerQuack duckAnswerQuack1 = new DuckAnswerQuack().sound("quack-quack, quack-quack, quack-quack");
        validateResponse(runner, HttpStatus.OK, duckAnswerQuack1);
    }

    @Flaky
    @Test(description = "successfulQuackDifferentEven32", priority = 1)
    @CitrusTest
    @Description("Проверка того, что уточка крякает: repetitionCount=3, soundCount=2, чётный id")
    public void successfulQuackDifferentEven32(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        deleteAllInDatabase(runner);
        runner.variable("id", 2);
        deleteDuckFinally(runner, "DELETE FROM DUCK WHERE ID=${id}");
        databaseUpdate(runner, "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                "values (${id}, 'yellow', 0.01, 'rubber', 'quack','ACTIVE');");

        duckQuack(runner, "${id}", "3", "2");
        DuckAnswerQuack duckAnswerQuack1 = new DuckAnswerQuack().sound("quack-quack, quack-quack, quack-quack");
        validateResponse(runner, HttpStatus.OK, duckAnswerQuack1);
    }

    @Flaky
    @Test(description = "successfulQuackDifferentOdd23", priority = 1)
    @CitrusTest
    @Description("Проверка того, что уточка крякает: repetitionCount=2, soundCount=3, нечётный id")
    public void successfulQuackDifferentOdd23(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        deleteAllInDatabase(runner);
        runner.variable("id", 1);
        deleteDuckFinally(runner, "DELETE FROM DUCK WHERE ID=${id}");
        databaseUpdate(runner, "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                "values (${id}, 'yellow', 0.01, 'rubber', 'quack','ACTIVE');");

        duckQuack(runner, "${id}", "2", "3");
        DuckAnswerQuack duckAnswerQuack1 = new DuckAnswerQuack().sound("quack-quack-quack, quack-quack-quack");
        validateResponse(runner, HttpStatus.OK, duckAnswerQuack1);
    }

    @Flaky
    @Test(description = "successfulQuackDifferentEven23", priority = 1)
    @CitrusTest
    @Description("Проверка того, что уточка крякает: repetitionCount=2, soundCount=3, чётный id")
    public void successfulQuackDifferentEven23(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        deleteAllInDatabase(runner);
        runner.variable("id", 2);
        deleteDuckFinally(runner, "DELETE FROM DUCK WHERE ID=${id}");
        databaseUpdate(runner, "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                "values (${id}, 'yellow', 0.01, 'rubber', 'quack','ACTIVE');");

        duckQuack(runner, "${id}", "2", "3");
        DuckAnswerQuack duckAnswerQuack1 = new DuckAnswerQuack().sound("quack-quack-quack, quack-quack-quack");
        validateResponse(runner, HttpStatus.OK, duckAnswerQuack1);
    }

    @Test(description = "successfulQuackEqualNewOdd", priority = 1)
    @CitrusTest
    @Description("Проверка того, что уточка крякает: repetitionCount=3, soundCount=3, нечётный id")
    public void successfulQuackEqualNewOdd(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        deleteAllInDatabase(runner);
        runner.variable("id", 1);
        deleteDuckFinally(runner, "DELETE FROM DUCK WHERE ID=${id}");
        databaseUpdate(runner, "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                "values (${id}, 'yellow', 0.01, 'rubber', 'quack','ACTIVE');");

        duckQuack(runner, "${id}", "3", "3");
        DuckAnswerQuack duckAnswerQuack1 = new DuckAnswerQuack().sound("quack-quack-quack, quack-quack-quack, quack-quack-quack");
        validateResponse(runner, HttpStatus.OK, duckAnswerQuack1);
    }

    @Flaky
    @Test(description = "successfulQuackEqualNewEven", priority = 1)
    @CitrusTest
    @Description("Проверка того, что уточка крякает: repetitionCount=3, soundCount=3, чётный id")
    public void successfulQuackEqualNewEven(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        deleteAllInDatabase(runner);
        runner.variable("id", 2);
        deleteDuckFinally(runner, "DELETE FROM DUCK WHERE ID=${id}");
        databaseUpdate(runner, "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                "values (${id}, 'yellow', 0.01, 'rubber', 'quack','ACTIVE');");

        duckQuack(runner, "${id}", "3", "3");
        DuckAnswerQuack duckAnswerQuack2 = new DuckAnswerQuack().sound("quack-quack-quack, quack-quack-quack, quack-quack-quack");
        validateResponse(runner, HttpStatus.OK, duckAnswerQuack2);
    }

    @Test(description = "successfulQuackZeroSoundCount", priority = 1)
    @CitrusTest
    @Description("Проверка того, что уточка крякает: repetitionCount=3, soundCount=0")
    public void successfulQuackZeroSoundCount(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        generateFakeId(runner, context);
        deleteDuckFinally(runner, "DELETE FROM DUCK WHERE ID=${fakeId}");
        databaseUpdate(runner, "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                "values (${fakeId}, 'yellow', 0.01, 'rubber', 'quack','ACTIVE');");

        duckQuack(runner, "${fakeId}", "3", "0");
        DuckAnswerQuack duckAnswerQuack = new DuckAnswerQuack().sound("");
        validateResponse(runner, HttpStatus.OK, duckAnswerQuack);
    }
}
