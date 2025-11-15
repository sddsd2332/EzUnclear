package me.kall.ezunclear.mixin.dr;

import com.brandon3055.brandonscore.blocks.TileBCBase;
import com.brandon3055.draconicevolution.blocks.reactor.ProcessExplosion;
import com.brandon3055.draconicevolution.blocks.reactor.tileentity.TileReactorCore;
import me.kall.ezunclear.data.PendingMeltdown;
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

    @Shadow
    private ProcessExplosion explosionProcess;

    @Shadow
    protected abstract void minimalBoom();

    @Inject(method = "updateCriticalState", remap = false, at = @At(value = "INVOKE", remap = false, target = "Lcom/brandon3055/draconicevolution/blocks/reactor/ProcessExplosion;detonate()Z"), cancellable = true)
    private void onBigExplode(@NotNull CallbackInfo ci) {
        ci.cancel();
        if (!PendingMeltdown.posList.contains(pos)){
            PendingMeltdown.posList.add(pos);
            TextComponentString ezUnclear = new TextComponentString(I18n.translateToLocal("info.ezunclear"));
            this.world.playerEntities.forEach(player -> player.sendStatusMessage(ezUnclear, false));
            synchronized (PendingMeltdown.MELT_DOWNS) {
                PendingMeltdown.MELT_DOWNS.add(() -> {
                    this.explosionProcess.detonate();
                    this.world.setBlockToAir(pos);
                });
            }
        }
    }

    @Inject(method = "updateCriticalState", remap = false, at = @At(value = "INVOKE", remap = false, target = "Lcom/brandon3055/draconicevolution/blocks/reactor/tileentity/TileReactorCore;minimalBoom()V"), cancellable = true)
    private void onMinimalBoom(@NotNull CallbackInfo ci) {
        ci.cancel();
        if (!PendingMeltdown.posList.contains(pos)){
            PendingMeltdown.posList.add(pos);
            TextComponentString ezUnclear = new TextComponentString(I18n.translateToLocal("info.ezunclear"));
            this.world.playerEntities.forEach(player -> player.sendStatusMessage(ezUnclear, false));
            synchronized (PendingMeltdown.MELT_DOWNS) {
                PendingMeltdown.MELT_DOWNS.add(this::minimalBoom);
            }
        }

    }

}
