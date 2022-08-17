/*
 * Copyright 2022 QuiltMC
 * Modified to work on Fabric
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.quiltmc.qsl.frozenblock.datafixerupper.mixin;

import org.quiltmc.qsl.frozenblock.datafixerupper.impl.QuiltDataFixesInternals;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.ChunkSerializer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/**
 * Modified to work on Fabric
 */
@Mixin(value = ChunkSerializer.class, priority = 1001)
public abstract class ChunkSerializerMixin {
    @ModifyVariable(
            method = "serialize",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NbtCompound;putInt(Ljava/lang/String;I)V", ordinal = 0, shift = At.Shift.AFTER), index = 3
    )
    private static NbtCompound addModDataVersions(NbtCompound compound) {
        return QuiltDataFixesInternals.addModDataVersions(compound);
    }
}