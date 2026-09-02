package com.zackdcat.footbox;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShapeRenderer;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class FootBoxRenderer {

    public static void render(WorldRenderContext context) {
        Minecraft client = Minecraft.getInstance();

        if (client.player == null || client.level == null) return;
        if (!client.getEntityRenderDispatcher().shouldRenderHitBoxes()) return;

        float tickDelta = context.tickCounter().getGameTimeDeltaPartialTick(true);

        double lerpX = client.player.xo + (client.player.getX() - client.player.xo) * tickDelta;
        double lerpY = client.player.yo + (client.player.getY() - client.player.yo) * tickDelta;
        double lerpZ = client.player.zo + (client.player.getZ() - client.player.zo) * tickDelta;

        double offsetX = lerpX - client.player.getX();
        double offsetY = lerpY - client.player.getY();
        double offsetZ = lerpZ - client.player.getZ();

        Vec3 cameraPos = context.camera().getPosition();

        AABB fullBox = client.player.getBoundingBox().move(offsetX, offsetY, offsetZ).move(-cameraPos.x, -cameraPos.y, -cameraPos.z);

        double minY = fullBox.minY + 0.001;
        AABB bottomSquareBox = new AABB(
                fullBox.minX, minY, fullBox.minZ,
                fullBox.maxX, minY, fullBox.maxZ
        );

        PoseStack poseStack = context.matrixStack();
        VertexConsumer lines = context.consumers().getBuffer(RenderType.lines());

        ShapeRenderer.renderLineBox(
                poseStack,
                lines,
                bottomSquareBox,
                1.0F, 1.0F, 1.0F, 1.0F
        );
    }
}