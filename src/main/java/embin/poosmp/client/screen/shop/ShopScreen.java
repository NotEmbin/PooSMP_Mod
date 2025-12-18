package embin.poosmp.client.screen.shop;

import embin.poosmp.economy.shop.ShopCategory;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

// TODO: finish shop screen
public class ShopScreen extends Screen {
    protected final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this);

    protected ShopScreen() {
        super(Component.literal("Shop"));
    }

    @Override
    protected void init() {
        this.layout.addToFooter(Button.builder(Component.literal("Exit"), button -> this.onClose()).width(200).build());
        this.layout.visitWidgets(this::addRenderableWidget);
    }

    private class ShopCategoryWidget extends ContainerObjectSelectionList<ShopCategoryWidget.ShopCategoryEntry> {
        private final ShopCategory shopCategory;

        public ShopCategoryWidget(Minecraft minecraftClient, ShopCategory shopCategory) {
            super(minecraftClient, ShopScreen.this.width, 400, 55, 44);
            this.shopCategory = shopCategory;
        }

        private class ShopCategoryEntry extends ContainerObjectSelectionList.Entry<ShopCategoryWidget.ShopCategoryEntry> {
            private final ShopCategory shopCategory;

            public ShopCategoryEntry(ShopCategory shopCategory) {
                this.shopCategory = shopCategory;
            }

            @Override
            public List<? extends NarratableEntry> narratables() {
                return List.of();
            }

            @Override
            public List<? extends GuiEventListener> children() {
                return List.of();
            }

            @Override
            public void renderContent(GuiGraphics guiGraphics, int i, int j, boolean bl, float f) {

            }
        }
    }

    private class ShopContentsWidget extends ContainerObjectSelectionList<ShopContentsWidget.ShopContentsEntry> {
        private final ShopCategory shopCategory;

        public ShopContentsWidget(Minecraft minecraftClient, ShopCategory shopCategory) {
            super(minecraftClient, ShopScreen.this.width, 400, 55, 44);
            this.shopCategory = shopCategory;
        }

        private class ShopContentsEntry extends ContainerObjectSelectionList.Entry<ShopContentsWidget.ShopContentsEntry> {
            private final ShopCategory shopCategory;

            public ShopContentsEntry(ShopCategory shopCategory) {
                this.shopCategory = shopCategory;
            }

            @Override
            public List<? extends NarratableEntry> narratables() {
                return List.of();
            }

            @Override
            public List<? extends GuiEventListener> children() {
                return List.of();
            }

            @Override
            public void renderContent(GuiGraphics guiGraphics, int i, int j, boolean bl, float f) {

            }
        }
    }
}
