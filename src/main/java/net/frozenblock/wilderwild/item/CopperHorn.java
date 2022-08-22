package net.frozenblock.wilderwild.item;

import net.frozenblock.lib.sound.FrozenSoundPackets;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class CopperHorn extends Item {
    private static final String INSTRUMENT_KEY = "instrument";
    private final TagKey<Instrument> instrumentTag;
    private final int shift;

    public CopperHorn(Properties settings, TagKey<Instrument> instrumentTag, int shift) {
        super(settings);
        this.instrumentTag = instrumentTag;
        this.shift = shift;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
        super.appendHoverText(stack, world, tooltip, context);
        Optional<ResourceKey<Instrument>> optional = this.getInstrument(stack).flatMap(Holder::unwrapKey);
        if (optional.isPresent()) {
            MutableComponent mutableText = Component.translatable(Util.makeDescriptionId(INSTRUMENT_KEY, optional.get().location()));
            tooltip.add(mutableText.withStyle(ChatFormatting.GRAY));
        }

    }

    public static ItemStack getStackForInstrument(Item item, Holder<Instrument> instrument) {
        ItemStack itemStack = new ItemStack(item);
        setInstrument(itemStack, instrument);
        return itemStack;
    }

    private static void setInstrument(ItemStack stack, Holder<Instrument> instrument) {
        CompoundTag nbtCompound = stack.getOrCreateTag();
        nbtCompound.putString(
                INSTRUMENT_KEY, (instrument.unwrapKey().orElseThrow(() -> new IllegalStateException("Invalid instrument"))).location().toString()
        );
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> stacks) {
        if (this.allowedIn(group)) {
            for (Holder<Instrument> registryEntry : Registry.INSTRUMENT.getTagOrEmpty(this.instrumentTag)) {
                stacks.add(getStackForInstrument(RegisterItems.COPPER_HORN, registryEntry));
            }
        }

    }

    private Optional<Holder<Instrument>> getInstrument(ItemStack stack) {
        CompoundTag nbtCompound = stack.getTag();
        if (nbtCompound != null) {
            ResourceLocation identifier = ResourceLocation.tryParse(nbtCompound.getString(INSTRUMENT_KEY));
            if (identifier != null) {
                return Registry.INSTRUMENT.getHolder(ResourceKey.create(Registry.INSTRUMENT_REGISTRY, identifier));
            }
        }

        Iterator<Holder<Instrument>> iterator = Registry.INSTRUMENT.getTagOrEmpty(this.instrumentTag).iterator();
        return iterator.hasNext() ? Optional.of(iterator.next()) : Optional.empty();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        WilderWild.log(user, "Used Copper Horn", WilderWild.DEV_LOGGING);
        ItemStack itemStack = user.getItemInHand(hand);
        Optional<Holder<Instrument>> optional = this.getInstrument(itemStack);
        if (optional.isPresent()) {
            Instrument instrument = optional.get().value();
            user.startUsingItem(hand);

            playSound(instrument, user, world);

            return InteractionResultHolder.consume(itemStack);
        } else {
            return InteractionResultHolder.fail(itemStack);
        }
    }

    private void playSound(Instrument instrument, Player user, Level world) {
        SoundEvent soundEvent = instrument.soundEvent();
        float range = instrument.range() / 16.0F;
        int note = (int) ((-user.getXRot() + 90) / 7.5);

        if (!world.isClientSide) {
            float soundPitch = !user.isShiftKeyDown() ?
                    (float) Math.pow(2.0D, (note - 12.0F) / 12.0D) :
                    (float) Math.pow(2.0D, 0.01111F * -user.getXRot());
            FrozenSoundPackets.createMovingRestrictionLoopingSound(world, user, soundEvent, SoundSource.RECORDS, range, soundPitch, WilderWild.id("copper_horn"));
        }
        world.gameEvent(GameEvent.INSTRUMENT_PLAY, user.position(), GameEvent.Context.of(user));
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        Optional<Holder<Instrument>> optional = this.getInstrument(stack);
        return optional.map(instrumentRegistryEntry -> instrumentRegistryEntry.value().useDuration()).orElse(0);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.TOOT_HORN;
    }

}
