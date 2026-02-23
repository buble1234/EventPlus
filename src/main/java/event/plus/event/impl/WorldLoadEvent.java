package event.plus.event.impl;

import event.plus.event.Event;
import net.minecraft.client.world.ClientWorld;
import org.jetbrains.annotations.Nullable;

public class WorldLoadEvent extends Event {
    @Nullable
    private final ClientWorld level;

    public WorldLoadEvent(@Nullable ClientWorld level) {
        this.level = level;
    }

    @Nullable
    public ClientWorld getLevel() {
        return level;
    }

    public boolean isUnload() {
        return level == null;
    }

    public boolean isLoad() {
        return level != null;
    }
}