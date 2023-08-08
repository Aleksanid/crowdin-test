package ua.aleksanid.utility;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegExUtilitiesTest {
    static Stream<Arguments> testData() {
        return Stream.of(
                Arguments.of("hello*world", "\\Qhello\\E.*\\Qworld\\E"),
                Arguments.of("*hello*world*", "\\Q\\E.*\\Qhello\\E.*\\Qworld\\E.*\\Q\\E"),
                Arguments.of("helloworld", "\\Qhelloworld\\E"),
                Arguments.of("", "\\Q\\E"),
                Arguments.of("*", "\\Q\\E.*\\Q\\E"),
                Arguments.of("hello.*world", "\\Qhello.\\E.*\\Qworld\\E")
        );
    }

    @ParameterizedTest
    @MethodSource("testData")
    void testConvertWildcardToRegEx(String input, String expected) {
        String actual = RegExUtilities.convertWildcardToRegEx(input);
        assertEquals(expected, actual);
    }
}