package mrthomas20121.tfcompat.compat.thermalexpansion;

import cofh.thermalexpansion.util.managers.dynamo.NumismaticManager;
import cofh.thermalexpansion.util.managers.machine.*;
import cofh.thermalfoundation.init.TFItems;
import mrthomas20121.tfcompat.TFCompatConfig;
import mrthomas20121.tfcompat.compat.TFCompatResources;
import mrthomas20121.tfcompat.library.RecipeRegistry;
import mrthomas20121.tfcompat.library.helpers.MetalHelper;
import mrthomas20121.tfcompat.library.helpers.TFCMetals;
import net.dries007.tfc.api.recipes.barrel.BarrelRecipe;
import net.dries007.tfc.api.recipes.heat.HeatRecipe;
import net.dries007.tfc.api.recipes.quern.QuernRecipe;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.ICrop;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.objects.Gem;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;
import net.dries007.tfc.objects.blocks.wood.*;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.objects.items.ItemGem;
import net.dries007.tfc.objects.items.ItemSeedsTFC;
import net.dries007.tfc.objects.items.ItemsTFC;
import net.dries007.tfc.objects.items.metal.ItemOreTFC;
import net.dries007.tfc.objects.items.wood.ItemLumberTFC;
import net.dries007.tfc.types.DefaultMetals;
import net.dries007.tfc.util.agriculture.Crop;
import net.dries007.tfc.util.calendar.ICalendar;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.IForgeRegistry;

public class ThermalRegistry extends RecipeRegistry {

    public ThermalRegistry()
    {
        super("thermal_registry");
    }

    @Override
    public void registerRecipes(IForgeRegistry<IRecipe> r) {
        if(TFCompatConfig.DefaultConfig.thermal.extruder) extruderRecipes();
        if(TFCompatConfig.DefaultConfig.thermal.precipitator) precipiratorRecipes();
        if(TFCompatConfig.DefaultConfig.thermal.pulverizer) pulverizerRecipes();
        if(TFCompatConfig.DefaultConfig.thermal.sawmill) sawmillRecipes();
        if(TFCompatConfig.DefaultConfig.thermal.redstone_furnace) redstoneFurnaceRecipes();
        fluidTransposerRecipes();
        pyrolyticConversionRecipes();
        lapidaryRecipes();
    }

    @Override
    public void registerBarrelRecipes(IForgeRegistry<BarrelRecipe> r) {
        if(TFCompatConfig.DefaultConfig.thermal.thermal_refinery_recipes) {
            RefineryManager.RefineryRecipe[] recipes = RefineryManager.getRecipeList();
            for(RefineryManager.RefineryRecipe recipe : recipes)
            {
                FluidStack input = recipe.getInput();
                FluidStack output = recipe.getOutputFluid();
                ItemStack outputItem = recipe.getOutputItem();
                r.register(new BarrelRecipe(IIngredient.of(input), IIngredient.of(ItemStack.EMPTY), output, outputItem.isEmpty() ? ItemStack.EMPTY : outputItem, 61* ICalendar.TICKS_IN_DAY).setRegistryName("still_"+input.getFluid().getName()));
            }
        }

        // enderium
        r.register(new BarrelRecipe(IIngredient.of(FluidRegistry.getFluid("ender"), 1000), IIngredient.of(MetalHelper.getMetalItem(TFCMetals.blue_steel, Metal.ItemType.SCRAP, 1)), null, MetalHelper.getMetalItem(TFCompatResources.enderium, Metal.ItemType.INGOT, 1), 8*ICalendar.TICKS_IN_HOUR).setRegistryName("enderium_ingot"));

        // signalum
        r.register(new BarrelRecipe(IIngredient.of(FluidRegistry.getFluid("redstone"), 1000), IIngredient.of(MetalHelper.getMetalItem(TFCMetals.black_steel, Metal.ItemType.SCRAP, 1)), null, MetalHelper.getMetalItem(TFCompatResources.signalum, Metal.ItemType.INGOT, 1), 8*ICalendar.TICKS_IN_HOUR).setRegistryName("signalum_ingot"));

        // lumium
        r.register(new BarrelRecipe(IIngredient.of(FluidRegistry.getFluid("glowstone"), 1000), IIngredient.of(MetalHelper.getMetalItem(TFCMetals.red_steel, Metal.ItemType.SCRAP, 1)), null, MetalHelper.getMetalItem(TFCompatResources.lumium, Metal.ItemType.INGOT, 1), 8*ICalendar.TICKS_IN_HOUR).setRegistryName("lumium_ingot"));

    }

