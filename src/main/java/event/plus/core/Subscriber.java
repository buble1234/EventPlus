package event.plus.core;

import event.plus.event.Event;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;

public final class Subscriber {
    private final Object owner;
    private final MethodHandle handle;
    private final EventPriority priority;
    private final Class<?> eventType;

    public Subscriber(Object owner, Method method, EventPriority priority) {
        this.owner = owner;
        this.priority = priority;
        this.eventType = method.getParameterTypes()[0];

        try {
            method.setAccessible(true);
            this.handle = MethodHandles.lookup().unreflect(method);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("EventPlus: cannot access method " + method.getName() + " in " + owner.getClass().getSimpleName(), e);
        }
    }

    public void invoke(Event event) {
        try {
            handle.invoke(owner, event);
        } catch (Throwable t) {
            throw new RuntimeException("EventPlus: error firing " + eventType.getSimpleName() + " in " + owner.getClass().getSimpleName(), t);
        }
    }

    public Object owner() {
        return owner;
    }

    public EventPriority priority() {
        return priority;
    }

    public Class<?> eventType() {
        return eventType;
    }
}