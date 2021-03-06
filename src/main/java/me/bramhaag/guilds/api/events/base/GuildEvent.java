package me.bramhaag.guilds.api.events.base;

import me.bramhaag.guilds.guild.Guild;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public abstract class GuildEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private Guild guild;
    private boolean cancelled;

    public GuildEvent(Player player, Guild guild) {
        super(player);

        this.guild = guild;
        this.cancelled = false;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
