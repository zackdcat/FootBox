package com.zackdcat.footbox;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.debug.DebugScreenEntries;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShapeRenderer;
import net.minecraft.world.phys.AABB;

public class FootBoxRenderer {

    private static float tickDelta;

    public static void setTickDelta(float tickDelta) {
        FootBoxRenderer.tickDelta = tickDelta;
    }

    public static float getTickDelta() {
        return tickDelta;
    }

    public static void render(
            PoseStack poseStack,
            MultiBufferSource.BufferSource bufferSource,
            double cameraX,
            double cameraY,
            double cameraZ
    ) {
        Minecraft client = Minecraft.getInstance();

        if (client.player == null || client.level == null) return;

        if (!client.debugEntries.isCurrentlyEnabled(DebugScreenEntries.ENTITY_HITBOXES)) {
            return;
        }

        double lerpX = client.player.xo
                + (client.player.getX() - client.player.xo) * tickDelta;

        double lerpY = client.player.yo
                + (client.player.getY() - client.player.yo) * tickDelta;

        double lerpZ = client.player.zo
                + (client.player.getZ() - client.player.zo) * tickDelta;

        double offsetX = lerpX - client.player.getX();
        double offsetY = lerpY - client.player.getY();
        double offsetZ = lerpZ - client.player.getZ();

        AABB fullBox = client.player.getBoundingBox()
                .move(offsetX, offsetY, offsetZ)
                .move(-cameraX, -cameraY, -cameraZ);

        double minY = fullBox.minY + 0.001;

        AABB footBox = new AABB(
                fullBox.minX,
                minY,
                fullBox.minZ,
                fullBox.maxX,
                minY,
                fullBox.maxZ
        );

        VertexConsumer lines = bufferSource.getBuffer(RenderType.lines());

        ShapeRenderer.renderLineBox(
                poseStack.last(),
                lines,
                footBox,
                1.0F,
                1.0F,
                1.0F,
                1.0F
        );
    }
}