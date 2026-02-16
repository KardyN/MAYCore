/**
 *     MAYCore  Copyright (C) 2026  KardyN
 *     The following code is licensed under GNU General Public License v3.0
 *     Find full license text at project root or at https://www.gnu.org/licenses/gpl-3.0.txt
 */

package tech.kardyn.may.mixin.create;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.compat.jei.category.animations.AnimatedBlazeBurner;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.devices.CharcoalForgeBlock;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = AnimatedBlazeBurner.class, remap = false)
public abstract class AnimatedBlazeBurnerMixin extends AnimatedKinetics {

    @Shadow
    private BlazeBurnerBlock.HeatLevel heatLevel;

    /**
     * @author KardyN
     * @reason Display Charcoal Forge instead of BLaze Burner in JEI
     */
    @Overwrite
    public void draw(GuiGraphics graphics, int xOffset, int yOffset){
        PoseStack matrixStack = graphics.pose();
        matrixStack.pushPose();
        matrixStack.translate(xOffset, yOffset, 200);
        matrixStack.mulPose(Axis.XP.rotationDegrees(-15.5f));
        matrixStack.mulPose(Axis.YP.rotationDegrees(22.5f));
        int scale = 23;

        blockElement(TFCBlocks.CHARCOAL_FORGE.get().defaultBlockState().setValue(CharcoalForgeBlock.HEAT, heatLevel == BlazeBurnerBlock.HeatLevel.SEETHING ? 7 : 3)).atLocal(0, 1.65, 0).scale(scale).render(graphics);

        matrixStack.popPose();
    }
}
