package ubiquitaku.chohan;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Chohan extends JavaPlugin {
    boolean mc = false;
    int money;
    VaultManager vault;
    List<Player> c = new ArrayList<>();
    List<Player> h = new ArrayList<>();

    @Override
    public void onEnable() {
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("mc")) {
            if (args.length == 0) {
                sender.sendMessage("/mc new <金額> : 丁半を開始します\n/mc c : 丁に参加します"
                +"\n/mc h : 半に参加します");
                return true;
            }
            if (args[0].equals("c")) {
                if (!(mc)) {
                    sender.sendMessage("現在丁半は行われていません");
                    return true;
                }
                if (money > vault.getBalance(getServer().getPlayerUniqueId(sender.getName()))) {
                    sender.sendMessage("所持金が足りないため参加できません");
                    return true;
                }
                Player p = (Player) sender;
                if (c.contains(p)) {
                    sender.sendMessage("あなたはすでに丁に参加しています");
                    return true;
                }
                if (h.contains(p)) {
                    h.remove(p);
                    c.add(p);
                    sender.sendMessage("半から丁に変更しました");
                    return true;
                }
                //金取る
                c.add(p);
                sender.sendMessage("丁に参加しました");
                return true;
            }

            if (args[0].equals("h")) {
                if (!(mc)) {
                    sender.sendMessage("現在丁半は行われていません");
                    return true;
                }
                if (money > vault.getBalance(getServer().getPlayerUniqueId(sender.getName()))) {
                    sender.sendMessage("所持金が足りないため参加できません");
                    return true;
                }
                Player p = (Player) sender;
                if (h.contains(p)) {
                    sender.sendMessage("あなたはすでに半に参加しています");
                    return true;
                }
                if (c.contains(p)) {
                    c.remove(p);
                    h.add(p);
                    sender.sendMessage("丁から半に変更しました");
                }
                //金取る
                h.add(p);
                sender.sendMessage("半に参加しました");
                return true;
            }

            if (args[0].equals("new")) {
                if (mc) {
                    sender.sendMessage("丁半が行われているため開始できません");
                    return true;
                }
                if (args.length != 2) {
                    sender.sendMessage("金額が入力されていません");
                    return true;
                }
                int m = Integer.parseInt(args[1]);
                if (m > vault.getBalance(getServer().getPlayerUniqueId(sender.getName()))) {
                    sender.sendMessage("所持金を超える丁半の開始はできません");
                    return true;
                }
                mc = true;
                money = m;
                Bukkit.broadcastMessage(sender.getName()+"が"+m+"円の丁半を開始しました");
                //i want to timer
            }
        }
        return true;
    }
}
