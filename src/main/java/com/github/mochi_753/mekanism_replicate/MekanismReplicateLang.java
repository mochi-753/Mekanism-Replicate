package com.github.mochi_753.mekanism_replicate;

import mekanism.api.text.ILangEntry;
import mekanism.common.Mekanism;
import net.minecraft.Util;
import org.jetbrains.annotations.NotNull;

public enum MekanismReplicateLang implements ILangEntry {
    DESCRIPTION_REPLICATOR("description", "replicator");

    private final String key;

    MekanismReplicateLang(String type, String path) {
        this(Util.makeDescriptionId(type, Mekanism.rl(path)));
    }

    MekanismReplicateLang(String key) {
        this.key = key;
    }

    @Override
    public @NotNull String getTranslationKey() {
        return key;
    }
}
