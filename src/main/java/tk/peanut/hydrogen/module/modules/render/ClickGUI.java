package tk.peanut.hydrogen.module.modules.render;

import org.lwjgl.input.Keyboard;
import tk.peanut.hydrogen.Hydrogen;
import tk.peanut.hydrogen.file.files.ClickGuiFile;
import tk.peanut.hydrogen.module.Category;
import tk.peanut.hydrogen.module.Info;
import tk.peanut.hydrogen.module.Module;
import tk.peanut.hydrogen.settings.Setting;
import tk.peanut.hydrogen.ui.clickgui.ClickGui;

/**
 * Created by peanut on 03/02/2021
 */

@Info(name = "ClickGUI", description = "The click gui", category = Category.Render, color = -1)
public class ClickGUI extends Module {

    public ClickGui clickgui;

    public ClickGUI() {
        super(Keyboard.KEY_LSHIFT);
        Hydrogen.getClient().settingsManager.rSetting(new Setting("Blur", this, true));
        Hydrogen.getClient().settingsManager.rSetting(new Setting("Tooltip", this, true));
   /*     ArrayList<String> options = new ArrayList<>();
        options.add("DefaultOption");
        options.add("Option2");
        options.add("Option3");
        Hydrogen.getInstance().settingsManager.rSetting(new Setting("OptionSelector", this, "DefaultOption", options));
        Hydrogen.getInstance().settingsManager.rSetting(new Setting("BooleanOption", this, false));
        Hydrogen.getInstance().settingsManager.rSetting(new Setting("SliderOptionInt", this, 255, 0, 255, true));
        Hydrogen.getInstance().settingsManager.rSetting(new Setting("SliderOptionDouble", this, 10, 0, 20, false));*/
    }

    @Override
    public void onEnable()
    {
        if(this.clickgui == null)
            this.clickgui = new ClickGui();
        ClickGuiFile.loadClickGui();
        mc.displayGuiScreen(this.clickgui);
        toggle();
        super.onEnable();
    }

}
