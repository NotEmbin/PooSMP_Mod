package embin.poosmp.client.screen.shop;

import com.google.common.collect.Lists;
import embin.poosmp.client.screen.upgrade.BalanceWidget;
import embin.poosmp.util.Id;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

@Deprecated
public class ShopScreenOld extends HandledScreen<ShopScreenHandler> {
    private static final Identifier TEXTURE = Id.of("textures/gui/container/shop.png");
    private final List<ClickableWidget> buttons = Lists.newArrayList();

    public ShopScreenOld(ShopScreenHandler handler, PlayerInventory inventory) {
        super(handler, inventory, Text.empty());
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        context.drawTexture(TEXTURE, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
        context.getMatrices().push();
        context.getMatrices().translate(0.0F, 0.0F, 100.0F);
    }

    @Override
    protected void init() {
        super.init();
        this.buttons.clear();
        this.addButton(new BalanceWidget(((this.width - this.backgroundWidth) / 2) - 30, 100));
        this.addButton(new PurchaseButtonWidget(((this.width - this.backgroundWidth) / 2) + 10, 100, button -> {
            if (this.client == null) return;
            if (this.client.player == null) return;
            this.client.player.playSound(SoundEvents.ENCHANT_THORNS_HIT);
        }));
    }

    protected <T extends ClickableWidget> void addButton(T button) {
        this.addDrawableChild(button);
        this.buttons.add(button);
    }

    public class PurchaseButtonWidget extends ButtonWidget {
        public static final Text PURCHASE_TEXT = Text.translatable("button.poosmp.purchase");

        protected PurchaseButtonWidget(int x, int y, PressAction onPress) {
            super(ShopScreenOld.this.width / 2, y, 48, 20, PURCHASE_TEXT, onPress, null);
        }

        @Override
        protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
            super.renderWidget(context, mouseX, mouseY, delta);
            this.active = ShopScreenOld.this.handler.selectedItem != null;
        }
    }
}