    /**
     * add a fluid transposer recipe for each fluid that match
     */
    private void fluidTransposerRecipes() {
        for(BarrelRecipe recipe: TFCRegistries.BARREL.getValuesCollection()) {
            NonNullList<FluidStack> fluidStack = recipe.getFluidIngredient().getValidIngredients();
            FluidStack output = recipe.getOutputFluid();
            if(!fluidStack.isEmpty()) {
                for(ItemStack stack : recipe.getItemIngredient().getValidIngredients()) {
                    TransposerManager.addExtractRecipe(1000, stack,recipe.getOutputStack(), fluidStack.get(0), 1, false);
                }
            }
            if(output != null && fluidStack.isEmpty()) {
                if(!recipe.getItemIngredient().getValidIngredients().isEmpty()) {
                    for(ItemStack stack : recipe.getItemIngredient().getValidIngredients()) {
                        TransposerManager.addFillRecipe(1000, stack, recipe.getOutputStack(), output, false);
                    }
                }
            }
        }
    }

    /**
     * Pyrolytic Conversion Augment Recipes
     */
    private void pyrolyticConversionRecipes() {
        FurnaceManager.addRecipePyrolysis(3000, new ItemStack(ItemOreTFC.get(TFCRegistries.ORES.getValue(DefaultMetals.BITUMINOUS_COAL))), new ItemStack(TFItems.itemMaterial, 1, 802), 250);
        FurnaceManager.addRecipePyrolysis(3000, new ItemStack(ItemOreTFC.get(TFCRegistries.ORES.getValue(DefaultMetals.LIGNITE))), new ItemStack(TFItems.itemMaterial, 1, 802), 250);
        FurnaceManager.addRecipePyrolysis(3000, new ItemStack(ItemOreTFC.get(TFCRegistries.ORES.getValue(DefaultMetals.PETRIFIED_WOOD))), new ItemStack(TFItems.itemMaterial, 1, 802), 200);
    }

    /**
     * Lapidary Calibration Augment Recipes
     */
    private void lapidaryRecipes() {
        NumismaticManager.removeGemFuel(new ItemStack(Items.DIAMOND));
        NumismaticManager.removeGemFuel(new ItemStack(Items.QUARTZ));
        NumismaticManager.removeGemFuel(new ItemStack(Items.EMERALD));
        NumismaticManager.removeGemFuel(new ItemStack(Items.DYE, 1, 4));
        NumismaticManager.removeGemFuel(new ItemStack(Items.PRISMARINE_SHARD));
        NumismaticManager.removeGemFuel(ItemGem.get(Gem.AMETHYST, Gem.Grade.NORMAL, 1));

        for(Gem gem: Gem.values()) {
            ItemStack chippedGem = ItemGem.get(gem, Gem.Grade.CHIPPED, 1);
            ItemStack flawedGem = ItemGem.get(gem, Gem.Grade.FLAWED, 1);
            ItemStack normalGem = ItemGem.get(gem, Gem.Grade.NORMAL, 1);
            ItemStack flawlessGem = ItemGem.get(gem, Gem.Grade.FLAWLESS, 1);
            ItemStack exquisiteGem = ItemGem.get(gem, Gem.Grade.EXQUISITE, 1);

            NumismaticManager.addGemFuel(chippedGem, 50000);
            NumismaticManager.addGemFuel(flawedGem, 100000);
            NumismaticManager.addGemFuel(normalGem, 150000);
            NumismaticManager.addGemFuel(flawlessGem, 200000);
            NumismaticManager.addGemFuel(exquisiteGem, 250000);
        }
    }

    private void extruderRecipes()
    {
        for(Rock rock : TFCRegistries.ROCKS.getValuesCollection())
        {
            ExtruderManager.addRecipeIgneous(1000, new ItemStack(BlockRockVariant.get(rock, Rock.Type.RAW)), FluidRegistry.getFluidStack("lava", 1000), FluidRegistry.getFluidStack("fresh_water", 1000));
            ExtruderManager.addRecipeSedimentary(1000, new ItemStack(BlockRockVariant.get(rock, Rock.Type.SAND)), FluidRegistry.getFluidStack("lava", 1000), FluidRegistry.getFluidStack("fresh_water", 1000));
        }
    }

