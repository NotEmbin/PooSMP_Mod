package embin.poosmp.datagen;

import embin.poosmp.block.PooSMPBlocks;
import embin.poosmp.items.PooSMPItems;
import embin.poosmp.util.PooSMPTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
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

        offerPlanksRecipe(exporter, PooSMPBlocks.PALE_OAK_PLANKS, PooSMPTags.Items.PALE_OAK_LOGS, 4);
        offerBarkBlockRecipe(exporter, PooSMPBlocks.PALE_OAK_WOOD, PooSMPBlocks.PALE_OAK_LOG);
        offerBarkBlockRecipe(exporter, PooSMPBlocks.STRIPPED_PALE_OAK_WOOD, PooSMPBlocks.STRIPPED_PALE_OAK_LOG);
        offerDoorRecipe(exporter, PooSMPBlocks.PALE_OAK_DOOR, PooSMPBlocks.PALE_OAK_PLANKS);
        offerTrapdoorRecipe(exporter, PooSMPBlocks.PALE_OAK_TRAPDOOR, PooSMPBlocks.PALE_OAK_PLANKS);
        offerStairsRecipe(exporter, PooSMPBlocks.PALE_OAK_STAIRS, PooSMPBlocks.PALE_OAK_PLANKS);
        offerSlabRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, PooSMPBlocks.PALE_OAK_SLAB, PooSMPBlocks.PALE_OAK_PLANKS);
        offerFenceRecipe(exporter, PooSMPBlocks.PALE_OAK_FENCE, PooSMPBlocks.PALE_OAK_PLANKS);
        offerFenceGateRecipe(exporter, PooSMPBlocks.PALE_OAK_FENCE_GATE, PooSMPBlocks.PALE_OAK_PLANKS);
        offerPressurePlateRecipe(exporter, PooSMPBlocks.PALE_OAK_PRESSURE_PLATE, PooSMPBlocks.PALE_OAK_PLANKS);
        offerButtonRecipe(exporter, PooSMPBlocks.PALE_OAK_BUTTON, PooSMPBlocks.PALE_OAK_PLANKS);

        offerStairsRecipe(exporter, PooSMPBlocks.RESIN_BRICK_STAIRS, PooSMPBlocks.RESIN_BRICKS);
        offerSlabRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, PooSMPBlocks.RESIN_BRICK_SLAB, PooSMPBlocks.RESIN_BRICKS);
        offerWallRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, PooSMPBlocks.RESIN_BRICK_WALL, PooSMPBlocks.RESIN_BRICKS);
        offerChiseledBlockRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, PooSMPBlocks.CHISELED_RESIN_BRICKS, PooSMPBlocks.RESIN_BRICK_SLAB);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, PooSMPBlocks.RESIN_BRICK_SLAB, PooSMPBlocks.RESIN_BRICKS, 2);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, PooSMPBlocks.RESIN_BRICK_STAIRS, PooSMPBlocks.RESIN_BRICKS);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, PooSMPBlocks.RESIN_BRICK_WALL, PooSMPBlocks.RESIN_BRICKS);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, PooSMPBlocks.CHISELED_RESIN_BRICKS, PooSMPBlocks.RESIN_BRICKS);
    }

    public static void offerStairsRecipe(RecipeExporter exporter, ItemConvertible output, ItemConvertible input) {
        createStairsRecipe(output, Ingredient.ofItems(input)).criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter);
    }

    public static void offerRedNetherFenceRecipe(RecipeExporter exporter, ItemConvertible output, ItemConvertible input) {
        createRedNetherFenceRecipe(output, Ingredient.ofItems(input)).criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter);
    }

    public static void offerDoorRecipe(RecipeExporter exporter, ItemConvertible output, ItemConvertible input) {
        createDoorRecipe(output, Ingredient.ofItems(input)).criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter);
    }

    public static void offerTrapdoorRecipe(RecipeExporter exporter, ItemConvertible output, ItemConvertible input) {
        createTrapdoorRecipe(output, Ingredient.ofItems(input)).criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter);
    }

    public static void offerFenceRecipe(RecipeExporter exporter, ItemConvertible output, ItemConvertible input) {
        createFenceRecipe(output, Ingredient.ofItems(input)).criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter);
    }

    public static void offerFenceGateRecipe(RecipeExporter exporter, ItemConvertible output, ItemConvertible input) {
        createFenceGateRecipe(output, Ingredient.ofItems(input)).criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter);
    }

    public static void offerButtonRecipe(RecipeExporter exporter, ItemConvertible output, ItemConvertible input) {
        createButtonRecipe(output, Ingredient.ofItems(input)).criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter);
    }

    public static CraftingRecipeJsonBuilder createRedNetherFenceRecipe(ItemConvertible output, Ingredient input) {
        Item item = PooSMPItems.RED_NETHER_BRICK;
        return ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, output, 6).input('W', input).input('#', item).pattern("W#W").pattern("W#W");
    }

    public static CraftingRecipeJsonBuilder createButtonRecipe(ItemConvertible output, Ingredient input) {
        return ShapelessRecipeJsonBuilder.create(RecipeCategory.REDSTONE, output, 1).input(input);
    }
}
