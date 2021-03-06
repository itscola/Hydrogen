package tk.peanut.hydrogen;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import tk.peanut.hydrogen.file.FileManager;
import tk.peanut.hydrogen.module.ModuleManager;
import tk.peanut.hydrogen.ui.clickgui.ClickGui;
import tk.peanut.hydrogen.settings.SettingsManager;
import tk.peanut.hydrogen.ui.ingame.uiHUD;
import tk.peanut.hydrogen.utils.KeybindManager;
import tk.peanut.hydrogen.utils.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

@Mod(modid = Hydrogen.modid, name = Hydrogen.name, version = Hydrogen.version_number, useMetadata = true)
public class Hydrogen {

    public static final String modid = "hydrogen";
    public static final String name = "Hydrogen";
    public static final String devs = "zPeanut & UltramoxX";

    public static final String version_number = "1.4";
    private static final String version_suffix = "-dev";
    public static final String version = "v" + version_number + version_suffix;

    public String prefix = "[§e" + name + "§f] ";

    private static Hydrogen instance;

    public ModuleManager moduleManager;
    public SettingsManager settingsManager;
    public KeybindManager keybindManager;
    public FileManager fileManager;
    public ClickGui clickgui;
    public File directory;

    public boolean outdated = false;
    public String newversion;

    public Hydrogen() {
        instance = this;
    }

    public void startClient() {
        directory = new File(Minecraft.getMinecraft().mcDataDir, name);
        if (!this.directory.exists()) {
            directory.mkdir();
        }
        settingsManager = new SettingsManager();
        moduleManager = new ModuleManager();
        keybindManager = new KeybindManager();
        clickgui = new ClickGui();
        moduleManager.addModules();
        new uiHUD();
        this.isOutdated();
    }

    public static Hydrogen getClient() {
        return instance;
    }

    public void stopClient() {
    }

    public void isOutdated() {
        try {
            URL url = new URL("https://raw.githubusercontent.com/zPeanut/Resources/master/version-hydrogen");
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            while ((line = br.readLine()) != null) {
                if (Float.parseFloat(line) > Float.parseFloat(version_number)) {
                    outdated = true;
                    newversion = line;
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
      TODO LIST

      1.4:

      TODO: ArrayList outline

      TODO: Update Function
      TODO: Maybe improve ClickGUI a bit? - better visuals -> see screenshot
      TODO: Add more Player modules
      TODO: Finish Tephra modules import

      TODO: ItemESP
      TODO: W-Tap Module

      1.5:

      TODO: Blur hotbar (maybe 1.5)
      TODO: Blur on ClickGUI Buttons (i.e. see Impact)
      TODO: Visibility Option on Module in ClickGUI
      TODO: Freecam Module
      TODO: Tracers Module
      TODO: Custom Menu
     */

}

