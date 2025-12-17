package embin.poosmp.client.screen.shop;

import com.google.common.collect.Lists;
import embin.poosmp.client.screen.upgrade.BalanceWidget;
import embin.poosmp.util.Id;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;

@Deprecated
public class ShopScreenOld extends AbstractContainerScreen<ShopScreenHandler> {
    private static final Identifier TEXTURE = Id.of("textures/gui/container/shop.png");
    private final List<AbstractWidget> buttons = Lists.newArrayList();

    public ShopScreenOld(ShopScreenHandler handler, Inventory inventory) {
        super(handler, inventory, Component.empty());
    }

    @Override
    protected void renderBg(GuiGraphics context, float delta, int mouseX, int mouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        context.blit(TEXTURE, i, j, 0, 0, this.imageWidth, this.imageHeight);
        context.pose().push();
        context.pose().translate(0.0F, 0.0F, 100.0F);
    }

    @Override
    protected void init() {
        super.init();
        this.buttons.clear();
        this.addButton(new BalanceWidget(((this.width - this.imageWidth) / 2) - 30, 100));
        this.addButton(new PurchaseButtonWidget(((this.width - this.imageWidth) / 2) + 10, 100, button -> {
            if (this.minecraft == null) return;
            if (this.minecraft.player == null) return;
            this.minecraft.player.makeSound(SoundEvents.THORNS_HIT);
        }));
    }

    protected <T extends AbstractWidget> void addButton(T button) {
        this.addRenderableWidget(button);
        this.buttons.add(button);
    }

    public class PurchaseButtonWidget extends Button {
        public static final Plain PURCHASE_TEXT = Plain.translatable("button.poosmp.purchase");

        protected PurchaseButtonWidget(int x, int y, OnPress onPress) {
            super(ShopScreenOld.this.width / 2, y, 48, 20, PURCHASE_TEXT, onPress, null);
        }

        @Override
        protected void renderWidget(GuiGraphics context, int mouseX, int mouseY, float delta) {
            super.renderWidget(context, mouseX, mouseY, delta);
            this.active = ShopScreenOld.this.menu.selectedItem != null;
        }
    }
}
