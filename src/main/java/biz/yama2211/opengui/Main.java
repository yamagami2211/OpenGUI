package biz.yama2211.opengui;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener
{
	//
public void onEnable(){
	getServer().getPluginManager().registerEvents(this, this);

}

public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

	if (!(sender instanceof Player)) {
		sender.sendMessage(ChatColor.RED + "このコマンドはゲーム内から実行してください。");
	} else {
		Player player = (Player)sender;
	//エンダーチェストを開く
	if (cmd.getName().equalsIgnoreCase("enderchest")) {

		if (args.length == 0) {
 {
			if ((sender.hasPermission("opengui.enderchest")) || (sender.isOp())) { //Pex1

			player.openInventory(player.getEnderChest());
			sender.sendMessage(ChatColor.GREEN + "エンダーチェストを開きました" );
			}//Pex1
			else {
				sender.sendMessage(ChatColor.RED + "権限がありません。");
			}
	}
		}
	//(command) <PlayerName> で<PlayerName>のエンダーチェストを開く
	if (args.length == 1)
	{
		if ((sender.hasPermission("opengui.enderchest.other")) || (sender.isOp())) { //Pex1.5
	    try
	    {
	        Player targetplayer = player.getServer().getPlayer(args[0]);

	        player.openInventory(targetplayer.getEnderChest());
	        //player.openInventory(targetplayer.getEnderChest());
	        sender.sendMessage(ChatColor.GOLD + targetplayer.getName()+ ChatColor.GREEN + " のエンダーチェストを開きました" );

	    }
	    catch (Exception err)
	    {
	      player.sendMessage(ChatColor.GOLD + args[0] + ChatColor.RED + " はオフラインです。");
	    }

	}//Pex1.5
		else {
			sender.sendMessage(ChatColor.RED + "権限がありません。");
		}
		}

	}
    	//インベントリを開く
	if(cmd.getName().equalsIgnoreCase("inventory")) {

		if(args.length == 0) {
			if ((sender.hasPermission("opengui.inventory")) || (sender.isOp())) { //Pex2
			player.openInventory(player.getInventory());
			sender.sendMessage(ChatColor.GREEN +"インベントリを開きました" );
		} //pex2
			else {
				sender.sendMessage(ChatColor.RED + "権限がありません。");
			}
	}
		//(command) <PlayerName> で<PlayerName>のインベントリを開く
	if (args.length == 1)
	{
		if ((sender.hasPermission("opengui.inventory.other")) || (sender.isOp())) { //Pex2.5
	    try
	    {
	        Player targetplayer = player.getServer().getPlayer(args[0]);

	        player.openInventory(targetplayer.getInventory());
	        sender.sendMessage(ChatColor.GOLD +targetplayer.getName() + ChatColor.GREEN + "のインベントリを開きました");

	    }
	    catch (Exception err)
	    {
	      player.sendMessage(ChatColor.GOLD + args[0] + ChatColor.RED + " はオフラインです。");
	    }
	}//pex2.5
		else {
			sender.sendMessage(ChatColor.RED + "権限がありません。");
		}

		}

	}

    	//ワークベンチ(作業台)を開く
	if(cmd.getName().equalsIgnoreCase("workbench")) {

		if ((sender.hasPermission("opengui.workbench")) || (sender.isOp())) { //Pex3
		player.openWorkbench(null, true);
		sender.sendMessage(ChatColor.GREEN +"ワークベンチを開きました");
		}//Pex3
		else {
			sender.sendMessage(ChatColor.RED + "権限がありません。");
		}
	}
    	//エンチャントテーブルを開く (ラピスラズリ必須)
	if(cmd.getName().equalsIgnoreCase("enchanting")) {

		if ((sender.hasPermission("opengui.enchanting")) || (sender.isOp())) { //Pex4
		player.openEnchanting(null, true);
		sender.sendMessage(ChatColor.GREEN +"エンチャントテーブルを開きました");
		}//Pex4
		else {
			sender.sendMessage(ChatColor.RED + "権限がありません。");
		}
	}


}
return false;
}
}
