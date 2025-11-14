package me.kall.ezunclear.mixin.dr;

import com.brandon3055.brandonscore.blocks.TileBCBase;
import com.brandon3055.draconicevolution.blocks.reactor.ProcessExplosion;
import com.brandon3055.draconicevolution.blocks.reactor.tileentity.TileReactorCore;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = TileReactorCore.class, remap = false)
public abstract class MixinTileReactorCore extends TileBCBase {

    @Inject(method = "updateCriticalState", at = @At(value = "INVOKE", target = "Lcom/brandon3055/draconicevolution/blocks/reactor/ProcessExplosion;detonate()Z"), cancellable = true)
    private void onBigExplode(@NotNull CallbackInfo ci) {
        TextComponentString ezUnclear = new TextComponentString(I18n.translateToLocal("info.ezunclear"));
        TextComponentString goWrong = new TextComponentString(I18n.translateToLocal("info.ezunclear.interact"));
        this.world.playerEntities.forEach(player -> player.sendStatusMessage(ezUnclear, false));
        this.world.playerEntities.forEach(player -> player.sendStatusMessage(goWrong, false));
    }

    @Inject(method = "updateCriticalState", at = @At(value = "INVOKE", target = "Lcom/brandon3055/draconicevolution/blocks/reactor/tileentity/TileReactorCore;minimalBoom()V"), cancellable = true)
    private void onMinimalBoom(@NotNull CallbackInfo ci) {
        TextComponentString ezUnclear = new TextComponentString(I18n.translateToLocal("info.ezunclear"));
        TextComponentString goWrong = new TextComponentString(I18n.translateToLocal("info.ezunclear.interact"));
        this.world.playerEntities.forEach(player -> player.sendStatusMessage(ezUnclear, false));

        this.world.playerEntities.forEach(player -> player.sendStatusMessage(goWrong, false));
    }

}
