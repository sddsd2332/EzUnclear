package me.kall.ezunclear.mixin.ic2;

import ic2.core.block.TileEntityInventory;
import ic2.core.block.reactor.tileentity.TileEntityNuclearReactorElectric;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = TileEntityNuclearReactorElectric.class,remap = false)
public abstract class MixinTileEntityNuclearReactorElectric extends TileEntityInventory {

    @Inject(method = "calculateHeatEffects", at = @At(value = "INVOKE",target = "Lic2/core/block/reactor/tileentity/TileEntityNuclearReactorElectric;explode()V"))
    private void onBigExplode(CallbackInfoReturnable<Boolean> cir) {
        TextComponentString ezUnclear = new TextComponentString(I18n.translateToLocal("info.ezunclear"));
        this.world.playerEntities.forEach(player -> player.sendStatusMessage(ezUnclear, false));
        TextComponentString goWrong = new TextComponentString(I18n.translateToLocal("info.ezunclear.interact"));
        this.world.playerEntities.forEach(player -> player.sendStatusMessage(goWrong, false));
    }
}
