package event.plus;

import event.plus.core.EventBus;
import event.plus.event.Event;
import event.plus.event.impl.*;
import net.fabricmc.api.ModInitializer;

public class EventPlus implements ModInitializer {

    @Override
    public void onInitialize() {
    }


    public static void sub(Object obj) {
        EventBus.sub(obj);
    }

    public static void unsub(Object obj) {
        EventBus.unsub(obj);
    }

    public static <T extends Event> T fire(T event) {
        return EventBus.fire(event);
    }

    public static boolean hasSubscribers(Class<? extends Event> eventType) {
        return EventBus.hasSubscribers(eventType);
    }

    public static final class Events {
        public static final Class<?> TICK = TickEvent.class;
        public static final Class<?> RENDER_HUD = RenderHudEvent.class;
        public static final Class<?> RENDER_WORLD = RenderWorldEvent.class;
        public static final Class<?> MOVE = MoveEvent.class;
        public static final Class<?> ATTACK = AttackEvent.class;
        public static final Class<?> CHAT = ChatEvent.class;
        public static final Class<?> PACKET = PacketEvent.class;
        public static final Class<?> KEY = KeyEvent.class;
        public static final Class<?> WORLD_LOAD = WorldLoadEvent.class;
        public static final Class<?> JUMP = JumpEvent.class;
        public static final Class<?> DEATH = DeathEvent.class;
        public static final Class<?> SWING_HAND = SwingHandEvent.class;
        public static final Class<?> MOUSE_CLICK = MouseClickEvent.class;
        public static final Class<?> MOUSE_SCROLL = MouseScrollEvent.class;

        private Events() {
        }
    }
}