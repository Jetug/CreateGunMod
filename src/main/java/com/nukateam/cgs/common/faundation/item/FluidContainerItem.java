package com.nukateam.cgs.common.faundation.item;

import com.nukateam.cgs.common.faundation.registry.items.CgsItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class FluidContainerItem extends Item {
    private final Supplier<? extends Fluid> fluidSupplier;

    public FluidContainerItem(Supplier<? extends Fluid> fluid, Properties properties) {
        super(properties);
        this.fluidSupplier = fluid;
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        if(this == CgsItems.LAVA_CONTAINER.get()){
            return 20000;
        }
        return -1;
    }

    @Override
    public boolean hasCraftingRemainingItem() {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        if (fluidSupplier.get() == Fluids.EMPTY) {
            return ItemStack.EMPTY;
        }
        return new ItemStack(CgsItems.EMPTY_CONTAINER.get());
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new FluidBucketWrapper(stack);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        var stack = player.getItemInHand(hand);
        var hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
        var ret = ForgeEventFactory.onBucketUse(player, level, stack, hitResult);
        if (ret != null) return ret;

        var blockpos = hitResult.getBlockPos();
        var direction = hitResult.getDirection();
        var relative = blockpos.relative(direction);

        if (level.mayInteract(player, blockpos) && player.mayUseItemAt(relative, direction, stack)) {
            var pos = hitResult.getBlockPos();
            var state = level.getBlockState(pos);
            var fluid = state.getFluidState().getType();

            if (this.fluidSupplier.get() == Fluids.EMPTY) {

                var pickupResult = tryPickupFluid(player, level, hitResult, fluid);
                if (pickupResult.consumesAction()) {
                    var container = handleContainerAfterUse(fluid, player, stack, true);
                    return InteractionResultHolder.sidedSuccess(container, level.isClientSide());
                }
            } else {
                var placementResult = tryPlaceFluid(player, level, hitResult, stack);
                if (placementResult.consumesAction()) {
                    var container = handleContainerAfterUse(fluid, player, stack, false);
                    return InteractionResultHolder.sidedSuccess(container, level.isClientSide());
                }
            }
        }

        return InteractionResultHolder.pass(stack);
    }

    private InteractionResult tryPickupFluid(Player player, Level level, BlockHitResult hitResult, Fluid stack) {
        var pos = hitResult.getBlockPos();
        var state = level.getBlockState(pos);
        var fluidState = state.getFluidState();
//        if (blockstate.getBlock() instanceof BucketPickup bucketPickup) {
//            player.awardStat(Stats.ITEM_USED.get(this));
//            bucketPickup.pickupBlock(level, pos, state);
//            bucketPickup.getPickupSound(blockstate).ifPresent((soundEvent) -> {
//                player.playSound(soundEvent, 1.0F, 1.0F);
//            });
//            level.gameEvent(player, GameEvent.FLUID_PICKUP, pos);
//
//            return InteractionResult.SUCCESS;
//        }
//        if (fluidState.isSource() && fluidState.getType() != Fluids.EMPTY) {
            if (state.getBlock() instanceof BucketPickup bucketPickup) {
                bucketPickup.pickupBlock(level, pos, state);
                player.awardStat(Stats.ITEM_USED.get(this));
                bucketPickup.getPickupSound(state).ifPresent((soundEvent) -> {
                    player.playSound(soundEvent, 1.0F, 1.0F);
                });

                return InteractionResult.SUCCESS;
            }
//        }

        return InteractionResult.FAIL;
    }

    private InteractionResult tryPlaceFluid(Player player, Level level, BlockHitResult hitResult, ItemStack stack) {
        var fluid = fluidSupplier.get();
        if (fluid == Fluids.EMPTY) return InteractionResult.FAIL;

        var pos = hitResult.getBlockPos();
        var direction = hitResult.getDirection();
        var placePos = pos.relative(direction);

        var blockstate = level.getBlockState(pos);
        var blockpos2 = canBlockContainFluid(level, pos, blockstate) ? pos : placePos;

        if (player.mayUseItemAt(placePos, direction, player.getItemInHand(InteractionHand.MAIN_HAND))) {
            if(emptyContents(player, level, blockpos2, hitResult, stack)) {
                if (placeFluid(player, level, placePos, hitResult, fluid)) {
                    player.awardStat(Stats.ITEM_USED.get(this));
                    this.playEmptySound(player, level, pos);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.FAIL;
    }

    public boolean emptyContents(@Nullable Player player, Level level, BlockPos pPos, @Nullable BlockHitResult pResult, @Nullable ItemStack container) {
        if (!(this.fluidSupplier.get() instanceof FlowingFluid)) {
            return false;
        } else {
            BlockState blockstate = level.getBlockState(pPos);
            Block block = blockstate.getBlock();
            boolean flag = blockstate.canBeReplaced(this.fluidSupplier.get());
            boolean flag1 = blockstate.isAir() || flag || block instanceof LiquidBlockContainer && ((LiquidBlockContainer)block).canPlaceLiquid(level, pPos, blockstate, this.fluidSupplier.get());
            java.util.Optional<FluidStack> containedFluidStack = java.util.Optional.ofNullable(container).flatMap(FluidUtil::getFluidContained);
            if (!flag1) {
                return pResult != null && this.emptyContents(player, level, pResult.getBlockPos().relative(pResult.getDirection()), (BlockHitResult)null, container);
            } else if (containedFluidStack.isPresent() && this.fluidSupplier.get().getFluidType().isVaporizedOnPlacement(level, pPos, containedFluidStack.get())) {
                this.fluidSupplier.get().getFluidType().onVaporize(player, level, pPos, containedFluidStack.get());
                return false;
            } else if (level.dimensionType().ultraWarm() && this.fluidSupplier.get().is(FluidTags.WATER)) {
                int i = pPos.getX();
                int j = pPos.getY();
                int k = pPos.getZ();
                level.playSound(player, pPos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.5F, 2.6F + (level.random.nextFloat() - level.random.nextFloat()) * 0.8F);

                for(int l = 0; l < 8; ++l) {
                    level.addParticle(ParticleTypes.LARGE_SMOKE, (double)i + Math.random(), (double)j + Math.random(), (double)k + Math.random(), 0.0D, 0.0D, 0.0D);
                }

                return false;
            } else if (block instanceof LiquidBlockContainer && ((LiquidBlockContainer)block).canPlaceLiquid(level,pPos,blockstate,fluidSupplier.get())) {
                ((LiquidBlockContainer)block).placeLiquid(level, pPos, blockstate, ((FlowingFluid)this.fluidSupplier.get()).getSource(false));
                this.playEmptySound(player, level, pPos);
                return true;
            } else {
                if (!level.isClientSide && flag && !blockstate.liquid()) {
                    level.destroyBlock(pPos, true);
                }

                if (!level.setBlock(pPos, this.fluidSupplier.get().defaultFluidState().createLegacyBlock(), 11) && !blockstate.getFluidState().isSource()) {
                    return false;
                } else {
                    this.playEmptySound(player, level, pPos);
                    return true;
                }
            }
        }
    }

    private boolean placeFluid(Player player, Level level, BlockPos pos, BlockHitResult hitResult, Fluid fluid) {
        if (!(fluid instanceof FlowingFluid)) return false;

        var state = level.getBlockState(pos);
        var canReplace = state.canBeReplaced(fluid);
        var isReplaceable = state.canBeReplaced();

        if (level.isEmptyBlock(pos) || canReplace || isReplaceable) {
            if (!level.isClientSide && isReplaceable && !state.liquid()) {
                level.destroyBlock(pos, true);
            }

            if (level.setBlock(pos, fluid.defaultFluidState().createLegacyBlock(), 11) || state.getFluidState().isSource()) {
                return true;
            }
        }
        return false;
    }

    private ItemStack handleContainerAfterUse(Fluid targetFluid, Player player, ItemStack stack, boolean filled) {
        if(!player.isCreative()) {
//            var pos = hitResult.getBlockPos();
//            var state = player.level().getBlockState(pos);
//            var fluidState = state.getFluidState();
//            var targetFluid = fluidState.getType();
//            var targetFluid = getTargetFluid(hitResult, player.level());
            var newContainer = filled ?
                    getFilledContainerForFluid(targetFluid)
                    : CgsItems.EMPTY_CONTAINER.get();

            if (newContainer == null) return stack;

            var newStack = new ItemStack(newContainer);

            if (stack.hasTag()) {
                newStack.setTag(stack.getTag().copy());
            }

            stack.shrink(1);

            if (stack.isEmpty()) {
                return newStack;
            } else {
                if (!player.getInventory().add(newStack)) {
                    player.drop(newStack, false);
                }
                return stack;
            }
        }
        return stack;
    }

    private Fluid getTargetFluid(BlockHitResult hitResult, Level level) {
        var pos = hitResult.getBlockPos();
        var fluidState = level.getFluidState(pos);
        return fluidState.getType();
    }

    private Item getFilledContainerForFluid(Fluid fluid) {
        for (var entry : CgsItems.CONTAINERS.entrySet()) {
            if (entry.getKey() == fluid) {
                return entry.getValue().get();
            }
        }
        return null;
    }

    protected void playEmptySound(@Nullable Player player, LevelAccessor level, BlockPos pPos) {
        SoundEvent soundevent = this.fluidSupplier.get().getFluidType().getSound(player, level, pPos, net.minecraftforge.common.SoundActions.BUCKET_EMPTY);
        if(soundevent == null) soundevent = this.fluidSupplier.get().is(FluidTags.LAVA) ? SoundEvents.BUCKET_EMPTY_LAVA : SoundEvents.BUCKET_EMPTY;
        level.playSound(player, pPos, soundevent, SoundSource.BLOCKS, 1.0F, 1.0F);
        level.gameEvent(player, GameEvent.FLUID_PLACE, pPos);
    }

    protected boolean canBlockContainFluid(Level worldIn, BlockPos posIn, BlockState blockstate) {
        return blockstate.getBlock() instanceof LiquidBlockContainer && ((LiquidBlockContainer)blockstate.getBlock()).canPlaceLiquid(worldIn, posIn, blockstate, this.fluidSupplier.get());
    }
}