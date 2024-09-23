package embin.poosmp.datagen;

import embin.poosmp.block.PooSMPBlocks;
import embin.poosmp.items.PooSMPItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class PooSMPRecipeProvider extends FabricRecipeProvider {
    public PooSMPRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        offerStairsRecipe(exporter, PooSMPBlocks.POOP_BRICK_STAIRS, PooSMPBlocks.POOP_BRICKS);
        offerSlabRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, PooSMPBlocks.POOP_BRICK_SLAB, PooSMPBlocks.POOP_BRICKS);
        offerWallRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, PooSMPBlocks.POOP_BRICK_WALL, PooSMPBlocks.POOP_BRICKS);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, PooSMPBlocks.POOP_BRICK_STAIRS, PooSMPBlocks.POOP_BRICKS);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, PooSMPBlocks.POOP_BRICK_SLAB, PooSMPBlocks.POOP_BRICKS, 2);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, PooSMPBlocks.POOP_BRICK_WALL, PooSMPBlocks.POOP_BRICKS);
        offerRedNetherFenceRecipe(exporter, PooSMPBlocks.RED_NETHER_BRICK_FENCE, Blocks.RED_NETHER_BRICKS);
    }

    public static void offerStairsRecipe(RecipeExporter exporter, ItemConvertible output, ItemConvertible input) {
        createStairsRecipe(output, Ingredient.ofItems(input)).criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter);
    }

    public static void offerRedNetherFenceRecipe(RecipeExporter exporter, ItemConvertible output, ItemConvertible input) {
        createRedNetherFenceRecipe(output, Ingredient.ofItems(input)).criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter);
    }

    public static CraftingRecipeJsonBuilder createRedNetherFenceRecipe(ItemConvertible output, Ingredient input) {
        Item item = PooSMPItems.RED_NETHER_BRICK;
        return ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, output, 6).input('W', input).input('#', item).pattern("W#W").pattern("W#W");
    }
}
