package ru.otus.hw03;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.Notification;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/* Параметры запуска VM (для OOM примерно в 5 минут):
    -Xms64m
    -Xmx64m
    -XX:+UseG1GC

    -Xms64m
    -Xmx64m
    -XX:+UseConcMarkSweepGC

    -Xms64m
    -Xmx64m
    -XX:+UseParallelGC
* */
/** Приложение с медленной утечкой памяти.*/
public class MemoryLeakApp {

    public static void main(String[] args) throws InterruptedException {
        // включаем вывод мониторинга в System.out
        monitoreGC();

        List<String> list = new ArrayList<>();
        final int elementsToAdd = 10_000;
        final int elementsToRemove = (int) (0.25 * elementsToAdd);
        while (true) {
            // добавляем элементы
            for (int i = 1; i <= elementsToAdd; i++) {
                list.add(new String("Hello OTUS"));
            }
            // удаляем removeRatio элементов
            for (int i = 0; i < elementsToRemove; i++) {
                list.remove(i);
            }
            // имитируем отдых
            Thread.sleep(10);
        }
    }

    /** Включаем мониторинг GC */
    private static void monitoreGC() {
        // собираем события сборщиков
        Map<String, List<Notification>> gcNotifications = new HashMap<>();
        AtomicInteger minuteCnt = new AtomicInteger(0);
        // печатаем по таймеру раз в минуту результаты работы GC за минуту
        TimerTask task = new TimerTask() {
            public void run() {
                int lastMinute = minuteCnt.getAndIncrement();
                long currentTs = System.currentTimeMillis();
                for (String gcName : gcNotifications.keySet()) {
                    List<GarbageCollectionNotificationInfo> lastGCInfos =  gcNotifications.get(gcName)
                            .stream().filter(notification -> currentTs - notification.getTimeStamp() <= 60000L)
                            .map( notification -> {
                                return GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                            })
                            .collect(Collectors.toList());
                    System.out.println("GC name: " + gcName
                            + ", Minute: " + lastMinute
                            + ", Count: " + lastGCInfos.size()
                            + ", Duration (ms): " + lastGCInfos.stream().map(x -> x.getGcInfo().getDuration())
                                                        .reduce(Long::sum).orElse(0L));
                    }
                }
        };

        Timer timer = new Timer();
        timer.schedule(task,0, 60000L);

        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            System.out.println("GC name:" + gcbean.getName());
            gcNotifications.put(gcbean.getName(), new ArrayList<>());
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                    String gcName = info.getGcName();

                    gcNotifications.get(gcName).add(notification);

//                    String gcAction = info.getGcAction();
//                    String gcCause = info.getGcCause();
//
//                    long startTime = info.getGcInfo().getStartTime();
//                    long duration = info.getGcInfo().getDuration();
//
//                    System.out.println("start:" + startTime + " Name:" + gcName +
//                            ", action:" + gcAction + ", gcCause:" + gcCause + "(" + duration + " ms)");
                }
            };
            emitter.addNotificationListener(listener, null, null);
        }
    }

}
