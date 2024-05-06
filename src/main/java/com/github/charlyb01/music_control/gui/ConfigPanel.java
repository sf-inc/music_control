package com.github.charlyb01.music_control.gui;

import com.github.charlyb01.music_control.ResourcePackUtils;
import com.github.charlyb01.music_control.config.ModConfig;
import com.github.charlyb01.music_control.gui.components.Button;
import com.github.charlyb01.music_control.gui.components.SoundConfigPanel;
import com.github.charlyb01.music_control.gui.components.SoundListPanel;
import io.github.cottonmc.cotton.gui.widget.WBox;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WCardPanel;
import io.github.cottonmc.cotton.gui.widget.WText;
import io.github.cottonmc.cotton.gui.widget.data.Axis;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.pack.PackScreen;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ConfigPanel extends WBox {
    protected static boolean isEvent = true;

    protected Button selectedButton;
    protected SoundConfigPanel soundConfigPanel;
    protected WCardPanel cardPanel = new WCardPanel();
    protected WBox resourcePackCard = new WBox(Axis.VERTICAL);
    protected WBox musicConfigCard = new WBox(Axis.VERTICAL);
    protected Screen previousScreen = null;
    protected final MinecraftClient client;

    public ConfigPanel(final MinecraftClient client) {
        super(Axis.VERTICAL);
        this.setInsets(Insets.ROOT_PANEL);
        this.setHorizontalAlignment(HorizontalAlignment.LEFT);
        this.client = client;

        this.setupResourcePackPanel();
        this.setupConfigMusicPanel();

        this.cardPanel.add(this.resourcePackCard);
        this.cardPanel.add(this.musicConfigCard);
        this.add(this.cardPanel);

        this.updateLayout();
    }

    private void updateLayout() {
        if (ResourcePackUtils.wasCreatedOrIsEnabled()) {
            this.musicConfigCard.layout();
            this.cardPanel.setSelectedCard(this.musicConfigCard);
        } else {
            this.resourcePackCard.layout();
            this.cardPanel.setSelectedCard(this.resourcePackCard);
        }
    }

    private void backToMusicScreen(ResourcePackManager manager) {
        this.client.options.refreshResourcePacks(manager);
        this.client.setScreen(this.previousScreen);
        this.previousScreen = null;
        this.updateLayout();
    }

    private void setupResourcePackPanel() {
        WText text = new WText(Text.translatable("gui.music_control.label.resourcePack"));
        text.setHorizontalAlignment(HorizontalAlignment.CENTER);

        WButton enableButton = new WButton(Text.translatable("gui.music_control.button.enable"));
        enableButton.setEnabled(ResourcePackUtils.exists());
        enableButton.setOnClick(() -> {
            this.previousScreen = this.client.currentScreen;
            this.client.setScreen(new PackScreen(
                    this.client.getResourcePackManager(),
                    this::backToMusicScreen,
                    this.client.getResourcePackDir(),
                    Text.translatable("resourcePack.title")));
        });

        WButton createButton = new WButton(Text.translatable("gui.music_control.button.create"));
        createButton.setOnClick(() -> {
            ResourcePackUtils.createResourcePack();
            this.updateLayout();
        });

        WBox buttonsBox = new WBox(Axis.HORIZONTAL);
        buttonsBox.setHorizontalAlignment(HorizontalAlignment.CENTER);
        buttonsBox.add(enableButton, 100, 20);
        buttonsBox.add(createButton, 100, 20);

        this.resourcePackCard.add(text, ModConfig.get().cosmetics.gui.width, 20);
        this.resourcePackCard.add(buttonsBox, ModConfig.get().cosmetics.gui.width, 20);
    }

    private void setupConfigMusicPanel() {
        final WBox listsBox = new WBox(Axis.HORIZONTAL);
        final int outerWidth = ModConfig.get().cosmetics.gui.width;
        final int innerWidth = ModConfig.get().cosmetics.gui.width - 4;

        BiConsumer<Identifier, Button> onSoundClicked = (Identifier identifier, Button button) -> {
            if (this.selectedButton != null) {
                this.selectedButton.setEnabled(true);
            }
            this.selectedButton = button;
            this.selectedButton.setEnabled(false);

            if (this.soundConfigPanel != null) {
                listsBox.remove(this.soundConfigPanel);
            }
            this.soundConfigPanel = new SoundConfigPanel(identifier, isEvent, innerWidth / 2);
            listsBox.add(this.soundConfigPanel);
            this.soundConfigPanel.setHost(listsBox.getHost());
            listsBox.layout();
        };
        Consumer<Boolean> onToggle = (Boolean isEvent) -> {
            ConfigPanel.isEvent = isEvent;

            if (soundConfigPanel != null) {
                listsBox.remove(this.soundConfigPanel);
                this.soundConfigPanel = null;
                if (this.selectedButton != null) {
                    this.selectedButton.setEnabled(true);
                    this.selectedButton = null;
                }
            }
        };

        listsBox.add(new SoundListPanel(onSoundClicked, onSoundClicked, onToggle, innerWidth / 2, isEvent));

        WButton saveButton = new WButton(Text.translatable("gui.music_control.button.save"));
        saveButton.setOnClick(() -> {
            ResourcePackUtils.writeConfig();
            this.client.reloadResources();
        });
        WBox buttonBox = new WBox(Axis.HORIZONTAL);
        buttonBox.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        buttonBox.add(saveButton, 100, 20);

        this.musicConfigCard.add(
                listsBox,
                outerWidth,
                ModConfig.get().cosmetics.gui.height - 20
        );
        this.musicConfigCard.add(buttonBox, outerWidth, 20);
    }
}
