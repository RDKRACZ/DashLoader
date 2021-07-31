package net.oskarstrom.dashloader.mixin.accessor;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.block.BlockModels;
import net.minecraft.client.render.model.BakedModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(BlockModels.class)
public interface BlockModelsAccessor {

	@Accessor
	Map<BlockState, BakedModel> getModels();

	@Accessor
	void setModels(Map<BlockState, BakedModel> models);
}
