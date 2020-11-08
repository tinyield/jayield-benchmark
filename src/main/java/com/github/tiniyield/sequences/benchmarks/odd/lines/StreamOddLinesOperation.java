package com.github.tiniyield.sequences.benchmarks.odd.lines;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

public class StreamOddLinesOperation {

    static class StreamOddLines<T> extends Spliterators.AbstractSpliterator<T> {

        private static long odd(long l) {
            return l == Long.MAX_VALUE ? l : (l + 1) / 2;
        }

        final Consumer<T> doNothing = item -> {
        };
        final Spliterator<T> source;
        boolean isOdd;

        StreamOddLines(Spliterator<T> source) {
            super(odd(source.estimateSize()), source.characteristics());
            this.source = source;
        }

        @Override
        public boolean tryAdvance(Consumer<? super T> action) {
            if (!source.tryAdvance(doNothing)) return false;
            return source.tryAdvance(action);
        }

        @Override
        public void forEachRemaining(Consumer<? super T> action) {
            isOdd = false;
            source.forEachRemaining(item -> {
                if(isOdd) action.accept(item );
                isOdd = !isOdd;
            });
        }
    }

}
