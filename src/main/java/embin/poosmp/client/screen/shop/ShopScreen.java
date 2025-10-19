package embin.poosmp.client.screen.shop;

import embin.poosmp.economy.shop.ShopCategory;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.gui.widget.ThreePartsLayoutWidget;
import net.minecraft.text.Text;

import java.util.List;

// TODO: finish shop screen
public class ShopScreen extends Screen {
    protected final ThreePartsLayoutWidget layout = new ThreePartsLayoutWidget(this);

    protected ShopScreen() {
        super(Text.literal("Shop"));
    }

    @Override
    protected void init() {
        this.layout.addFooter(ButtonWidget.builder(Text.literal("Exit"), button -> this.close()).width(200).build());
        this.layout.forEachChild(this::addDrawableChild);
    }

    private class ShopCategoryWidget extends ElementListWidget<ShopCategoryWidget.ShopCategoryEntry> {
        private final ShopCategory shopCategory;

        public ShopCategoryWidget(MinecraftClient minecraftClient, ShopCategory shopCategory) {
            super(minecraftClient, ShopScreen.this.width, 400, 55, 44);
            this.shopCategory = shopCategory;
        }

        private class ShopCategoryEntry extends ElementListWidget.Entry<ShopCategoryWidget.ShopCategoryEntry> {
            private final ShopCategory shopCategory;

            public ShopCategoryEntry(ShopCategory shopCategory) {
                this.shopCategory = shopCategory;
            }

            @Override
            public List<? extends Selectable> selectableChildren() {
                return List.of();
            }

            @Override
            public List<? extends Element> children() {
                return List.of();
            }

            @Override
            public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {

            }
        }
    }

    private class ShopContentsWidget extends ElementListWidget<ShopContentsWidget.ShopContentsEntry> {
        private final ShopCategory shopCategory;

        public ShopContentsWidget(MinecraftClient minecraftClient, ShopCategory shopCategory) {
            super(minecraftClient, ShopScreen.this.width, 400, 55, 44);
            this.shopCategory = shopCategory;
        }

        private class ShopContentsEntry extends ElementListWidget.Entry<ShopContentsWidget.ShopContentsEntry> {
            private final ShopCategory shopCategory;

            public ShopContentsEntry(ShopCategory shopCategory) {
                this.shopCategory = shopCategory;
            }

            @Override
            public List<? extends Selectable> selectableChildren() {
                return List.of();
            }

            @Override
            public List<? extends Element> children() {
                return List.of();
            }

            @Override
            public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {

            }
        }
    }
}
