package tk.peanut.phosphor.modules;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import tk.peanut.phosphor.events.EventKey;
import tk.peanut.phosphor.modules.modules.render.HUD;
import tk.peanut.phosphor.modules.modules.movement.Eagle;
import tk.peanut.phosphor.modules.modules.render.TestModule2;
import tk.peanut.phosphor.modules.modules.render.TestModule3;
import tk.peanut.phosphor.scripting.ScriptModule;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    @NotNull
    private List<Module> modules = new ArrayList<>();

    public ModuleManager() {
        EventManager.register(this);
    }


    public void addModules() {
        addModule(new HUD());
        addModule(new Eagle());
        addModule(new TestModule2());
        addModule(new TestModule3());
    }

    private void addModule(@NotNull Module module) {
        modules.add(module);
        EventManager.register(module);
    }

    @NotNull
    public List<Module> getModules() {
        return modules;
    }

    @NotNull
    public <T extends Module> T getModule(Class<T> clazz) {
        return (T) modules.stream().filter(mod -> mod.getClass() == clazz).findFirst().orElse(null);
    }

    public Module getModule(@NotNull String name, boolean caseSensitive) {
        return modules.stream().filter(mod -> !caseSensitive && name.equalsIgnoreCase(mod.getName()) || name.equals(mod.getName())).findFirst().orElse(null);
    }

    @EventTarget
    private void onKey(@NotNull EventKey event) {
        for (Module module : modules) if (module.getKeybind() == event.getKey()) module.toggle();
    }

    public Module getModulebyName(String moduleName) {
        for(Module mod : modules) {
            if((mod.getName().trim().equalsIgnoreCase(moduleName)) || (mod.toString().trim().equalsIgnoreCase(moduleName.trim()))) {
                return mod;
            }
        }
        return null;
    }

    public List<Module> getEnabledMods() {
        List<Module> modules = new ArrayList<>();

        for (Module mod : this.getModules()) {
            if(mod.isEnabled() || (mod.getSlide() != 0 && !mod.isEnabled())) {
                modules.add(mod);
            }
        }
        return modules;
    }

    public boolean get(int i) {
        // TODO Auto-generated method stub
        return false;
    }

    public void addScriptModule(ScriptModule module) {
        addModule(module);
    }
}
