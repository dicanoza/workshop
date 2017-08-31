package com.daitangroup;

import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

public class Exemplo1 {

    @Test
    @Ignore
    public void iteratestrings() throws IOException {
        String[] strings = "meu pé de laranja lima".split(" ");
        for (String st : strings) {
            System.out.print(st);
        }
        System.out.println();
        Stream.of(strings).forEach(System.out::print);
        System.out.println();
    }

    @Test
    @Ignore
    public void infiniteStream() {
        Stream<String> echo = Stream.generate(() -> "echo");
        echo.forEach(System.out::println);
        Stream<Double> randoms = Stream.generate(Math::random);
        randoms.forEach(System.out::println);
    }

    @Test
    @Ignore
    public void linesFromFile() {
        try (Stream<String> lines = Files.lines(path())) {
            // lines.forEach(System.out::println);
            long count = lines.filter(x -> x.length() < 50 && x.length() > 1).count();
            System.out.println(count);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Ignore
    public void mapExample() {
        String[] strings = "meu pé de laranja lima".split(" ");
        Stream.of(strings).map(x -> x.toUpperCase()).forEach(System.out::print);
    }

    @Test
    @Ignore
    public void flatMapExample() {
        try (Stream<String> lines = Files.lines(path())) {
            lines.flatMap(x -> asList(x.split(" ")).stream()).limit(10).forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Ignore
    public void limitSkipConcat() {
        Stream<Double> limit = Stream.generate(Math::random).limit(10);

        limit.map(x -> (int) (100 * x)).skip(8).forEach(System.out::println);

        Stream.concat(Arrays.asList(1, 2, 3).stream(), Arrays.asList(4, 5, 6).stream()).forEach(System.out::print);

    }

    @Test
    @Ignore
    public void distinctSorted() {
        Stream<Integer> stream = Arrays.asList(1, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3).stream();

        stream.distinct().forEach(System.out::println);

        System.out.println("------------");
        stream = Arrays.asList(1, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3).stream();
        stream.distinct().sorted(Comparator.reverseOrder()).forEach(System.out::println);

        System.out.println("------------");
        Stream<String> stream2 = asList("Meu pé de laraja lima".split(" ")).stream();
        stream2.map(String::toLowerCase).sorted(Comparator.naturalOrder()).forEach(System.out::println);

    }

    @Test
    @Ignore
    public void countMaxMin() {
        Stream<Integer> stream = Arrays.asList(1, 2, 3, 123, 55555, 23123, 13213, 23123, 111, 3).stream();
        System.out.println("------------");
        System.out.println(stream.count());

        stream = Arrays.asList(1, 2, 3, 123, 55555, 23123, 13213, 23123, 111, 3).stream();
        System.out.println(stream.max(Comparator.naturalOrder()).get());

        stream = Arrays.asList(1, 2, 3, 123, 55555, 23123, 13213, 23123, 111, 3).stream();
        System.out.println(stream.min(Comparator.naturalOrder()).get());

    }

    @Test
    @Ignore
    public void findFirstFindAny() {
        List<Double> collect = Stream.generate(Math::random).limit(100000).collect(Collectors.toList());

        Optional<Double> optional = collect.stream().filter(x -> x > 0.9).findFirst();
        System.out.println(optional.get());

        optional = collect.parallelStream().filter(x -> x > 0.9).findAny();
        System.out.println(optional.get());
    }

    @Test
    @Ignore
    public void anyAllNoneMatch() {
        List<Double> collect = Stream.generate(Math::random).limit(100000).collect(Collectors.toList());

        boolean anyMatch = collect.stream().anyMatch(x -> x < 0.03);
        System.out.println(anyMatch);

        boolean allMatch = collect.stream().allMatch(x -> x < 1);
        System.out.println(allMatch);

        boolean noneMatch = collect.stream().noneMatch(x -> x > 1);
        System.out.println(noneMatch);
    }

    @Test
    @Ignore
    public void optional() {

        // Optional<String> optionalString = Optional.<String>empty();
        Optional<String> optionalString = Optional.<String>of("some value");

        if (optionalString.isPresent()) {
            System.out.println(optionalString.get());
        }

        optionalString.ifPresent(System.out::println);

        List<String> list = new ArrayList<>();
        optionalString.ifPresent(list::add);

        System.out.println(list.size());
    }

    @Test
    @Ignore
    public void optionalOrElse() {

        Optional<String> optionalString = Optional.<String>empty();
        String orElse = optionalString.orElse("bla");

        System.out.println(orElse);
        String orElseGet = optionalString.orElseGet(() -> System.getProperty("user.dir"));

        System.out.println(orElseGet);
        try {
            optionalString.orElseThrow(IllegalArgumentException::new);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    @Ignore
    public void reduce() {
        Stream<Integer> stream = Arrays.asList(1, 2, 3, 4, 5, 6).stream();

        Optional<Integer> reduce = stream.reduce((x, y) -> x + y);
        reduce.ifPresent(System.out::println);

        stream = Arrays.asList(1, 2, 3, 4, 5, 6).stream();

        Integer i = stream.reduce(5, (x, y) -> x + y);
        System.out.println(i);

    }

    @Test
    @Ignore
    public void collect() {
        List<String> list = Arrays.asList("1", "2", "3", "4", "5", "6");
        Stream<String> stream = list.stream();

        Long collect = stream.mapToLong(Long::parseLong).sum();
        System.out.println(collect);

        stream = list.stream();
        System.out.println(stream.collect(Collectors.joining(" | ")));

        String[] arr = {"4", "5", "6", "1", "2", "3"};
        stream = Arrays.stream(arr);
        System.out.println(stream.collect(Collectors.toList()));

        stream = Arrays.stream(arr);
        System.out.println(stream.collect(Collectors.toSet()));

        stream = Arrays.stream(arr);
        TreeSet<String> collect1 = stream.collect(Collectors.toCollection(TreeSet::new));
        System.out.println(collect1);

    }

    @Test
    @Ignore
    public void collectToMap() {
        Person[] people = {new Person("joao", 1), new Person("paulo", 2), new Person("daniel", 3)};

        Map<Integer, String> map = Arrays.stream(people).collect(Collectors.toMap(Person::getId, Person::getName));
        map.entrySet().stream().forEach(System.out::println);


    }

    @Test
    @Ignore
    public void grouping() {
        Stream<Locale> locales = Stream.of(Locale.getAvailableLocales());
        Map<String, List<Locale>> countryToLocales = locales.collect(
                groupingBy(Locale::getCountry));
        countryToLocales.forEach((x, y) -> System.out.println(x + " : " + y));
    }

    @Test
    @Ignore
    public void groupingCount() {
        Stream<Locale> locales = Stream.of(Locale.getAvailableLocales());

        Map<String, Long> countryToLocaleCounts = locales.collect(
                groupingBy(Locale::getCountry, counting()));
        countryToLocaleCounts.forEach((x, y) -> System.out.println(x + " : " + y));
    }

    @Test
    @Ignore
    public void parallel() {

        try (Stream<String> lines = Files.lines(path())) {

            List<String> strings = lines.flatMap(x -> asList(x.split(" ")).stream()).collect(Collectors.toList());
            int[] shortWords = new int[12];
            int[] shortWordsParallel = new int[12];

            Stream<String> stream = strings.stream();
            Stream<String> parallelStream = strings.parallelStream();


            stream.forEach(x -> {
                if (x.length() < 12) shortWords[x.length()]++;
            });

            parallelStream.forEach(x -> {
                if (x.length() < 12) shortWordsParallel[x.length()]++;
            });

            IntStream.range(0, 12).forEach(x -> {
                System.out.println(shortWords[x] + " : " + shortWordsParallel[x]);
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Test
    @Ignore
    public void countWords() {

        try (Stream<String> lines = Files.lines(path())) {
            lines.parallel()
                    .flatMap(x -> asList(x.split(" ")).stream())
                    .filter(x -> !x.isEmpty())
                    .collect(groupingByConcurrent(identity(), counting()))
                    .entrySet().stream()
                    .sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue()))
                    .limit(10).map(x -> x.getKey() + ":" + x.getValue())
                    .forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class Person {

        public String name;
        public Integer id;
        public Person(String name, Integer id) {
            this.name = name;
            this.id = id;
        }

        String getName() {
            return name;
        }

        Integer getId() {
            return id;
        }

    }

    private Path path() throws URISyntaxException {
        return Paths.get(this.getClass().getResource("/alice.txt").toURI());
    }
}
