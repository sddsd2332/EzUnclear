package me.kall.ezunclear.mixin.ic2;

import ic2.core.block.TileEntityInventory;
import ic2.core.block.reactor.tileentity.TileEntityNuclearReactorElectric;
import me.kall.ezunclear.data.PendingMeltdown;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = TileEntityNuclearReactorElectric.class, remap = false)
public abstract class MixinTileEntityNuclearReactorElectric extends TileEntityInventory {

    @Shadow
    public abstract void explode();

    @Inject(method = "calculateHeatEffects", at = @At(value = "INVOKE", target = "Lic2/core/block/reactor/tileentity/TileEntityNuclearReactorElectric;explode()V"), cancellable = true)
    private void onExplode(CallbackInfoReturnable<Boolean> cir) {
        cir.cancel();
        if (!PendingMeltdown.posList.contains(pos)) {
            PendingMeltdown.posList.add(pos);
            TextComponentString ezUnclear = new TextComponentString(I18n.translateToLocal("info.ezunclear"));
            this.world.playerEntities.forEach(player -> player.sendStatusMessage(ezUnclear, false));
            synchronized (PendingMeltdown.MELT_DOWNS) {
                PendingMeltdown.MELT_DOWNS.add(() -> {
                    explode();
                    cir.setReturnValue(true);
                });
            }
        }
    }
}
