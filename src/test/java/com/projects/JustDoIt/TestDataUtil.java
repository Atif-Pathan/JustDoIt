package com.projects.JustDoIt;

import com.projects.JustDoIt.model.enitities.Task;

public class TestDataUtil {

    private TestDataUtil(){

    }

    public static Task createTestTaskA() {
        return Task.builder()
                .title("Eat Breakfast")
                .description("Menu: Jam/Bread + Eggs")
                .finished(true)
                .build();
    }

    public static Task createTestTaskB() {
        return Task.builder()
                .title("Morning Routine")
                .description("Wash Face, Brush Teeth & Go to the washroom")
                .finished(true)
                .build();
    }

    public static Task createTestTaskC() {
        return Task.builder()
                .title("Nap")
                .description("Nap at 3pm")
                .finished(false)
                .build();
    }
}
