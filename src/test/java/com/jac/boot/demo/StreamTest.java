package com.jac.boot.demo;

import com.jac.boot.demo.st.Instructor;
import com.jac.boot.demo.st.Instructors;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

public class StreamTest {

  @Test
  void stream(){

    List<String> list = Arrays.asList("Kot", "pies", "sarna", "Osa");
 list.stream().filter(t -> Character.isLowerCase(t.charAt(0)))
        .forEach(System.out::println);

  list.stream().sorted().forEach(System.out::println);

    List<Integer> integers = Arrays.asList(2, 4, 5);
    System.out.println(integers.stream().reduce((a, b) -> a +b ));
    System.out.println(list.stream().reduce(String::concat).get());

    BinaryOperator<Integer> binaryOperator = (x,y) -> x*y;

    System.out.println(integers.stream().reduce(binaryOperator));

    Predicate<Instructor> p1 = (i) -> i.isOnLineCourses();
    Predicate<Instructor> p2 = (i) -> i.getYearsOfExperience() > 2;

    Map<String, List<String>> collect = Instructors.getAll().stream().filter(p1).filter(p2).peek(
        System.out::println)
        .collect(Collectors.toMap(Instructor::getName, Instructor::getCourses));

    String s = Instructors.getAll().stream().filter(p1).findFirst().map(t -> t.getName())
        .orElse("sa");
    System.out.println(s);

   /* Map<String, Object> map = new HashMap<>();

    map.put("P_DATE_FROM", LocalDateTime.now());
    map.put("P_DATE_TO", null);
    String collect1 = map.entrySet().stream().sorted(Entry.comparingByKey()).map(e ->
    e.getKey() + ":" + (Objects.nonNull(e.getValue()) ? e.getValue().toString() : "")
    ).collect(
        Collectors.joining());
    System.out.println(collect1);
*/

  }

}
