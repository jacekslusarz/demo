package com.jac.boot.demo.st;

import java.util.Arrays;
import java.util.List;

public class Instructors {

  public static List<Instructor> getAll(){
    Instructor instructor1 = new Instructor("Mike", 10, "Softfare developer", "M", true,
        Arrays.asList("Java programing", "Scala programing"));

    Instructor instructor2 = new Instructor("Adam", 5, "Softfare architect", "M", true,
        Arrays.asList("C++", "Scala programing"));

    Instructor instructor3 = new Instructor("Ewa", 15, "Datapase architect", "F", false,
        Arrays.asList("Oracle", "Mongo", "DB2"));

    Instructor instructor4 = new Instructor("Jenny", 4, "Enginer", "F", true,
        Arrays.asList("React", "Angular"));


    return Arrays.asList(instructor1, instructor2, instructor3, instructor4);

  }

}
