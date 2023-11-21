package com.jac.boot.demo;

import com.jac.boot.demo.eight.HellInterface;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntSupplier;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

public class JavaEightTest {

  @Test
  void declCollection(){
    IntStream.rangeClosed(1,10).min();

    HellInterface hellInterface = () -> "hello world";
    Consumer<String> cons = x -> System.out.println("consume " + x);
    Consumer<String> after = x -> System.out.println("after " + x);
    Consumer<String> compsiteConsumer = cons.andThen(after);

    compsiteConsumer.accept("kaa");

    Predicate<Integer> pred = (x) -> x > 10;
    Predicate<Integer> allPred = pred.and(x -> x < 20);


    System.out.println("is fulfilled for 3: " + allPred.test(3)) ;
    System.out.println("is fulfilled for 11: " + allPred.negate().test(11)) ;
    System.out.println("is fulfilled for 21: " + allPred.test(21)) ;

    Function<Long, String> fn = (x) ->  "To jest " +x;

    System.out.println(fn.apply(5l));
    Function<Long, String> longStringFunction = fn.andThen(x -> "To jest " + x);

    UnaryOperator<String> un = x -> x.toLowerCase();

    System.out.println(un.apply("kiSzka"));
    System.out.println(longStringFunction.apply(10L));
    //  System.out.println(hellInterface.sayHello());

    Arrays.asList("sa").forEach(System.out::println);

    Function<Integer, Double> sqrt = Math::sqrt;

    System.out.println(sqrt.apply(12));


  }

}
