package me.legit.bungeefunmod.commands.fun;

import me.legit.bungeefunmod.Bungeefunmod;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.ChatColor;

import java.util.List;
import java.util.Random;

public class EightBallCommand extends Command {

    public EightBallCommand() {
        super("8ball");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Usage: /8ball <question>");
            return;
        }

        List<String> answers = Bungeefunmod.getInstance().getConfigManager()
                .getMessages().getStringList("8ball-answers");

        if (answers.isEmpty()) {
            sender.sendMessage(ChatColor.RED + "No answers configured!");
            return;
        }

        String randomAnswer = answers.get(new Random().nextInt(answers.size()));
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "🎱 " + randomAnswer);
    }
}
