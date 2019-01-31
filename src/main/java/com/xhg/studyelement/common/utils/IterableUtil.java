package com.xhg.studyelement.common.utils;

import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * 重写foreach循环
 * @author Colin.hu
 * @date scrollbar-plugin/15/2018
 */
public class IterableUtil {

    public static <E> void forEach(
            Iterable<? extends E> elements, BiConsumer<Integer, ? super E> action) {
        Objects.requireNonNull(elements);
        Objects.requireNonNull(action);

        int index = 0;
        for (E element : elements) {
            action.accept(index++, element);
        }
    }
}
