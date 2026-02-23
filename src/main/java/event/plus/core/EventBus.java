package event.plus.core;

import event.plus.event.Event;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public final class EventBus {
    private static final Map<Class<?>, Subscriber[]> REGISTRY = new ConcurrentHashMap<>();

    private static final Map<Class<?>, CopyOnWriteArrayList<Subscriber>> BUILDING = new ConcurrentHashMap<>();

    private EventBus() {
    }

    public static void sub(Object obj) {
        Class<?> clazz = obj.getClass();

        for (Method method : clazz.getDeclaredMethods()) {
            EventOn annotation = method.getAnnotation(EventOn.class);
            if (annotation == null) continue;

            if (method.getParameterCount() != 1) {
                throw new IllegalArgumentException("EventPlus: @EventOn method " + method.getName() + " must have exactly 1 parameter");
            }

            Class<?> eventType = method.getParameterTypes()[0];

            if (!Event.class.isAssignableFrom(eventType)) {
                throw new IllegalArgumentException("EventPlus: @EventOn method " + method.getName() + " parameter must extend Event");
            }

            Subscriber subscriber = new Subscriber(obj, method, annotation.priority());

            BUILDING.computeIfAbsent(eventType, k -> new CopyOnWriteArrayList<>()).add(subscriber);

            rebuild(eventType);
        }
    }

    public static void unsub(Object obj) {
        for (Map.Entry<Class<?>, CopyOnWriteArrayList<Subscriber>> entry : BUILDING.entrySet()) {
            entry.getValue().removeIf(s -> s.owner() == obj);
            rebuild(entry.getKey());
        }
    }

    public static void unsub(Object obj, Class<? extends Event> eventType) {
        CopyOnWriteArrayList<Subscriber> list = BUILDING.get(eventType);
        if (list == null) return;
        list.removeIf(s -> s.owner() == obj);
        rebuild(eventType);
    }

    public static <T extends Event> T fire(T event) {
        Subscriber[] subscribers = REGISTRY.get(event.getClass());
        if (subscribers == null || subscribers.length == 0) return event;

        for (Subscriber subscriber : subscribers) {
            if (event.isCancellable() && event.isCancelled()) break;
            subscriber.invoke(event);
        }

        return event;
    }

    public static boolean hasSubscribers(Class<? extends Event> eventType) {
        Subscriber[] arr = REGISTRY.get(eventType);
        return arr != null && arr.length > 0;
    }

    public static void clear() {
        REGISTRY.clear();
        BUILDING.clear();
    }

    private static void rebuild(Class<?> eventType) {
        CopyOnWriteArrayList<Subscriber> list = BUILDING.get(eventType);
        if (list == null || list.isEmpty()) {
            REGISTRY.remove(eventType);
            return;
        }

        Subscriber[] sorted = list.toArray(new Subscriber[0]);
        Arrays.sort(sorted, Comparator.comparingInt(s -> -s.priority().getLevel()));
        REGISTRY.put(eventType, sorted);
    }
}