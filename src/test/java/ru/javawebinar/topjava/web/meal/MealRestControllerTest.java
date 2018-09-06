package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


class MealRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = MealRestController.REST_URL + "/";

    @Autowired
    protected MealService mealService;

    @Test
    void getAll() throws Exception {
        mockMvc.perform(get(REST_URL + ADMIN_ID))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(ADMIN_MEAL1, ADMIN_MEAL2));
    }

    @Test
    void getOne() throws Exception{
        mockMvc.perform((get(REST_URL + USER_ID + "/id/" + MEAL1_ID)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MEAL1));
    }

    @Test
    void deleteOne() throws Exception{
        mockMvc.perform(delete(REST_URL + USER_ID + "/id/" + 100002))
                .andExpect(status().isNoContent());

        assertMatch(mealService.getAll(100000), MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);

    }

    @Test
    void createWithLocation() throws Exception {
        Meal expected = new Meal(LocalDateTime.now(), "NeWWWW", 666);
        ResultActions action = mockMvc.perform(post(REST_URL + ADMIN_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Meal returned = TestUtil.readFromJson(action, Meal.class);
        expected.setId(returned.getId());

        System.out.println("****************\n" + returned.getDescription() + " - " + returned.getId() + "\n****************************");

        assertMatch(returned, expected);
        assertMatch(mealService.getAll(ADMIN_ID), expected, ADMIN_MEAL2, ADMIN_MEAL1);
    }

    @Test
    void update() throws Exception {
        Meal updated = new Meal(MEAL1);

        updated.setDescription("Updated***");
        updated.setCalories(666);
        mockMvc.perform(put(REST_URL + USER_ID + "/id/" + 100002)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        assertMatch(mealService.get(100002, USER_ID), updated);
    }

    @Test
    void getBetween() throws Exception{
        mockMvc.perform(get(REST_URL + USER_ID + "/between?startDate=2015-05-31&endTime=17:00:00"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MEAL5, MEAL4));
    }
}