package com.zackdcat.footbox;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;

public class FootBox implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		WorldRenderEvents.LAST.register(FootBoxRenderer::render);
	}
}