package me.bramhaag.guilds.listeners;

import me.bramhaag.guilds.Main;
import me.bramhaag.guilds.commands.CommandChat;
import me.bramhaag.guilds.guild.Guild;
import me.bramhaag.guilds.guild.GuildRole;
import me.bramhaag.guilds.message.Message;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        Guild guild = Guild.getGuild(player.getUniqueId());
        if (guild == null) {
            return;
        }

        if (CommandChat.guildchat.contains(player.getUniqueId())) {
            e.setCancelled(true);
            if (guild == null) {
                Message.sendMessage(player, Message.COMMAND_ERROR_NO_GUILD);
                return;
            }

            GuildRole role = GuildRole.getRole(guild.getMember(player.getUniqueId()).getRole());
            if (!role.canChat()) {
                Message.sendMessage(player, Message.COMMAND_ERROR_ROLE_NO_PERMISSION);
                return;
            }

            String message = e.getMessage();
            guild.sendMessage(Message.COMMAND_CHAT_MESSAGE.replace("{role}", GuildRole.getRole(guild.getMember(player.getUniqueId()).getRole()).getName(), "{player}", player.getName(), "{message}", message));
            return;
        } else
            Bukkit.broadcastMessage("DEBUG:: Player " + player.getName() + "  is not in the guild chat list.");

        String prefixFormat = ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("prefix.format").replace("{prefix}", guild.getPrefix()));
        String chatFormat = Main.getInstance().getConfig().getString("chat.format").replace("{guild}", prefixFormat);

        e.setFormat(chatFormat);
    }

}
