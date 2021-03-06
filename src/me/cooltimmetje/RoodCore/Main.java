package me.cooltimmetje.RoodCore;

import me.cooltimmetje.RoodCore.Commands.*;
import me.cooltimmetje.RoodCore.Core.ArmorStandCustomization.Contents;
import me.cooltimmetje.RoodCore.Core.ArmorStandCustomization.MainMenu;
import me.cooltimmetje.RoodCore.Core.DataClass;
import me.cooltimmetje.RoodCore.Core.Gadgets.GadgetsMenu;
import me.cooltimmetje.RoodCore.Core.MysteryChests.*;
import me.cooltimmetje.RoodCore.Core.Rankup;
import me.cooltimmetje.RoodCore.EventListeners.ChatEvent;
import me.cooltimmetje.RoodCore.EventListeners.JoinQuitEvent;
import me.cooltimmetje.RoodCore.EventListeners.ResourcePackEvent;
import me.cooltimmetje.RoodCore.MysqlManager.Database;
import me.cooltimmetje.RoodCore.Timers.*;
import me.cooltimmetje.RoodCore.UserInterfaces.JukeboxUI;
import me.cooltimmetje.RoodCore.UserInterfaces.TimeRainUI;
import me.cooltimmetje.RoodCore.Utilities.MiscUtils;
import me.cooltimmetje.RoodCore.Utilities.TitleUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * This class has been created on 13-5-2015 at 20:40 by cooltimmetje.
 */
public class Main extends JavaPlugin {

    private static Plugin plugin;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        plugin = this;
        Database.connectToDatabase();

        registerEvents(this, new JoinQuitEvent(), new ResourcePackEvent(), new JukeboxUI(), new Rankup(), new TimeRainUI(), new GadgetsMenu(),
                new ChestChooseUI(), new NormalChestOpening(), new ChestCrafting(), new ChestsShop(), new EpicChestOpening(), new LegendChestOpening(),
                new MainMenu(), new ChatEvent(), new Contents());
        getCommand("tokens").setExecutor(new TokensCommand());
        getCommand("xp").setExecutor(new ExperienceSystem());
        getCommand("rp").setExecutor(new ResourcePackCommand());
        getCommand("codetim").setExecutor(new CodeCommand());
        getCommand("coderood").setExecutor(new CodeCommand());
        getCommand("swaggergear").setExecutor(new SwaggerGear());
        getCommand("masstokens").setExecutor(new MassTokens());
        getCommand("rankup").setExecutor(new Rankup());
        getCommand("chests").setExecutor(new ChestChooseUI());
        getCommand("advantage").setExecutor(new ThomasCommand());
        getCommand("sell").setExecutor(new SellCommand());
        getCommand("gadgets").setExecutor(new GadgetsMenu());

        for (Player p : Bukkit.getOnlinePlayers()) {
            Database.loadData(p);
            p.setPlayerListName(p.getDisplayName());
            TitleUtils.setTabList(p);
        }

        Announce.announcer();
        Tokens.tokensGiver();
        Chests.chestsGiver();
        DataSaving.dataSaver();
        DataClass.setResourceURL();
        DataClass.setResource1();
        DataClass.setResource2();
        DataClass.setResource3();
        DataClass.setResourceList();
        DataClass.setColorCodes();
        DataClass.listRanks();
        DataClass.setUpdatesList();
        OnlineChecker.onlineChecker();
//        Holograms.setHolograms();
//        UpdatesBoard.updateBoard();

    }

    @Override
    public void onDisable(){
        Bukkit.getScheduler().cancelAllTasks();
//        UpdatesBoard.removeHologram();
        MiscUtils.removeAllJukeboxHologram();
//        Holograms.removeHolograms();
        for(Player p : Bukkit.getOnlinePlayers()) {
            Database.saveData(p);
        }

//        Database.hikari.shutdown();

        plugin = null;
    }

    public static void registerEvents(Plugin plugin, Listener... listeners) {
        for(Listener listener : listeners){
            Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }

    public static Plugin getPlugin(){
        return plugin;
    }



}
