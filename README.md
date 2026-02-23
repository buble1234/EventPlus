# EventPlus - Event Library for Minecraft

A fast, lightweight event bus library for Minecraft Fabric mods.

## What can it do
Subscribing objects to events, firing cancellable and non-cancellable events, priority-based listener ordering, and per-type unsubscription.

## Built-in Events
`TickEvent`, `RenderHudEvent`, `RenderWorldEvent`, `MoveEvent`, `AttackEvent`, `ChatEvent`, `PacketEvent`, `KeyEvent`, `WorldLoadEvent`, `JumpEvent`, `DeathEvent`, `SwingHandEvent`, `MouseClickEvent`, `MouseScrollEvent`

## How it works inside
**EventBus** - core of the library. Stores subscribers as arrays per event type for fast iteration. Sorts by `EventPriority` once on registration, not on every fire.

**Subscriber** - wraps a `MethodHandle` instead of `Method.invoke`. ~3-5x faster than reflection. Built once on `sub()`, reused forever.

**EventOn** - annotation that marks a method as an event listener. Takes an optional `priority` parameter.

**EventPhase** - `PRE` / `POST` enum shared across all events that have a phase (render, tick).

**CancellableEvent** - base class for events that can be stopped mid-chain via `cancel()`. Stopped immediately when `isCancelled()` returns true — remaining listeners are skipped.

## How to use

```groovy
dependencies {
    modImplementation 'com.github.yourusername:EventPlus:1.21.10'
}
```

Subscribe and listen:
```java
public class MyModule {

    public MyModule() {
        EventPlus.sub(this);
    }

    @EventOn
    public void onTick(TickEvent e) {
        if (e.getPhase() != EventPhase.PRE) return;
        // runs every tick
    }

    @EventOn(priority = EventPriority.HIGH)
    public void onJump(JumpEvent e) {
        e.cancel(); // prevent jump
    }

    @EventOn
    public void onPacket(PacketEvent e) {
        if (e.isSend()) {
            // outgoing packet
        }
    }

    @EventOn
    public void onRender(RenderHudEvent e) {
        if (e.getPhase() != EventPhase.POST) return;
        DrawContext ctx = e.getGraphics();
        // draw here
    }
}
```

Unsubscribe:
```java
EventPlus.unsub(this);
```

Fire a custom event:
```java
EventPlus.fire(new MyCustomEvent());
```

Create a custom event:
```java
// Non-cancellable
public class MyEvent extends Event {
    private final String data;
    public MyEvent(String data) { this.data = data; }
    public String getData() { return data; }
}

// Cancellable
public class MyEvent extends CancellableEvent {
    ...
}
```
