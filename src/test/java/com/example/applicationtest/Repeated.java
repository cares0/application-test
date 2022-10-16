package com.example.applicationtest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

public class Repeated {

    @DisplayName("repeated test")
    @RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetitions}")
    void repeatedTest(RepetitionInfo repetitionInfo) {
        System.out.println("repetitionInfo.getCurrentRepetition() = "
                + repetitionInfo.getCurrentRepetition());

        System.out.println("repetitionInfo.getTotalRepetitions() = "
                + repetitionInfo.getTotalRepetitions());
    }

    @DisplayName("parameterized Test")
    @ParameterizedTest(name = "[{index}] {displayName}, message={0}")
    @ValueSource(strings = {"A", "B", "C", "D", "E"})
    void parameterizedTest(String message) {
        Assertions.assertThat(message).isEqualTo("A");
    }

    @DisplayName("parameterized Test")
    @ParameterizedTest(name = "[{index}] {displayName}, message={0}")
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void valueSource(int message) {
        Assertions.assertThat(message % 2).isEqualTo(1);
    }

    @DisplayName("parameterized Test")
    @ParameterizedTest(name = "[{index}] {displayName}, message={0}")
    @ValueSource(strings = {"A", "B", "C", "D", "E"})
    @NullAndEmptySource
    void nullAndEmptySource(String message) {
        Assertions.assertThat(message).isEqualTo(null);
    }

    @DisplayName("argument conversion1 Test")
    @ParameterizedTest(name = "[{index}] {displayName}, message={0}")
    @ValueSource(strings = {"A", "B", "C", "D", "E"})
    void argumentConversion(@ConvertWith(TestDtoConverter.class) TestDto testDto) {
        System.out.println("testDto.getName() = " + testDto.getName());
    }

    public static class TestDtoConverter extends SimpleArgumentConverter {
        @Override
        protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            Assertions.assertThat(targetType).isEqualTo(TestDto.class).as("TestDto로만 캐스팅이 가능합니다.");
            return new TestDto(source.toString());
        }
    }

    @DisplayName("csvSource1 Test")
    @ParameterizedTest(name = "[{index}] {displayName}, message={0}. {1}")
    @CsvSource({"1, A", "2, B", "3, C", "4, D", "5, E"})
    void csvSource1(Integer num, String str) {
        System.out.println("num = " + num);
        System.out.println("str = " + str);
    }

    @DisplayName("csvSource2 Test")
    @ParameterizedTest(name = "[{index}] {displayName}, message={0}. {1}")
    @CsvSource({"1, A", "2, B", "3, C", "4, D", "5, E"})
    void csvSource2(ArgumentsAccessor accessor) {
        Integer integer = accessor.getInteger(0);
        String string = accessor.getString(1);

        System.out.println("integer = " + integer);
        System.out.println("string = " + string);
    }

    @DisplayName("csvSource3 Test")
    @ParameterizedTest(name = "[{index}] {displayName}, message={0}. {1}")
    @CsvSource({"1, A", "2, B", "3, C", "4, D", "5, E"})
    void csvSource3(@AggregateWith(TestDtoAggregator.class) TestDto testDto) {
        System.out.println("testDto.getName() = " + testDto.getName());
        System.out.println("testDto.getAge() = " + testDto.getAge());
    }

    static class TestDtoAggregator implements ArgumentsAggregator {
        
        @Override
        public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context) throws ArgumentsAggregationException {
            return new TestDto(accessor.getString(1), accessor.getInteger(0));
        }
    }
    

}
