package com.zackdcat.footbox.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.zackdcat.footbox.FootBoxRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DebugRenderer.class)
public class DebugRendererMixin {

    @Inject(
            method = "render",
            at = @At("RETURN")
    )
    private void footbox$render(
            PoseStack poseStack,
            Frustum frustum,
            MultiBufferSource.BufferSource bufferSource,
            double cameraX,
            double cameraY,
            double cameraZ,
            boolean bl,
            CallbackInfo ci
    ) {
        FootBoxRenderer.render(
                poseStack,
                bufferSource,
                cameraX,
                cameraY,
                cameraZ
        );
    }
}