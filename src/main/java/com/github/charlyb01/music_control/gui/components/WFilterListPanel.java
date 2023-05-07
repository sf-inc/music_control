package com.github.charlyb01.music_control.gui.components;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import io.github.cottonmc.cotton.gui.widget.WListPanel;
import io.github.cottonmc.cotton.gui.widget.WWidget;

public class WFilterListPanel<D, W extends WWidget> extends WListPanel<D, W> {

  protected final List<D> dataOrig;

  public WFilterListPanel(List<D> data, Supplier<W> supplier, BiConsumer<D, W> configurator) {
    super(data, supplier, configurator);
    this.dataOrig = data;
  }

  public void runFilter(Function<D, Boolean> filter) {

    if (filter == null) {
      this.data = this.dataOrig;
      this.layout();
      return;
    }

    ArrayList<D> dataResult = new ArrayList<>();
    for (var item : dataOrig) {
      if (filter.apply(item)) {
        dataResult.add(item);
      }
    }

    this.data = dataResult;
    this.layout();
  }
}
