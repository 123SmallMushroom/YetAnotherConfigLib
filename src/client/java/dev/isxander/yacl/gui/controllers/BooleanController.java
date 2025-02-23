package dev.isxander.yacl.gui.controllers;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.isxander.yacl.api.Controller;
import dev.isxander.yacl.api.Option;
import dev.isxander.yacl.api.utils.Dimension;
import dev.isxander.yacl.gui.AbstractWidget;
import dev.isxander.yacl.gui.YACLScreen;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import java.util.function.Function;

/**
 * This controller renders a simple formatted {@link Component}
 */
public class BooleanController implements Controller<Boolean> {

    public static final Function<Boolean, Component> ON_OFF_FORMATTER = (state) ->
            state
                    ? CommonComponents.OPTION_ON
                    : CommonComponents.OPTION_OFF;

    public static final Function<Boolean, Component> TRUE_FALSE_FORMATTER = (state) ->
            state
                    ? Component.translatable("yacl.control.boolean.true")
                    : Component.translatable("yacl.control.boolean.false");

    public static final Function<Boolean, Component> YES_NO_FORMATTER = (state) ->
            state
                    ? CommonComponents.GUI_YES
                    : CommonComponents.GUI_NO;

    private final Option<Boolean> option;
    private final Function<Boolean, Component> valueFormatter;
    private final boolean coloured;

    /**
     * Constructs a tickbox controller
     * with the default value formatter of {@link BooleanController#ON_OFF_FORMATTER}
     *
     * @param option bound option
     */
    public BooleanController(Option<Boolean> option) {
        this(option, ON_OFF_FORMATTER, false);
    }

    /**
     * Constructs a tickbox controller
     * with the default value formatter of {@link BooleanController#ON_OFF_FORMATTER}
     *
     * @param option bound option
     * @param coloured value format is green or red depending on the state
     */
    public BooleanController(Option<Boolean> option, boolean coloured) {
        this(option, ON_OFF_FORMATTER, coloured);
    }

    /**
     * Constructs a tickbox controller
     *
     * @param option bound option
     * @param valueFormatter format value into any {@link Component}
     * @param coloured value format is green or red depending on the state
     */
    public BooleanController(Option<Boolean> option, Function<Boolean, Component> valueFormatter, boolean coloured) {
        this.option = option;
        this.valueFormatter = valueFormatter;
        this.coloured = coloured;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Option<Boolean> option() {
        return option;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Component formatValue() {
        return valueFormatter.apply(option().pendingValue());
    }

    /**
     * Value format is green or red depending on the state
     */
    public boolean coloured() {
        return coloured;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AbstractWidget provideWidget(YACLScreen screen, Dimension<Integer> widgetDimension) {
        return new BooleanControllerElement(this, screen, widgetDimension);
    }

    public static class BooleanControllerElement extends ControllerWidget<BooleanController> {
        public BooleanControllerElement(BooleanController control, YACLScreen screen, Dimension<Integer> dim) {
            super(control, screen, dim);
        }

        @Override
        protected void drawHoveredControl(PoseStack matrices, int mouseX, int mouseY, float delta) {

        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (!isMouseOver(mouseX, mouseY) || !isAvailable())
                return false;

            toggleSetting();
            return true;
        }

        @Override
        protected int getHoveredControlWidth() {
            return getUnhoveredControlWidth();
        }

        public void toggleSetting() {
            control.option().requestSet(!control.option().pendingValue());
            playDownSound();
        }

        @Override
        protected Component getValueText() {
            if (control.coloured()) {
                return super.getValueText().copy().withStyle(control.option().pendingValue() ? ChatFormatting.GREEN : ChatFormatting.RED);
            }

            return super.getValueText();
        }

        @Override
        public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
            if (!focused) {
                return false;
            }

            if (keyCode == InputConstants.KEY_RETURN || keyCode == InputConstants.KEY_SPACE || keyCode == InputConstants.KEY_NUMPADENTER) {
                toggleSetting();
                return true;
            }

            return false;
        }
    }
}
