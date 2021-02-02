package tk.peanut.phosphor.injection.mixins;

import com.darkmagician6.eventapi.EventManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.main.GameConfiguration;
import net.minecraft.util.Session;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tk.peanut.phosphor.Phosphor;
import tk.peanut.phosphor.events.EventKey;
import tk.peanut.phosphor.injection.interfaces.IMixinMinecraft;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
@SideOnly(Side.CLIENT)
public class MixinMinecraft implements IMixinMinecraft {
    @Shadow
    public GuiScreen currentScreen;

    @Shadow
    volatile boolean running = true;

    @Shadow
    @Mutable
    @Final
    private Session session;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void minecraftConstructor(GameConfiguration gameConfig, CallbackInfo ci) {
        new Phosphor();
    }

    @Inject(method = "startGame", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;ingameGUI:Lnet/minecraft/client/gui/GuiIngame;", shift = At.Shift.AFTER))
    private void startGame(CallbackInfo ci) {
        Phosphor.getInstance().startClient();
    }

    @Inject(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;dispatchKeypresses()V", shift = At.Shift.AFTER))
    private void onKey(CallbackInfo ci) {
        if (Keyboard.getEventKeyState() && currentScreen == null)
            EventManager.call(new EventKey(Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + 256 : Keyboard.getEventKey()));
    }

    @Inject(method = "shutdown", at = @At("HEAD"))
    private void onShutdown(CallbackInfo ci) {
        Phosphor.getInstance().stopClient();
    }

    @Override
    public Session getSession() {
        return session;
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }


}
