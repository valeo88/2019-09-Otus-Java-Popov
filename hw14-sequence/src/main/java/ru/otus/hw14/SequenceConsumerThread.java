package ru.otus.hw14;

/** Thread, which consumes values from corresponded sequence and print them.*/
public class SequenceConsumerThread<T> extends Thread {
    private final String seqName;
    private final SequenceGenerator<T> sequenceGenerator;

    private final Object monitor;

    public SequenceConsumerThread(String name, Object monitor, SequenceGenerator<T> sequenceGenerator, String seqName) {
        super(name);
        this.monitor = monitor;
        this.seqName = seqName;
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (monitor) {
                // need to use monitor here because of time difference between consuming next value and printing it
                if (sequenceGenerator.hasNext(seqName)) {
                    T value = sequenceGenerator.next(seqName);
                    print(value);
                }
            }
        }
    }

    private void print(T value) {
        System.out.println(getName() + ": " + value);
    }
}
