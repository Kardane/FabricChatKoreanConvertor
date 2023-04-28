package njoyshadow.enk.mixin;



import net.minecraft.network.message.MessageType;

import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import njoyshadow.enk.utils.ExceptionStringUtil;
import njoyshadow.enk.utils.UUidUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.naming.Name;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static net.minecraft.network.message.MessageType.CHAT;


@Mixin(ServerPlayNetworkHandler.class)
public abstract class MixinServerPlayNetworkHandler {
    @Shadow
    @Final
    private MinecraftServer server;

    @Shadow public ServerPlayerEntity player;

    @Shadow public abstract ServerPlayerEntity getPlayer();

    //@Shadow protected abstract CompletableFuture<Text> decorateChat(String query);

    @Inject(method = "onChatMessage",at = @At(value ="HEAD"),cancellable = true)
    public void receiveMessage(ChatMessageC2SPacket packet, CallbackInfo ci) throws ExecutionException, InterruptedException, TimeoutException {
        ci.cancel();
        String Message = packet.chatMessage();
        for (UUidUtil.EnKData playerdata : UUidUtil.playerList) {
            if (this.getPlayer().getUuid().equals(playerdata.getUUID()) && playerdata.getIsEnable()) {
                Message = new ExceptionStringUtil().getString(Message);
                break;
            }
        }
        Text NameMessage = Text.literal("<").append(this.player.getDisplayName()).append("> ").append(Text.literal(Message));
        this.server.getPlayerManager().broadcast(NameMessage,false);
    }
}
