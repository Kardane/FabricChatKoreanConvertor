package njoyshadowsmod.njoyshadowsmod.mixin;

import net.minecraft.network.MessageType;

import net.minecraft.server.filter.TextStream;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.TranslatableText;

import njoyshadowsmod.njoyshadowsmod.util.EnglishToKorean;
import njoyshadowsmod.njoyshadowsmod.util.KoreanConvertorUtil;
import njoyshadowsmod.njoyshadowsmod.util.SplitExceptString;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class MixinServerPlayNetworkHandler {
    @Shadow public abstract ServerPlayerEntity getPlayer();
    @Shadow @Final private MinecraftServer server;
    private final static EnglishToKorean englishToKorean = new KoreanConvertorUtil();

    @Shadow public ServerPlayerEntity player;

    @Inject(method = "handleMessage", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/PlayerManager;broadcast(Lnet/minecraft/text/Text;Ljava/util/function/Function;Lnet/minecraft/network/MessageType;Ljava/util/UUID;)V"

    ),
    cancellable = true)
    public void receiveMessage(TextStream.Message message, CallbackInfo ci) {
        //cancel send message
        ci.cancel();
        //message
        String Rawstring = message.getRaw();
        String string = message.getFiltered();
        //translator
        if(Rawstring.startsWith("-")){
            string= new SplitExceptString().getString(Rawstring.substring(1));
        }


        //쓸모 있는지 모르겠음 업적 같은게 아닐까 생각중
        TranslatableText text = string.isEmpty() ? null : new TranslatableText("chat.type.text", this.player.getDisplayName(), string);
        TranslatableText text2 = new TranslatableText("chat.type.text", this.player.getDisplayName(), string);
        this.server.getPlayerManager().broadcast(text2, player -> this.player.shouldFilterMessagesSentTo((ServerPlayerEntity)player) ? text : text2, MessageType.CHAT, this.player.getUuid());
    }

}
