package embin.poosmp.client.screen;

import embin.poosmp.PooSMPMod;
import embin.poosmp.PooSMPRegistries;
import embin.poosmp.upgrade.PriceObject;
import embin.poosmp.upgrade.Upgrade;
import embin.poosmp.upgrade.Upgrades;
import embin.poosmp.util.Id;
import embin.poosmp.util.PooSMPTags;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.StatsScreen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.option.ControlsListWidget;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.gui.widget.EntryListWidget;
import net.minecraft.client.gui.widget.NarratedMultilineTextWidget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.stat.StatFormatter;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UpgradesListWidget extends EntryListWidget<UpgradesListWidget.UpgradeEntry> {
    private UpgradesScreen parent;
    //private final
    public UpgradesListWidget(MinecraftClient client, UpgradesScreen parent) {
        super(client, parent.width, parent.layout.getContentHeight(), parent.layout.getHeaderHeight(), 22);
        this.parent = parent;
        DynamicRegistryManager registryLookup = client.player.getRegistryManager();
        RegistryEntryList<Upgrade> list = getTooltipOrder(registryLookup, PooSMPRegistries.Keys.UPGRADE, PooSMPTags.Upgrades.LIST_ORDER);
        for (RegistryEntry<Upgrade> upgrade : list) {
            this.addEntry(new UpgradeEntry(upgrade.value(), client.player));
        }

        for (Upgrade upgrade : PooSMPRegistries.UPGRADE) {
            if (!list.contains(PooSMPRegistries.UPGRADE.getEntry(upgrade))) {
                this.addEntry(new UpgradeEntry(upgrade, client.player));
            }
        }
    }

    @Override
    public int getRowWidth() {
        return 200;
    }

    public static RegistryEntryList<Upgrade> getTooltipOrder(@Nullable DynamicRegistryManager registries, RegistryKey<Registry<Upgrade>> key, TagKey<Upgrade> tag) {
        if (registries != null) {
            Optional<RegistryEntryList.Named<Upgrade>> optional = registries.getWrapperOrThrow(key).getOptional(tag);
            if (optional.isPresent()) {
                return optional.get();
            }
        }
        return RegistryEntryList.of();
    }

    @Override
    protected void renderDecorations(DrawContext context, int mouseX, int mouseY) {
        if (mouseY >= this.getY() && mouseY <= this.getBottom()) {
            UpgradeEntry entry = this.getHoveredEntry();
            if (entry != null) {
                if (mouseX < this.getRowLeft() || mouseX > this.getRowLeft() + 18) {
                    return;
                }
                Upgrade upgrade = entry.getUpgrade();
                List<Text> tooltip = List.of(upgrade.getName(), upgrade.getIdAsText());
                context.drawTooltip(this.client.textRenderer, tooltip, mouseX, mouseY);
            }
        }
    }

    public void update() {
        this.children().forEach(UpgradeEntry::update);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
    }

    public class UpgradeEntry extends EntryListWidget.Entry<UpgradesListWidget.UpgradeEntry> {
        private final Upgrade upgrade;
        private final PlayerEntity player;
        private final ButtonWidget buyButton;
        private final ButtonWidget sellButton;
        //private final NarratedMultilineTextWidget text;

        UpgradeEntry(Upgrade upgrade, PlayerEntity player) {
            this.upgrade = upgrade;
            this.player = player;
            this.buyButton = ButtonWidget.builder(Text.literal("+"), button -> {

            }).dimensions(50, 50, 20, 20).build();
            this.sellButton = ButtonWidget.builder(Text.literal("-"), button -> {

            }).dimensions(30, 50, 20, 20).build();
            //this.text = new NarratedMultilineTextWidget(150, Text.literal(StatFormatter.DEFAULT.format(PriceObject.getCurrentPrice(this.upgrade, this.player))), UpgradesListWidget.this.client.textRenderer, true, 3);
            //this.text.setCentered(true);
            //this.text.setTextColor(Colors.BLACK);
            this.update();
        }

        @Override
        public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            context.drawGuiTexture(Id.ofVanilla("container/slot"), x + 1, y + 1, 18, 18);
            context.drawItemWithoutEntity(this.upgrade.icon().value().getDefaultStack(), x + 2, y + 2);
            this.buyButton.setPosition(x + 140, y);
            this.sellButton.setPosition(x + 40, y);
            String upgradePrice = StatFormatter.DEFAULT.format(PriceObject.getCurrentPrice(this.upgrade, this.player));
            int offset = UpgradesListWidget.this.client.textRenderer.getWidth(upgradePrice) / 2;
            //context.fill(x - (offset * 2) + 9, y + 3, x + 103 + offset, y + 16, Colors.WHITE);
            //context.fill(x - (offset * 2) + 10, y + 4, x + 102 + offset, y + 15, Colors.BLACK);
            //this.text.setPosition(x + 100 - offset, y + 5);
            //this.text.render(context, mouseX, mouseY, tickDelta);
            context.drawTextWithShadow(UpgradesListWidget.this.client.textRenderer, Text.literal(upgradePrice), x + 100 - offset, y + 6, Colors.WHITE);
            this.buyButton.render(context, mouseX, mouseY, tickDelta);
            this.sellButton.render(context, mouseX, mouseY, tickDelta);
            this.update();
        }

        public Upgrade getUpgrade() {
            return this.upgrade;
        }

        protected void update() {
            this.sellButton.active = PriceObject.canBeSold(this.upgrade, this.player);
            //this.text.setCentered(true);
            //this.text.setTextColor(Colors.BLACK);
        }
    }
}
