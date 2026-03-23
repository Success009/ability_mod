package com.ability.mod.mixin;

import com.ability.mod.client.AbilityModClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
    private ButtonWidget joinButton;

    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("RETURN"), method = "init")
    private void onInit(CallbackInfo ci) {
        for (Drawable drawable : ((ScreenAccessor)this).getDrawables()) {
            if (drawable instanceof ClickableWidget widget) {
                if (widget instanceof ButtonWidget button) {
                    String text = button.getMessage().getString().toLowerCase();
                    if (text.contains("realms")) {
                        button.visible = false;
                        button.active = false;
                        
                        this.joinButton = this.addDrawableChild(ButtonWidget.builder(Text.literal("§7[BOOTING...]"), (b) -> {
                            AbilityModClient.joinServer(this);
                        })
                        .dimensions(button.getX(), button.getY(), button.getWidth(), button.getHeight())
                        .build());
                        
                        this.joinButton.active = false;
                        break;
                    }
                }
            }
        }
    }

    @Inject(at = @At("TAIL"), method = "render")
    private void onRender(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        String status = getBridgeStatus();
        String color = "§7";
        if (status.equalsIgnoreCase("ONLINE")) {
            color = "§a";
            status = "GATEWAY ACTIVE";
        } else {
            status = "INITIALIZING GATEWAY...";
        }
        context.drawCenteredTextWithShadow(this.textRenderer, color + "● " + status, this.width / 2, this.height / 4 + 48 + 24 * 2 + 25, 0xFFFFFF);
    }

    @Inject(at = @At("RETURN"), method = "tick")
    private void onTick(CallbackInfo ci) {
        if (this.joinButton != null) {
            if (getBridgeStatus().equalsIgnoreCase("ONLINE")) {
                this.joinButton.setMessage(Text.literal("§6§lJOIN ABILITY SMP"));
                this.joinButton.active = true;
            } else {
                this.joinButton.setMessage(Text.literal("§eCONNECTING..."));
                this.joinButton.active = false;
            }
        }
    }

    private String getBridgeStatus() {
        try {
            Path logPath = Path.of(System.getenv("LOCALAPPDATA"), "ability_gateway.log");
            if (Files.exists(logPath)) {
                List<String> lines = Files.readAllLines(logPath);
                if (!lines.isEmpty()) {
                    for (int i = lines.size() - 1; i >= 0; i--) {
                        String line = lines.get(i);
                        if (line.contains("GATEWAY ONLINE")) return "ONLINE";
                    }
                }
            }
        } catch (Exception ignored) {}
        return "BOOTING";
    }
}
