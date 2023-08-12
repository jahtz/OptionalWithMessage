package de.uniwue.jpp.errorhandling;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class OptionalWithMessageVal<T> implements OptionalWithMessage<T>{
    private final T value;
    public OptionalWithMessageVal(T value) {
        this.value = value;
    }
    @Override
    public boolean isPresent() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public T orElse(T def) {
        return value;
    }

    @Override
    public T orElseGet(Supplier<? extends T> supplier) {
        return value;
    }

    @Override
    public String getMessage() {
        throw new NoSuchElementException();
    }

    @Override
    public <S> OptionalWithMessage<S> map(Function<T, S> f) {
        return new OptionalWithMessageVal<S>(f.apply(this.value));  // gibt ein OptionalWithMessageVal vom Typ T zurück, welches das Ergebnis einer Lambda Funktion mit Rückgabetyp T hält
    }

    @Override
    public <S> OptionalWithMessage<S> flatMap(Function<T, OptionalWithMessage<S>> f) {
        return f.apply(this.value);  // gibt die Value bzw Message eines OptionalWithMessage zurück, das in einer Lamda Funktion entsteht.
    }

    @Override
    public Optional<String> consume(Consumer<T> c) {
        c.accept(this.value); // führt eine Lamda Funktion c auf this.value aus
        return Optional.empty();
    }

    @Override
    public Optional<String> tryToConsume(Function<T, Optional<String>> c) {
        if (this.value != null) return c.apply(this.value);  // führt ein Lambda Funktion aus, welche den Rückgabetyp Optional<String>> hat (falls value != null)
        else return Optional.empty();
    }
}
