package com.zombie_cute.mc.bakingdelight.util.components.cumstom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public record FlavorListComponent(List<FlavorComponent> flavors) {
    public static final Codec<FlavorListComponent> CODEC = RecordCodecBuilder.create(builder ->
            builder.group(
                    Codec.list(FlavorComponent.CODEC).fieldOf("flavors").forGetter(FlavorListComponent::flavors)
            ).apply(builder, FlavorListComponent::new));
}
