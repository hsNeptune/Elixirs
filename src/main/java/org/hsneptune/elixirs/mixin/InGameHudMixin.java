package org.hsneptune.elixirs.mixin;
//
//import net.minecraft.client.MinecraftClient;
//import net.minecraft.client.gui.LayeredDrawer;
//import org.spongepowered.asm.mixin.Final;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Shadow;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.hsneptune.elixirs.client.*;
//import net.minecraft.client.gui.hud.InGameHud;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//
//@Mixin(InGameHud.class)
//public abstract class InGameHudMixin {
//    @Shadow @Final private LayeredDrawer layeredDrawer;
//
//    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/LayeredDrawer;addLayer(Lnet/minecraft/client/gui/LayeredDrawer$Layer;)Lnet/minecraft/client/gui/LayeredDrawer;", ordinal = 3))
//    public void render(MinecraftClient client, CallbackInfo ci) {
//        // modifications made: changed from FAPIs interface to my interface
//        this.layeredDrawer.addLayer((context, tickDelta) -> {
//            HudOverlays.render(context);
//            HudOverlays.renderAffinity(context);
//        });
//    }
//}

