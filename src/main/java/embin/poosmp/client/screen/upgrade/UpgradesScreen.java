package embin.poosmp.client.screen.upgrade;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;

public class UpgradesScreen extends GameOptionsScreen {
    private UpgradesListWidget upgradesList;

    public UpgradesScreen(Screen parent) {
        super(parent, MinecraftClient.getInstance().options, Text.literal("Upgrades"));
    }

    @Override
    protected void init() {
        this.initHeader();
        this.initBody();
        this.initFooter();
        this.layout.forEachChild(element -> {
            if (element instanceof TextWidget || element instanceof ButtonWidget) {
                this.addDrawableChild(element);
            }
        });
        this.addDrawableChild(this.upgradesList);
    }

    @Override
    protected void initBody() {
        this.upgradesList = this.layout.addBody(new UpgradesListWidget(this.client, this));
        this.upgradesList.update();
    }

    @Override
    protected void addOptions() {}

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        this.upgradesList.update();
        this.layout.refreshPositions();
    }
}
