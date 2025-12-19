package embin.poosmp.client.screen.upgrade;

import embin.poosmp.PooSMPRegistries;
import embin.poosmp.PooSMPSavedData;
import embin.poosmp.client.ClientUpgradeData;
import embin.poosmp.networking.payload.BuyUpgradePayload;
import embin.poosmp.networking.payload.SellUpgradePayload;
import embin.poosmp.upgrade.PriceObject;
import embin.poosmp.upgrade.Upgrade;
import embin.poosmp.util.Id;
import embin.poosmp.util.PooSMPTags;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.StatFormatter;
import net.minecraft.tags.TagKey;
import net.minecraft.util.CommonColors;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class UpgradesListWidget extends ContainerObjectSelectionList<UpgradesListWidget.UpgradeEntry> {
    private UpgradesScreen parent;
    private Registry<Upgrade> upgradeRegistry;
    //private final
    public UpgradesListWidget(Minecraft client, UpgradesScreen parent) {
        super(client, parent.width, parent.layout.getContentHeight(), parent.layout.getHeaderHeight(), 22);
        this.parent = parent;
        RegistryAccess registryLookup = client.player.registryAccess();
        this.upgradeRegistry = registryLookup.lookupOrThrow(PooSMPRegistries.Keys.UPGRADE);

        HolderSet<Upgrade> list = getTooltipOrder(registryLookup, PooSMPRegistries.Keys.UPGRADE, PooSMPTags.Upgrades.LIST_ORDER);
        for (Holder<Upgrade> upgrade : list) {
            this.addEntry(new UpgradeEntry(upgrade.value(), client.player, this.upgradeRegistry));
        }

        for (Upgrade upgrade : this.upgradeRegistry) {
            if (!list.contains(this.upgradeRegistry.wrapAsHolder(upgrade))) {
                this.addEntry(new UpgradeEntry(upgrade, client.player, this.upgradeRegistry));
            }
        }
    }

    @Override
    public int getRowWidth() {
        return 200;
    }

    public static HolderSet<Upgrade> getTooltipOrder(@Nullable RegistryAccess registries, ResourceKey<Registry<Upgrade>> key, TagKey<Upgrade> tag) {
        if (registries != null) {
            Optional<HolderSet.Named<Upgrade>> optional = registries.lookupOrThrow(key).get(tag);
            if (optional.isPresent()) {
                return optional.get();
            }
        }
        return HolderSet.direct();
    }

    protected void renderDecorations(GuiGraphics context, int mouseX, int mouseY) {
        if (mouseY >= this.getY() && mouseY <= this.getBottom()) {
            UpgradeEntry entry = this.getHovered();
            if (entry != null) {
                if (mouseX < this.getRowLeft() || mouseX > this.getRowLeft() + 18) {
                    return;
                }
                Upgrade upgrade = entry.getUpgrade();
                List<Component> tooltip = List.of(upgrade.getName(this.upgradeRegistry), upgrade.getIdAsText(this.upgradeRegistry));
                context.setComponentTooltipForNextFrame(this.minecraft.font, tooltip, mouseX, mouseY);
            }
        }
    }

    public void update() {
        this.children().forEach(UpgradeEntry::update);
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
        super.renderWidget(guiGraphics, i, j, f);
        this.renderDecorations(guiGraphics, i, j);
    }

    public class UpgradeEntry extends ContainerObjectSelectionList.Entry<UpgradesListWidget.UpgradeEntry> {
        protected static final Identifier SLOT_TEXTURE = Id.ofVanilla("container/slot");
        private final Upgrade upgrade;
        private final Player player;
        private final Button buyButton;
        private final Button sellButton;
        private int ticksSincePurchase = 0;
        //private final NarratedMultilineTextWidget text;

        UpgradeEntry(Upgrade upgrade, Player player, Registry<Upgrade> upgradeRegistry) {
            this.upgrade = upgrade;
            this.player = player;
            this.buyButton = Button.builder(Component.literal("+"), button -> {
                ClientPlayNetworking.send(new BuyUpgradePayload(upgradeRegistry.getId(this.upgrade)));
                this.ticksSincePurchase = 0;
                button.active = false;
                UpgradesListWidget.this.update();
            }).bounds(50, 50, 20, 20).build();
            this.sellButton = Button.builder(Component.literal("-"), button -> {
                ClientPlayNetworking.send(new SellUpgradePayload(upgradeRegistry.getId(this.upgrade)));
                player.makeSound(SoundEvents.AMETHYST_BLOCK_BREAK);
                UpgradesListWidget.this.update();
            }).bounds(30, 50, 20, 20).build();
            //this.text = new NarratedMultilineTextWidget(150, Text.literal(StatFormatter.DEFAULT.format(PriceObject.getCurrentPrice(this.upgrade, this.player))), UpgradesListWidget.this.client.textRenderer, true, 3);
            //this.text.setCentered(true);
            //this.text.setTextColor(Colors.BLACK);
            this.update();
        }

        @Override
        public void renderContent(GuiGraphics guiGraphics, int mouseX, int mouseY, boolean hovered, float f) {
            final int x = this.getX();
            final int y = this.getY();
            guiGraphics.blitSprite(RenderPipelines.GUI, SLOT_TEXTURE, x + 1, y + 1, 18, 18);
            guiGraphics.renderFakeItem(this.upgrade.icon().getDefaultInstance(), x + 2, y + 2);
            this.buyButton.setPosition(x + 175, y);
            this.sellButton.setPosition(x + 80, y);
            final int amountPurchased = PooSMPSavedData.Client.INSTANCE.upgradePurchaseAmount(this.player, this.upgrade);
            String upgradePrice = StatFormatter.DEFAULT.format(PriceObject.getCurrentPrice(this.upgrade, this.player, amountPurchased));
            int offset = UpgradesListWidget.this.minecraft.font.width(upgradePrice) / 2;
            if (amountPurchased > 0) {
                Tooltip tooltip = Tooltip.create(Component.literal("+" + PriceObject.getCurrentPrice(this.upgrade, this.player, amountPurchased - 1)));
                this.sellButton.setTooltip(tooltip);
            } else {
                this.sellButton.setTooltip(null);
            }
            if (!canBeBought(this.upgrade, this.player)) {
                this.buyButton.setTooltip(Tooltip.create(Component.literal("Out of stock or insufficient xp level!")));
            } else if (this.upgrade.max_purchases().isPresent()){
                String tooltip = String.format("%s/%s", amountPurchased, this.upgrade.max_purchases().get());
                this.buyButton.setTooltip(Tooltip.create(Component.literal(tooltip)));
            } else {
                this.buyButton.setTooltip(null);
            }
            //context.fill(x - (offset * 2) + 9, y + 3, x + 103 + offset, y + 16, Colors.WHITE);
            //context.fill(x - (offset * 2) + 10, y + 4, x + 102 + offset, y + 15, Colors.BLACK);
            //this.text.setPosition(x + 100 - offset, y + 5);
            //this.text.render(context, mouseX, mouseY, tickDelta);
            guiGraphics.drawString(UpgradesListWidget.this.minecraft.font, Component.literal(upgradePrice), x + 138 - offset, y + 6, CommonColors.WHITE);
            guiGraphics.drawCenteredString(UpgradesListWidget.this.minecraft.font, StatFormatter.DEFAULT.format(amountPurchased), x + 50, y + 6, CommonColors.WHITE);
            this.buyButton.render(guiGraphics, mouseX, mouseY, f);
            this.sellButton.render(guiGraphics, mouseX, mouseY, f);
            guiGraphics.drawCenteredString(UpgradesListWidget.this.minecraft.font, "0", UpgradesListWidget.this.width / 2, 10, CommonColors.WHITE);
            this.update();
        }

        public Upgrade getUpgrade() {
            return this.upgrade;
        }

        protected static boolean canBeSold(Upgrade upgrade, Player player) {
            if (PooSMPSavedData.Client.INSTANCE.upgradePurchaseAmount(player, upgrade) <= 0) return false;
            return upgrade.can_be_sold();
        }

        // client side check :trollface:
        protected static boolean canBeBought(Upgrade upgrade, Player player) {
            int amountPurchased = PooSMPSavedData.Client.INSTANCE.upgradePurchaseAmount(player, upgrade);
            int upgradePrice = PriceObject.getCurrentPrice(upgrade, player, amountPurchased);
            if (player.experienceLevel < upgradePrice) return false;
            return PooSMPSavedData.Client.INSTANCE.upgradePurchaseAmount(player, upgrade) < upgrade.maxPurchases();
        }

        protected void update() {
            this.sellButton.active = canBeSold(this.upgrade, this.player) && this.ticksSincePurchase >= 120;
            this.buyButton.active = canBeBought(this.upgrade,this.player) && this.ticksSincePurchase >= 120;
            this.ticksSincePurchase++;
            //this.text.setCentered(true);
            //this.text.setTextColor(Colors.BLACK);
        }

        @Override
        public List<? extends NarratableEntry> narratables() {
            return List.of(this.buyButton, this.sellButton);
        }

        @Override
        public List<? extends GuiEventListener> children() {
            return List.of(this.buyButton, this.sellButton);
        }
    }
}
