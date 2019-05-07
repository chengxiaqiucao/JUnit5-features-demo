package qiucao.JUnit5_features_demo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.EnumSource.Mode;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TestWithParameterize {

	@Nested
	class ValueSourcesExampleTest {

		@ParameterizedTest
		@ValueSource(ints = { 2, 4, 8 })
		void testNumberShouldBeEven(int num) {
			assertEquals(0, num % 2);
		}

		@ParameterizedTest
		@ValueSource(strings = { "Radar", "Rotor", "Tenet", "Madam", "Racecar" })
		void testStringShouldBePalindrome(String word) {
			assertEquals(isPalindrome(word), true);
		}

		@ParameterizedTest
		@ValueSource(doubles = { 2.D, 4.D, 8.D })
		void testDoubleNumberBeEven(double num) {
			assertEquals(0, num % 2);
		}

		boolean isPalindrome(String word) {
			return word.toLowerCase().equals(new StringBuffer(word.toLowerCase()).reverse().toString());
		}
	}
}

class EnumSourcesExampleTest {

	@ParameterizedTest(name = "[{index}] TimeUnit: {arguments}")
	@EnumSource(TimeUnit.class)
	void testTimeUnitMinimumNanos(TimeUnit unit) {
		assertTrue(unit.toMillis(2000000L) > 1);
	}

	@ParameterizedTest
	@EnumSource(value = TimeUnit.class, names = { "SECONDS", "MINUTES" })
	void testTimeUnitJustSecondsAndMinutes(TimeUnit unit) {
		assertTrue(EnumSet.of(TimeUnit.SECONDS, TimeUnit.MINUTES).contains(unit));
		assertFalse(EnumSet
				.of(TimeUnit.DAYS, TimeUnit.HOURS, TimeUnit.MILLISECONDS, TimeUnit.NANOSECONDS, TimeUnit.MICROSECONDS)
				.contains(unit));
	}

	@ParameterizedTest
	@EnumSource(value = TimeUnit.class, mode = Mode.EXCLUDE, names = { "SECONDS", "MINUTES" })
	void testTimeUnitExcludingSecondsAndMinutes(TimeUnit unit) {
		assertFalse(EnumSet.of(TimeUnit.SECONDS, TimeUnit.MINUTES).contains(unit));
		assertTrue(EnumSet
				.of(TimeUnit.DAYS, TimeUnit.HOURS, TimeUnit.MILLISECONDS, TimeUnit.NANOSECONDS, TimeUnit.MICROSECONDS)
				.contains(unit));
	}

	@ParameterizedTest
	@EnumSource(value = TimeUnit.class, mode = Mode.MATCH_ALL, names = ".*SECONDS")
	void testTimeUnitIncludingAllTypesOfSecond(TimeUnit unit) {
		assertFalse(EnumSet.of(TimeUnit.DAYS, TimeUnit.HOURS, TimeUnit.MINUTES).contains(unit));
		assertTrue(EnumSet.of(TimeUnit.SECONDS, TimeUnit.MILLISECONDS, TimeUnit.NANOSECONDS, TimeUnit.MICROSECONDS)
				.contains(unit));
	}

}

class CsvSourceExampleTest {

	Map<Long, String> idToUsername = new HashMap<>();

	{
		idToUsername.put(1L, "Selma");
		idToUsername.put(2L, "Lisa");
		idToUsername.put(3L, "Tim");
	}

	@ParameterizedTest
	@CsvSource({ "1,Selma", "2,Lisa", "3,Tim" })
	void testUsersFromCsv(long id, String name) {
		assertTrue(idToUsername.containsKey(id));
		assertTrue(idToUsername.get(id).equals(name));
	}
}

class CsvFileSourceExampleTest {

	Map<Long, String> idToUsername = new HashMap<>();

	{
		idToUsername.put(1L, "Selma");
		idToUsername.put(2L, "Lisa");
		idToUsername.put(3L, "Tim");
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/users.csv")
	void testUsersFromCsv(long id, String name) {
		assertTrue(idToUsername.containsKey(id));
		assertTrue(idToUsername.get(id).equals(name));
	}
}

class MethodSourceExampleTest {
	@ParameterizedTest
	@MethodSource("stringGenerator")
	void shouldNotBeNullString(String arg) {
		assertNotNull(arg);
	}

	@ParameterizedTest
	@MethodSource("intGenerator")
	void shouldBeNumberWithinRange(int arg) {
		assertAll(() -> assertTrue(arg > 0), () -> assertTrue(arg <= 10));
	}

	@ParameterizedTest(name = "[{index}] user with id: {0} and name: {1}")
	@MethodSource("userGenerator")
	void shouldUserWithIdAndName(long id, String name) {
		assertNotNull(id);
		assertNotNull(name);
	}

	static Stream<String> stringGenerator() {
		return Stream.of("hello", "world", "let's", "test");
	}

	static IntStream intGenerator() {
		return IntStream.range(1, 10);
	}

	static Stream<Arguments> userGenerator() {
		return Stream.of(Arguments.of(1L, "Sally"), Arguments.of(2L, "Terry"), Arguments.of(3L, "Fred"));
	}
}

class ArgumentsSourceExampleTest {

	@ParameterizedTest
	@ArgumentsSource(CustomArgumentsGenerator.class)
	void testGeneratedArguments(double number) throws Exception {
		assertFalse(number == 0.D);
		assertTrue(number > 0);
		assertTrue(number < 1);
	}

	static class CustomArgumentsGenerator implements ArgumentsProvider {

		@Override
		public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
			return Stream.of(Math.random(), Math.random(), Math.random(), Math.random(), Math.random())
					.map(Arguments::of);
		}
	}
}