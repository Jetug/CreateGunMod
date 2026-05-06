package com.nukateam.cgs.common.datagen.providers;

import com.nukateam.cgs.common.faundation.registry.CgsBlocks;
import com.nukateam.cgs.common.faundation.registry.items.CgsItems;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Set;

public class CgsBlockLootTables extends BlockLootSubProvider {
    public CgsBlockLootTables(HolderLookup.Provider provider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);

    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return CgsBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }

    @Override
    protected void generate() {
        this.dropSelf(CgsBlocks.LEAD_BLOCK.get());
        this.dropSelf(CgsBlocks.RAW_LEAD_BLOCK.get());
        this.dropSelf(CgsBlocks.STEEL_BLOCK.get());
        this.dropItem(CgsBlocks.LEAD_ORE.get(), CgsItems.RAW_LEAD.get(), 2.0F, 5.0F);
        this.dropItem(CgsBlocks.DEEPSLATE_LEAD_ORE.get(), CgsItems.RAW_LEAD.get(), 2.0F, 5.0F);
        this.dropItem(CgsBlocks.SULFUR_ORE.get(), CgsItems.SULFUR.get(), 2.0F, 5.0F);
        this.simpleDrop(CgsBlocks.GUANO_BLOCK.get(), CgsItems.GUANO.get(),0);
    }

    private void dropItem(Block block, Item drop, float minDrops, float maxDrops) {
        this.add(block, b -> createCopperLikeOreDrops(block, drop, minDrops, maxDrops));
    }

    protected void simpleDrop(Block pBlock, Item item, int count) {
        this.add(pBlock, createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(count)))
                )
            )
        );
    }

    protected LootTable.Builder createCopperLikeOreDrops(Block pBlock, Item item, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                        .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))));
    }
}
