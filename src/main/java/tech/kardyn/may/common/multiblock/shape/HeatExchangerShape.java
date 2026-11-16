package tech.kardyn.may.common.multiblock.shape;

import flaxbeard.immersivepetroleum.common.blocks.multiblocks.shapes.GenericShape;
import flaxbeard.immersivepetroleum.common.util.AABBUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HeatExchangerShape extends GenericShape {
    public static final HeatExchangerShape GETTER = new HeatExchangerShape();

    HeatExchangerShape() {
    }

    @NotNull
    protected List<AABB> getShape(BlockPos posInMultiblock) {
        int x = posInMultiblock.getX();
        int y = posInMultiblock.getY();
        int z = posInMultiblock.getZ();

        List<AABB> shape = new ArrayList<>();

        shape.add(AABBUtils.FULL);

        return shape;
    }
}
