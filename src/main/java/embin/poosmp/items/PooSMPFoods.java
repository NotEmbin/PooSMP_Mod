package embin.poosmp.items;

import net.minecraft.component.type.FoodComponent;

public class PooSMPFoods {
    public static final FoodComponent POOPLET = new FoodComponent.Builder().snack().nutrition(6).saturationModifier(0.9f).build();
    public static final FoodComponent BANANA = new FoodComponent.Builder().snack().nutrition(5).saturationModifier(0.7f).build();
}
