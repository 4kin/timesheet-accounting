package dummy;

import com.google.gson.*;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class dummyTest {
    @Test
    void name() {
        Gson gson = new Gson();

        // Создание объектов JSON
        JsonObject jsonObject1 = new JsonObject();
        jsonObject1.addProperty("key1", "value1");

        JsonObject jsonObject2 = new JsonObject();
        jsonObject2.addProperty("key2", "value2");

        // Создание массива объектов JSON
        JsonObject[] jsonArray = {jsonObject1, jsonObject2};

        // Преобразование массива объектов JSON в строку JSON
        String jsonString = gson.toJson(jsonArray);
        System.out.println(jsonString);

    }

    @RepeatedTest(value = 5, name = "Повторение {currentRepetition} из {totalRepetitions}")
    void repeatedTestInGerman() {
        // ...
    }

}
