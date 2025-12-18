package embin.poosmp.datagen;

import embin.poosmp.block.PooSMPBlocks;
import embin.poosmp.items.PooSMPItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import java.util.concurrent.CompletableFuture;

public class PooSMPRecipeProvider extends FabricRecipeProvider {
    public PooSMPRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
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

    public static void offerStairsRecipe(RecipeExporter exporter, ItemLike output, ItemLike input) {
        createStairsRecipe(output, Ingredient.of(input)).criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter);
    }

    public static void offerRedNetherFenceRecipe(RecipeExporter exporter, ItemLike output, ItemLike input) {
        createRedNetherFenceRecipe(output, Ingredient.of(input)).criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter);
    }

    public static void offerDoorRecipe(RecipeExporter exporter, ItemLike output, ItemLike input) {
        createDoorRecipe(output, Ingredient.of(input)).criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter);
    }

    public static void offerTrapdoorRecipe(RecipeExporter exporter, ItemLike output, ItemLike input) {
        createTrapdoorRecipe(output, Ingredient.of(input)).criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter);
    }

    public static void offerFenceRecipe(RecipeExporter exporter, ItemLike output, ItemLike input) {
        createFenceRecipe(output, Ingredient.of(input)).criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter);
    }

    public static void offerFenceGateRecipe(RecipeExporter exporter, ItemLike output, ItemLike input) {
        createFenceGateRecipe(output, Ingredient.of(input)).criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter);
    }

    public static void offerButtonRecipe(RecipeExporter exporter, ItemLike output, ItemLike input) {
        createButtonRecipe(output, Ingredient.of(input)).criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter);
    }

    public static CraftingRecipeJsonBuilder createRedNetherFenceRecipe(ItemLike output, Ingredient input) {
        Item item = PooSMPItems.RED_NETHER_BRICK;
        return ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, output, 6).input('W', input).input('#', item).pattern("W#W").pattern("W#W");
    }

    public static CraftingRecipeJsonBuilder createButtonRecipe(ItemLike output, Ingredient input) {
        return ShapelessRecipeJsonBuilder.create(RecipeCategory.REDSTONE, output, 1).input(input);
    }
}
