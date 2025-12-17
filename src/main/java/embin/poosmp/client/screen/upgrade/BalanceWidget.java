package embin.poosmp.client.screen.upgrade;

import embin.poosmp.client.ClientUpgradeData;
import java.text.NumberFormat;
import java.util.Locale;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.util.CommonColors;

public class BalanceWidget extends AbstractWidget {
    public BalanceWidget(int x, int y) {
        super(x, y, 4, 4, Component.empty());
    }

    @Override
    protected void renderWidget(GuiGraphics context, int mouseX, int mouseY, float delta) {
        String balance = "$" + NumberFormat.getNumberInstance(Locale.US).format(ClientUpgradeData.INSTANCE.getBalance());
        context.drawString(Minecraft.getInstance().font, balance, this.getX(), this.getY(), CommonColors.WHITE);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput builder) {
    }
}
