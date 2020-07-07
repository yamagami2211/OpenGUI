/*
*
*  OpenGUI
*  WebSite1: https://forum.civa.jp/viewtopic.php?f=15&t=368#p1251
*  WebSite2: https://mc.yama2211.net/contents/OpenGUI.html
*  SourceCode: https://github.com/yamagami2211/OpenGUI/
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

	/* enchantingコマンド実行時に変化 */
	private boolean en = false;
	/* 実行時に表示する用のprefix  */
	private String prefix = "[OpenGUI]";

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		/* ヘルプコマンド */
		if(args.length == 0) {
			if (cmd.getName().equalsIgnoreCase("opengui")) {
				PluginDescriptionFile yml = getDescription();
				sender.sendMessage(ChatColor.GREEN + "PluginVersion : " + ChatColor.DARK_PURPLE + yml.getVersion());
				sender.sendMessage(ChatColor.AQUA + "/opengui reload" + ChatColor.WHITE + " : Configをリロード");
				sender.sendMessage(ChatColor.AQUA + "/enderchest <PlayerName>" + ChatColor.WHITE + " : 自分<PlayerName>のエンダーチェストを開く");
				sender.sendMessage(ChatColor.AQUA + "/inventory <PlayerName>" + ChatColor.WHITE + " : 自分<PlayerName>のインベントリーを開く");
				sender.sendMessage(ChatColor.AQUA + "/workbench" + ChatColor.WHITE + " : ワークベンチ(作業台)を開く");
				sender.sendMessage(ChatColor.AQUA + "/enchanting" + ChatColor.WHITE + " : エンチャントテーブルを開く");
				return true;
			}
		}
		if (args.length == 1) {
			/* ConfigReload */
			if (args[0].equalsIgnoreCase("reload")) {
				if ((sender.hasPermission("opengui.reload")) || (sender.isOp())) {
					String Confre = getConfig().getString("message"+ ".reload");
					Confre = Confre.replaceAll("%prefix",Getprefix());
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Confre));
					reloadConfig();
					return true;
				}
				/*Permissionエラー*/
				else {
					String NoPex = getConfig().getString("message"+ ".nopermission");
					NoPex = NoPex.replaceAll("%prefix",Getprefix());
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', NoPex));
					return true;
				}
			}
		}

		if (!(sender instanceof Player)) {
			String NoPl = getConfig().getString("message"+ ".noplayer");
			NoPl = NoPl.replaceAll("%prefix",Getprefix());
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', NoPl));
			return true;
		} else {
			Player player = (Player) sender;

			//エンダーチェストを開く
			if (cmd.getName().equalsIgnoreCase("enderchest")) {

				if (args.length == 0) {
					{
						if ((sender.hasPermission("opengui.enderchest")) || (sender.isOp())) { //Pex1

							player.openInventory(player.getEnderChest());

							String EnderOpMy = getConfig().getString("message"+ ".enderchest" + ".my");
							EnderOpMy = EnderOpMy.replaceAll("%prefix",Getprefix());
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', EnderOpMy));
							return true;
						} //Pex1
						else {
							String NoPex = getConfig().getString("message"+ ".nopermission");
							NoPex = NoPex.replaceAll("%prefix",Getprefix());
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', NoPex));
							return true;
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
							EnderOpOth = EnderOpOth.replace("%prefix",Getprefix());
							EnderOpOth = EnderOpOth.replaceAll("%player",args[0]);
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', EnderOpOth));
							return true;

						} catch (Exception err) {
							Player targetplayer = player.getServer().getPlayer(args[0]);
							String EnderOpOff = getConfig().getString("message"+ ".enderchest" + ".offline");
							EnderOpOff = EnderOpOff.replace("%prefix",Getprefix());
							EnderOpOff = EnderOpOff.replaceAll("%player",args[0]);
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', EnderOpOff));
							return true;
						}

					} //Pex1.5
					else {
						String NoPex = getConfig().getString("message"+ ".nopermission");
						NoPex = NoPex.replaceAll("%prefix",Getprefix());
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', NoPex));
						return true;
					}
				}

			}
			//インベントリを開く
			if (cmd.getName().equalsIgnoreCase("inventory")) {

				if (args.length == 0) {
					if ((sender.hasPermission("opengui.inventory")) || (sender.isOp())) { //Pex2
						player.openInventory(player.getInventory());

						String InvOpMy = getConfig().getString("message"+ ".inventory" + ".my");
						InvOpMy = InvOpMy.replaceAll("%prefix",Getprefix());
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', InvOpMy));
						return true;
					} //pex2
					else {
						String NoPex = getConfig().getString("message"+ ".nopermission");
						NoPex = NoPex.replaceAll("%prefix",Getprefix());
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', NoPex));
						return true;
					}
				}
				//(command) <PlayerName> で<PlayerName>のインベントリを開く
				if (args.length == 1) {
					if ((sender.hasPermission("opengui.inventory.other")) || (sender.isOp())) { //Pex2.5
						try {
							Player targetplayer = player.getServer().getPlayer(args[0]);

							player.openInventory(targetplayer.getInventory());

							String InvOpOth = getConfig().getString("message"+ ".inventory" + ".other");
							InvOpOth = InvOpOth.replace("%prefix",Getprefix());
							InvOpOth = InvOpOth.replaceAll("%player",args[0]);
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', InvOpOth));
							return true;
						} catch (Exception err) {
							Player targetplayer = player.getServer().getPlayer(args[0]);
							String InvOpOff = getConfig().getString("message"+ ".inventory" + ".offline");
							InvOpOff = InvOpOff.replace("%prefix",Getprefix());
							InvOpOff = InvOpOff.replaceAll("%player",args[0]);
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', InvOpOff));
							return true;
						}
					} //pex2.5
					else {
						String NoPex = getConfig().getString("message"+ ".nopermission");
						NoPex = NoPex.replaceAll("%prefix",Getprefix());
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', NoPex));
						return true;
					}

				}

			}

			//ワークベンチ(作業台)を開く
			if (cmd.getName().equalsIgnoreCase("workbench")) {

				if ((sender.hasPermission("opengui.workbench")) || (sender.isOp())) { //Pex3
					player.openWorkbench(null, true);

					String OpWb = getConfig().getString("message"+ ".workbench");
					OpWb = OpWb.replaceAll("%prefix",Getprefix());
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', OpWb));
					return true;
				} //Pex3
				else {
					String NoPex = getConfig().getString("message"+ ".nopermission");
					NoPex = NoPex.replaceAll("%prefix",Getprefix());
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', NoPex));
					return true;
				}
			}
			//エンチャントテーブルを開く (ラピスラズリ必須)
			if (cmd.getName().equalsIgnoreCase("enchanting")) {

				if ((sender.hasPermission("opengui.enchanting")) || (sender.isOp())) { //Pex4
					SetEn(true);
					player.openEnchanting(null, true);
					String OpEt = getConfig().getString("message"+ ".enchanting");
					OpEt = OpEt.replaceAll("%prefix",Getprefix());
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', OpEt));
					return true;
				} //Pex4
				else {
					String NoPex = getConfig().getString("message"+ ".nopermission");
					NoPex = NoPex.replaceAll("%prefix",Getprefix());
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', NoPex));
					return true;
				}
			}
		}
		return true;
	}//OnCommand

	/*
	 * enchantingコマンド実行時のみに動作する(はず)。
	 */

	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent e) {
		if (GetEn()) {

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
		if(GetEn()) {
			if(getConfig().getBoolean("lapis")) {
				if (e.getInventory() instanceof EnchantingInventory) {
					EnchantingInventory inventory = (EnchantingInventory) e.getInventory();
					inventory.setItem(1, null);
					SetEn(false);
				}
			}
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if(GetEn()) {
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

	//en のセッター
	public void SetEn(boolean SetEn){
		en = SetEn;
	}

	//en のゲッター
	public boolean GetEn(){
		return en;
	}

	//prefix のゲッター
	public String Getprefix(){
		return prefix;
	}
}
