package njoyshadow.enk.mixin;

import net.minecraft.network.ClientConnection;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import njoyshadow.enk.command.EngTKor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public class MixinOnConnect {
    @Shadow
    @Final
    private MinecraftServer server;

    @Inject(method = "onPlayerConnect", at = @At(value = "TAIL", target = "Lnet/minecraft/server/network/ServerPlayerEntity;onSpawn()V"))
    public void setEnk(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
        EngTKor.Enk(player);
    }
}
