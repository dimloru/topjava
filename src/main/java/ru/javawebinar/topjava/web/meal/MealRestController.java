package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@RestController
@RequestMapping(MealRestController.REST_URL)
public class MealRestController extends AbstractMealController {
    static final String REST_URL = "/rest/meals/foruser";

    @GetMapping(value = "/{uid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> getAll(@PathVariable("uid") int uid) {
        SecurityUtil.setAuthUserId(uid);
        return super.getAll();
    }

    @GetMapping(value = "/{uid}/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Meal get(@PathVariable("uid") int uid, @PathVariable("id") int id) {
        SecurityUtil.setAuthUserId(uid);
        return super.get(id);
    }

    @DeleteMapping(value = "/{uid}/id/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("uid") int uid, @PathVariable("id") int id) {
        SecurityUtil.setAuthUserId(uid);
        super.delete(id);
    }

    @PostMapping(value = "/{uid}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createWithLocation(@PathVariable("uid") int uid, @RequestBody Meal meal) {
        SecurityUtil.setAuthUserId(uid);
        Meal created = super.create(meal);

//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setLocation(uriOfNewResource);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/foruser/" + meal.getUser().getId() + "/id/{id}")
                .buildAndExpand(created.getId()).toUri();

        //Good practice to return URI of a newly created object
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{uid}/id/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody Meal meal, @PathVariable("uid") int uid, @PathVariable("id") int id) {
        SecurityUtil.setAuthUserId(uid);
        super.update(meal, id);
    }

    @GetMapping(value = "/{uid}/between", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> getBetween(@PathVariable("uid") int uid,
                 @RequestParam(value = "startTime", required = false) LocalTime startTime,
                 @RequestParam(value = "endTime", required = false) LocalTime endTime,
                 @RequestParam(value = "startDate", required = false) LocalDate startDate,
                 @RequestParam(value = "endDate", required = false) LocalDate endDate) {
        SecurityUtil.setAuthUserId(uid);
        // if ..DateTime== null ... or
        // Util.orElse
        return super.getBetween(startDate, startTime, endDate, endTime);
    }


}