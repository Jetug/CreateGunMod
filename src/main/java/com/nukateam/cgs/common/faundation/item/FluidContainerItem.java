package com.nukateam.cgs.common.faundation.item;

import com.nukateam.cgs.common.faundation.registry.items.CgsItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
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
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;

import javax.annotation.Nullable;
import java.util.Optional;
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

        if (state.getBlock() instanceof BucketPickup bucketPickup) {
            bucketPickup.pickupBlock(level, pos, state);
            player.awardStat(Stats.ITEM_USED.get(this));
            bucketPickup.getPickupSound(state).ifPresent((soundEvent) -> {
                player.playSound(soundEvent, 1.0F, 1.0F);
            });

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
    }

    private InteractionResult tryPlaceFluid(Player player, Level level, BlockHitResult hitResult, ItemStack stack) {
        var blockpos = hitResult.getBlockPos();
        var direction = hitResult.getDirection();
        var relativePos = blockpos.relative(direction);
        var blockstate = level.getBlockState(blockpos);
        var placePos = canBlockContainFluid(level, blockpos, blockstate) ? blockpos : relativePos;

        if (level.mayInteract(player, blockpos) && player.mayUseItemAt(relativePos, direction, stack)) {
            if (this.emptyContents(player, level, placePos, hitResult, stack)) {
                if (player instanceof ServerPlayer) {
                    CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) player, placePos, stack);
                }

                player.awardStat(Stats.ITEM_USED.get(this));
                return InteractionResult.SUCCESS;
            }
            else return InteractionResult.FAIL;
        }
        return InteractionResult.FAIL;
    }

    public boolean emptyContents(@Nullable Player player, Level level, BlockPos pos,
                                 @Nullable BlockHitResult result, @Nullable ItemStack container) {
        if (!(this.fluidSupplier.get() instanceof FlowingFluid)) {
            return false;
        } else {
            var blockstate = level.getBlockState(pos);
            var block = blockstate.getBlock();
            var containedFluidStack = Optional.ofNullable(container).flatMap(FluidUtil::getFluidContained);
            var canBeReplaced = blockstate.canBeReplaced(this.fluidSupplier.get());
            var canPlaceLiquid = block instanceof LiquidBlockContainer liquidBlockContainer &&
                    liquidBlockContainer.canPlaceLiquid(level, pos, blockstate, this.fluidSupplier.get());

            if (!(blockstate.isAir() || canBeReplaced || canPlaceLiquid)) {
                return result != null &&
                        this.emptyContents(player, level, result.getBlockPos().relative(result.getDirection()), null, container);
            } else if (containedFluidStack.isPresent() && this.fluidSupplier.get().getFluidType().isVaporizedOnPlacement(level, pos, containedFluidStack.get())) {
                this.fluidSupplier.get().getFluidType().onVaporize(player, level, pos, containedFluidStack.get());
                return false;
            } else if (level.dimensionType().ultraWarm() && this.fluidSupplier.get().is(FluidTags.WATER)) {
                var pitch = 2.6F + (level.random.nextFloat() - level.random.nextFloat()) * 0.8F;
                level.playSound(player, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.5F, pitch);

                for(int l = 0; l < 8; ++l) {
                    level.addParticle(ParticleTypes.LARGE_SMOKE,
                            (double) pos.getX() + Math.random(),
                            (double) pos.getY() + Math.random(),
                            (double) pos.getZ() + Math.random(),
                            0.0D, 0.0D, 0.0D);
                }

                return false;
            } else if (block instanceof LiquidBlockContainer liquidBlockContainer &&
                    liquidBlockContainer.canPlaceLiquid(level, pos,blockstate,fluidSupplier.get()))
            {
                liquidBlockContainer.placeLiquid(level, pos, blockstate, ((FlowingFluid)this.fluidSupplier.get()).getSource(false));
                this.playEmptySound(player, level, pos);
                return true;
            } else {
                if (!level.isClientSide && canBeReplaced && !blockstate.liquid()) {
                    level.destroyBlock(pos, true);
                }

                if (!level.setBlock(pos, this.fluidSupplier.get().defaultFluidState().createLegacyBlock(), 11) && !blockstate.getFluidState().isSource()) {
                    return false;
                } else {
                    this.playEmptySound(player, level, pos);
                    return true;
                }
            }
        }
    }

    private ItemStack handleContainerAfterUse(Fluid targetFluid, Player player, ItemStack stack, boolean filled) {
        if(!player.isCreative()) {
            var newContainer = filled ? getFilledContainerForFluid(targetFluid)
                    : CgsItems.EMPTY_CONTAINER.get();

            if (newContainer == null) return stack;

            var newStack = new ItemStack(newContainer);

            if (stack.hasTag())
                newStack.setTag(stack.getTag().copy());

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