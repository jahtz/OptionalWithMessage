package de.uniwue.jpp.errorhandling;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface OptionalWithMessage<T> {

    boolean isPresent();
    boolean isEmpty();
    T get();
    T orElse(T def);
    T orElseGet(Supplier<? extends T> supplier);
    String getMessage();
    <S> OptionalWithMessage<S> map(Function<T, S> f);
    <S> OptionalWithMessage<S> flatMap(Function<T, OptionalWithMessage<S>> f);
    Optional<String> consume(Consumer<T> c);
    Optional<String> tryToConsume(Function<T, Optional<String>> c);

    static <T> OptionalWithMessage<T> of(T val) {
        if (val == null) throw new NullPointerException();
        else return new OptionalWithMessageVal<>(val);
    }

    static <T> OptionalWithMessage<T> ofMsg(String msg) {
        if (msg == null) throw new NullPointerException();
        else return new OptionalWithMessageMsg<>(msg);
    }

    static <T> OptionalWithMessage<T> ofNullable(T val, String msg) {
        if (msg == null) throw new NullPointerException();
        else if (val == null) return new OptionalWithMessageMsg<>(msg);
        else return new OptionalWithMessageVal<>(val);
    }

    static <T> OptionalWithMessage<T> ofOptional(Optional<T> opt, String msg) {
        if (opt == null || msg == null) throw new NullPointerException();
        else if (opt.isEmpty()) return new OptionalWithMessageMsg<>(msg);
        else return new OptionalWithMessageVal<>(opt.get());
    }

    static <T> OptionalWithMessage<List<T>> sequence(List<OptionalWithMessage<T>> list) {
        List<T> resultList = new ArrayList<>();
        StringBuilder resultString = new StringBuilder();
        boolean allWithValuesFlag = true;
        for (OptionalWithMessage<T> item: list) {
            if (item.isEmpty()) {
                allWithValuesFlag = false;
                resultString.append(item.getMessage());
                resultString.append(System.lineSeparator());
            } else {
                resultList.add(item.get());
            }
        }
        if (allWithValuesFlag) return new OptionalWithMessageVal<>(resultList);
        else {
            resultString.delete(resultString.lastIndexOf(System.lineSeparator()), resultString.length());
            return new OptionalWithMessageMsg<>(resultString.toString());
        }
    }
}
