package com.daitangroup;

import static java.util.Arrays.asList;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Stream;

import org.junit.Ignore;
import org.junit.Test;

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
		Stream.of(strings).map(x -> x.toString().toUpperCase()).forEach(System.out::print);
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
//	@Ignore
	public void distinctSorted() {
		Stream<Integer> stream = Arrays.asList(1, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3).stream();

		stream.distinct().forEach(System.out::println);

		System.out.println("------------");		
		stream = Arrays.asList(1, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3).stream();		
		stream.distinct().sorted(Comparator.comparing(Function.<Integer> identity()).reversed()).forEach(System.out::println);
		
		System.out.println("------------");		
		Stream<String> stream2 = asList("Meu pé de laraja lima".split(" ")).stream();		
		stream2.sorted(Comparator.comparing(String::length).reversed()).forEach(System.out::println);
		

	}

	// @Test
	// @Ignore
	// public void countWords() {
	// try (Stream<String> lines = Files.lines(path())) {
	// lines.flatMap(x -> asList(x.split(" ")).stream()).filter(x ->
	// !x.isEmpty())
	// .collect(Collectors.groupingBy(Function.identity(),
	// Collectors.counting())).entrySet().stream()
	// .sorted(new Comparator<Entry<String, Long>>() {
	//
	// @Override
	// public int compare(Entry<String, Long> o1, Entry<String, Long> o2) {
	// return o2.getValue().compareTo(o1.getValue());
	// }
	//
	// }).limit(10).map(x -> x.getKey() + ":" +
	// x.getValue()).forEach(System.out::println);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

	private Path path() throws URISyntaxException {
		return Paths.get(this.getClass().getResource("/alice.txt").toURI());
	}

}