    private void precipiratorRecipes()
    {
        PrecipitatorManager.addRecipe(800, new ItemStack(Items.SNOWBALL, 4), FluidRegistry.getFluidStack("fresh_water", 500));
        PrecipitatorManager.addRecipe(800, new ItemStack(Blocks.SNOW, 1), FluidRegistry.getFluidStack("fresh_water", 500));
        PrecipitatorManager.addRecipe(800, new ItemStack(Blocks.SNOW_LAYER, 2), FluidRegistry.getFluidStack("fresh_water", 500));
        PrecipitatorManager.addRecipe(1600, new ItemStack(Blocks.ICE, 1), FluidRegistry.getFluidStack("fresh_water", 1000));
        PrecipitatorManager.addRecipe(1600, new ItemStack(Blocks.PACKED_ICE, 1), FluidRegistry.getFluidStack("fresh_water", 1000));
        PrecipitatorManager.addRecipe(1600, new ItemStack(BlocksTFC.SEA_ICE, 1), FluidRegistry.getFluidStack("salt_water", 1000));
    }

    private void sawmillRecipes()
    {
        for(Tree tree : TFCRegistries.TREES.getValuesCollection())
        {
            SawmillManager.addRecipe(1000, new ItemStack(BlockLogTFC.get(tree), 1), new ItemStack(ItemLumberTFC.get(tree), 8), new ItemStack(ItemsTFC.WOOD_ASH, 1), 20);
            SawmillManager.addRecipe(1000, new ItemStack(BlockPlanksTFC.get(tree), 1), new ItemStack(ItemLumberTFC.get(tree), 3), new ItemStack(ItemsTFC.WOOD_ASH, 1), 20);
            SawmillManager.addRecipe(1000, new ItemStack(BlockDoorTFC.get(tree), 1), new ItemStack(ItemLumberTFC.get(tree), 4), new ItemStack(ItemsTFC.WOOD_ASH, 1), 20);
            SawmillManager.addRecipe(1000, new ItemStack(BlockTrapDoorWoodTFC.get(tree), 1), new ItemStack(ItemLumberTFC.get(tree), 4), new ItemStack(ItemsTFC.WOOD_ASH, 1), 20);
            SawmillManager.addRecipe(1000, new ItemStack(BlockFenceTFC.get(tree), 1), new ItemStack(ItemLumberTFC.get(tree), 3), new ItemStack(ItemsTFC.WOOD_ASH, 1), 20);
            SawmillManager.addRecipe(1000, new ItemStack(BlockFenceGateTFC.get(tree), 1), new ItemStack(ItemLumberTFC.get(tree), 4), new ItemStack(ItemsTFC.WOOD_ASH, 1), 20);
        }

        for(ICrop crop: Crop.values()) {
            ItemStack seed = ItemSeedsTFC.get(crop, 1);
            ItemStack foodDrop = crop.getFoodDrop(1);

            SawmillManager.addRecipe(2000, foodDrop, new ItemStack(TFItems.itemMaterial, 1, 816), seed, 50);
        }
    }

    private void redstoneFurnaceRecipes()
    {
        // remove default redstone furnace recipes
        FurnaceManager.FurnaceRecipe[] recipes = FurnaceManager.getRecipeList(false);
        for(FurnaceManager.FurnaceRecipe recipe: recipes) {
            FurnaceManager.removeRecipe(recipe.getInput());
        }

        // for each TFC Heat Recipe, add it to the redstone Furnace Manager.
        for(HeatRecipe heatRecipe : TFCRegistries.HEAT.getValuesCollection())
        {
            NonNullList<IIngredient<ItemStack>> r = heatRecipe.getIngredients();
            if(r.size() > 0 && heatRecipe.getTransformTemp() < 1599f)
            {
                NonNullList<ItemStack> ingredient = r.get(0).getValidIngredients();
                ItemStack output = heatRecipe.getOutputs().get(0);
                for(ItemStack stack: ingredient) {
                    FurnaceManager.addRecipe(1000, stack, output);
                }
            }
        }
    }

    private void pulverizerRecipes() {
        // remove default Pulverizer recipes
        PulverizerManager.PulverizerRecipe[] recipes = PulverizerManager.getRecipeList();
        for(PulverizerManager.PulverizerRecipe pulverizerRecipe: recipes) {
            PulverizerManager.removeRecipe(pulverizerRecipe.getInput());
        }

        // for each TFC Quern Recipe, add it to the Pulverizer Manager.
        for(QuernRecipe recipe : TFCRegistries.QUERN.getValuesCollection()) {
            NonNullList<IIngredient<ItemStack>> ingredient = recipe.getIngredients();
            NonNullList<ItemStack> outputs = recipe.getOutputs();
            ItemStack output = outputs.get(0);
            NonNullList<ItemStack> stacks = ingredient.get(0).getValidIngredients();
            PulverizerManager.addRecipe(1000, stacks.get(0), output, output, 10);
        }
    }
}
