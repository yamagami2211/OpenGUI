/*
*
*  OpenGUI
*  WebSite1: https://forum.civa.jp/viewtopic.php?f=15&t=368#p1251
*  WebSite2: https://mc.yama2211.net/contents/OpenGUI.html
*  SourceCode: https://github.com/yamagami2211/OpenGUI/
*
*/
package biz.yama2211.opengui;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dye;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {


	//起動処理
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		saveDefaultConfig();
	}

	/*
	 * enchantingコマンド実行時に変化
	 */
	private boolean en = false;
	private String prefix = "[OpenGUI]";

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("opengui")) {
			PluginDescriptionFile yml = getDescription();
			player.sendMessage(ChatColor.GREEN + "PluginVersion : " +ChatColor.DARK_PURPLE + yml.getVersion());
			player.sendMessage(ChatColor.AQUA + "/opengui reload" +ChatColor.WHITE + " : Configをリロード");
			player.sendMessage(ChatColor.AQUA + "/enderchest <PlayerName>" + ChatColor.WHITE + " : 自分<PlayerName>のエンダーチェストを開く");
			player.sendMessage(ChatColor.AQUA + "/inventory <PlayerName>" + ChatColor.WHITE + " : 自分<PlayerName>のインベントリーを開く");
			player.sendMessage(ChatColor.AQUA + "/workbench" + ChatColor.WHITE + " : ワークベンチ(作業台)を開く");
			player.sendMessage(ChatColor.AQUA + "/enchanting" + ChatColor.WHITE + " : エンチャントテーブルを開く");
		}
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("reload")) {
				if ((sender.hasPermission("opengui.reload")) || (sender.isOp())) {

					reloadConfig();
					String Confre = getConfig().getString("message"+ ".reload");
					Confre = Confre.replaceAll("%prefix",prefix);
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Confre));

				}
			}
		}
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "このコマンドはゲーム内から実行してください。");
		} else {

			//エンダーチェストを開く
			if (cmd.getName().equalsIgnoreCase("enderchest")) {

				if (args.length == 0) {
					{
						if ((sender.hasPermission("opengui.enderchest")) || (sender.isOp())) { //Pex1

							player.openInventory(player.getEnderChest());

							String EnderOpMy = getConfig().getString("message"+ ".enderchest" + ".my");
							EnderOpMy = EnderOpMy.replaceAll("%prefix",prefix);
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', EnderOpMy));
						} //Pex1
						else {
							String NoPex = getConfig().getString("message"+ ".nopermission");
							NoPex = NoPex.replaceAll("%prefix",prefix);
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', NoPex));
						}
					}
				}
				//(command) <PlayerName> で<PlayerName>のエンダーチェストを開く
				if (args.length == 1) {
					if ((sender.hasPermission("opengui.enderchest.other")) || (sender.isOp())) { //Pex1.5
						try {
							Player targetplayer = player.getServer().getPlayer(args[0]);

							player.openInventory(targetplayer.getEnderChest());

							String EnderOpOth = getConfig().getString("message"+ ".enderchest" + ".other");
							EnderOpOth = EnderOpOth.replace("%prefix",prefix);
							EnderOpOth = EnderOpOth.replaceAll("%player",args[0]);
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', EnderOpOth));


						} catch (Exception err) {
							Player targetplayer = player.getServer().getPlayer(args[0]);
							String EnderOpOff = getConfig().getString("message"+ ".enderchest" + ".offline");
							EnderOpOff = EnderOpOff.replace("%prefix",prefix);
							EnderOpOff = EnderOpOff.replaceAll("%player",args[0]);
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', EnderOpOff));
						}

					} //Pex1.5
					else {
						String NoPex = getConfig().getString("message"+ ".nopermission");
						NoPex = NoPex.replaceAll("%prefix",prefix);
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', NoPex));
					}
				}

			}
			//インベントリを開く
			if (cmd.getName().equalsIgnoreCase("inventory")) {

				if (args.length == 0) {
					if ((sender.hasPermission("opengui.inventory")) || (sender.isOp())) { //Pex2
						player.openInventory(player.getInventory());

						String InvOpMy = getConfig().getString("message"+ ".inventory" + ".my");
						InvOpMy = InvOpMy.replaceAll("%prefix",prefix);
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', InvOpMy));

					} //pex2
					else {
						String NoPex = getConfig().getString("message"+ ".nopermission");
						NoPex = NoPex.replaceAll("%prefix",prefix);
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', NoPex));
					}
				}
				//(command) <PlayerName> で<PlayerName>のインベントリを開く
				if (args.length == 1) {
					if ((sender.hasPermission("opengui.inventory.other")) || (sender.isOp())) { //Pex2.5
						try {
							Player targetplayer = player.getServer().getPlayer(args[0]);

							player.openInventory(targetplayer.getInventory());

							String InvOpOth = getConfig().getString("message"+ ".inventory" + ".other");
							InvOpOth = InvOpOth.replace("%prefix",prefix);
							InvOpOth = InvOpOth.replaceAll("%player",args[0]);
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', InvOpOth));

						} catch (Exception err) {
							Player targetplayer = player.getServer().getPlayer(args[0]);
							String InvOpOff = getConfig().getString("message"+ ".inventory" + ".offline");
							InvOpOff = InvOpOff.replace("%prefix",prefix);
							InvOpOff = InvOpOff.replaceAll("%player",args[0]);
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', InvOpOff));
						}
					} //pex2.5
					else {
						String NoPex = getConfig().getString("message"+ ".nopermission");
						NoPex = NoPex.replaceAll("%prefix",prefix);
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', NoPex));
					}

				}

			}

			//ワークベンチ(作業台)を開く
			if (cmd.getName().equalsIgnoreCase("workbench")) {

				if ((sender.hasPermission("opengui.workbench")) || (sender.isOp())) { //Pex3
					player.openWorkbench(null, true);

					String OpWb = getConfig().getString("message"+ ".workbench");
					OpWb = OpWb.replaceAll("%prefix",prefix);
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', OpWb));
				} //Pex3
				else {
					String NoPex = getConfig().getString("message"+ ".nopermission");
					NoPex = NoPex.replaceAll("%prefix",prefix);
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', NoPex));
				}
			}
			//エンチャントテーブルを開く (ラピスラズリ必須)
			if (cmd.getName().equalsIgnoreCase("enchanting")) {

				if ((sender.hasPermission("opengui.enchanting")) || (sender.isOp())) { //Pex4
					en = true;
					player.openEnchanting(null, true);
					String OpEt = getConfig().getString("message"+ ".enchanting");
					OpEt = OpEt.replaceAll("%prefix",prefix);
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', OpEt));
				} //Pex4
				else {
					String NoPex = getConfig().getString("message"+ ".nopermission");
					NoPex = NoPex.replaceAll("%prefix",prefix);
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', NoPex));
				}
			}
		}
		return false;
	}//OnCommand

	/*
	 * enchantingコマンド実行時のみに動作する(はず)。
	 */

	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent e) {
		if (en) {

			if(getConfig().getBoolean("lapis")){

			if (e.getInventory() instanceof EnchantingInventory) {

				EnchantingInventory inventory = (EnchantingInventory) e.getInventory();

				Dye dye = new Dye();
				dye.setColor(DyeColor.BLUE);
				ItemStack itemStack = dye.toItemStack();

				itemStack.setAmount(64);
				inventory.setItem(1, itemStack);
			}

			}
		}

	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		if(en) {
			if(getConfig().getBoolean("lapis")) {
				if (e.getInventory() instanceof EnchantingInventory) {
					EnchantingInventory inventory = (EnchantingInventory) e.getInventory();
					inventory.setItem(1, null);
					en = false;
				}
			}
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if(en) {
			if(getConfig().getBoolean("lapis")){
			if (e.getInventory() instanceof EnchantingInventory) {
				//	if(this.inventories.contains((EnchantingInventory) e.getInventory())) {
				if (e.getSlot() == 1) {
					e.setCancelled(true);
				}
			}
			//	}

			}

		}

	}
}
