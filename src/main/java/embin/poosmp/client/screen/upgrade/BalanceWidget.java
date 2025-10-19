package embin.poosmp.client.screen.upgrade;

import embin.poosmp.client.ClientUpgradeData;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.stat.StatFormatter;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;

import java.text.NumberFormat;
import java.util.Locale;

public class BalanceWidget extends ClickableWidget {
    public BalanceWidget(int x, int y) {
        super(x, y, 4, 4, Text.empty());
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        String balance = "$" + NumberFormat.getNumberInstance(Locale.US).format(ClientUpgradeData.INSTANCE.getBalance());
        context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, balance, this.getX(), this.getY(), Colors.WHITE);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
    }
}
