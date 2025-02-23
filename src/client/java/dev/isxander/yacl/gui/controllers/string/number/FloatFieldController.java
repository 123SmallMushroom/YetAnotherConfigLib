package dev.isxander.yacl.gui.controllers.string.number;

import dev.isxander.yacl.api.Option;
import dev.isxander.yacl.gui.controllers.slider.FloatSliderController;
import net.minecraft.network.chat.Component;

import java.util.function.Function;

/**
 * {@inheritDoc}
 */
public class FloatFieldController extends NumberFieldController<Float> {
    private final float min, max;

    /**
     * Constructs a double field controller
     *
     * @param option option to bind controller to
     * @param min minimum allowed value (clamped on apply)
     * @param max maximum allowed value (clamped on apply)
     * @param formatter display text, not used whilst editing
     */
    public FloatFieldController(Option<Float> option, float min, float max, Function<Float, Component> formatter) {
        super(option, formatter);
        this.min = min;
        this.max = max;
    }

    /**
     * Constructs a double field controller.
     * Uses {@link FloatSliderController#DEFAULT_FORMATTER} as display text,
     * not used whilst editing.
     *
     * @param option option to bind controller to
     * @param min minimum allowed value (clamped on apply)
     * @param max maximum allowed value (clamped on apply)
     */
    public FloatFieldController(Option<Float> option, float min, float max) {
        this(option, min, max, FloatSliderController.DEFAULT_FORMATTER);
    }

    /**
     * Constructs a double field controller.
     * Does not have a minimum or a maximum range.
     *
     * @param option option to bind controller to
     * @param formatter display text, not used whilst editing
     */
    public FloatFieldController(Option<Float> option, Function<Float, Component> formatter) {
        this(option, -Float.MAX_VALUE, Float.MAX_VALUE, formatter);
    }

    /**
     * Constructs a double field controller.
     * Uses {@link FloatSliderController#DEFAULT_FORMATTER} as display text,
     * not used whilst editing.
     * Does not have a minimum or a maximum range.
     *
     * @param option option to bind controller to
     */
    public FloatFieldController(Option<Float> option) {
        this(option, -Float.MAX_VALUE, Float.MAX_VALUE, FloatSliderController.DEFAULT_FORMATTER);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double min() {
        return this.min;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double max() {
        return this.max;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getString() {
        return String.valueOf(option().pendingValue());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPendingValue(double value) {
        option().requestSet((float) value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double pendingValue() {
        return option().pendingValue();
    }
}
