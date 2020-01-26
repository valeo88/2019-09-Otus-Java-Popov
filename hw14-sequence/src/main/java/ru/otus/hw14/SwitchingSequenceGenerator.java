package ru.otus.hw14;

import java.util.Collections;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/** Generates the cyclic sequences of integers from 1 to 10.
 * New value first appears in firstSeqName sequence, and next in secondSeqName.*/
public class SwitchingSequenceGenerator implements SequenceGenerator<Integer> {
    private final int minValue = 1;
    private final int maxValue = 10;
    private int step = 1;

    private final String firstSeqName;
    private final String secondSeqName;

    private Map<String, Queue<Integer>> seqQueue = new ConcurrentHashMap<>();

    public SwitchingSequenceGenerator(String firstSeqName, String secondSeqName) {
        this.firstSeqName = firstSeqName;
        this.secondSeqName = secondSeqName;

        seqQueue.put(this.firstSeqName, new ConcurrentLinkedQueue<>(Collections.singletonList(minValue)));
        seqQueue.put(this.secondSeqName, new ConcurrentLinkedQueue<>());
    }

    @Override
    public boolean hasNext(String seqName) {
        return seqQueue.get(seqName)!=null && !seqQueue.get(seqName).isEmpty();
    }

    @Override
    public Integer next(String seqName) {
        if (hasNext(seqName)) {
            Integer value = seqQueue.get(seqName).poll();
            if (firstSeqName.equals(seqName)) {
                // switch to second queue
                seqQueue.get(secondSeqName).add(value);
            } else {
                // switch to first queue and change value
                if (value == maxValue && step > 0 || value == minValue && step < 0) {
                    step = -step;
                }
                seqQueue.get(firstSeqName).add(value + step);
            }
            return value;
        }
        return null;
    }
}
