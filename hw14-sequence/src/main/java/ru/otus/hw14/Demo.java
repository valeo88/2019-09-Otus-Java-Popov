package ru.otus.hw14;

public class Demo {
    private static final String FIRST_SEQ_NAME = "seq1";
    private static final String SECOND_SEQ_NAME = "seq2";

    public static void main(String[] args) {
        SequenceGenerator<Integer> sequenceGenerator = new SwitchingSequenceGenerator(FIRST_SEQ_NAME, SECOND_SEQ_NAME);
        final Object monitor = new Object();

        SequenceConsumerThread<Integer> thread1 = new SequenceConsumerThread<>("t1", monitor,
                sequenceGenerator, FIRST_SEQ_NAME);
        SequenceConsumerThread<Integer> thread2 = new SequenceConsumerThread<>("t2", monitor,
                sequenceGenerator, SECOND_SEQ_NAME);

        thread1.start();
        thread2.start();
    }
}
