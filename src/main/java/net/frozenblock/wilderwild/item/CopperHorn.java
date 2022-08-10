package net.frozenblock.wilderwild.item;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.server.EasyPacket;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Instrument;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.Optional;

public class CopperHorn extends Item {
    private static final String INSTRUMENT_KEY = "instrument";
    private final TagKey<Instrument> instrumentTag;
    private final int shift;

    public CopperHorn(Settings settings, TagKey<Instrument> instrumentTag, int shift) {
        super(settings);
        this.instrumentTag = instrumentTag;
        this.shift = shift;
    }

    public static ItemStack getStackForInstrument(Item item, RegistryEntry<Instrument> instrument) {
        ItemStack itemStack = new ItemStack(item);
        setInstrument(itemStack, instrument);
        return itemStack;
    }

    private static void setInstrument(ItemStack stack, RegistryEntry<Instrument> instrument) {
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        nbtCompound.putString(
                INSTRUMENT_KEY, (instrument.getKey().orElseThrow(() -> new IllegalStateException("Invalid instrument"))).getValue().toString()
        );
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        if (this.isIn(group)) {
            for (RegistryEntry<Instrument> registryEntry : Registry.INSTRUMENT.iterateEntries(this.instrumentTag)) {
                stacks.add(getStackForInstrument(RegisterItems.COPPER_HORN, registryEntry));
            }
        }

    }

    private Optional<RegistryEntry<Instrument>> getInstrument(ItemStack stack) {
        NbtCompound nbtCompound = stack.getNbt();
        if (nbtCompound != null) {
            Identifier identifier = Identifier.tryParse(nbtCompound.getString(INSTRUMENT_KEY));
            if (identifier != null) {
                return Registry.INSTRUMENT.getEntry(RegistryKey.of(Registry.INSTRUMENT_KEY, identifier));
            }
        }

        Iterator<RegistryEntry<Instrument>> iterator = Registry.INSTRUMENT.iterateEntries(this.instrumentTag).iterator();
        return iterator.hasNext() ? Optional.of(iterator.next()) : Optional.empty();
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        WilderWild.log(user, "Used Copper Horn", WilderWild.DEV_LOGGING);
        ItemStack itemStack = user.getStackInHand(hand);
        Optional<RegistryEntry<Instrument>> optional = this.getInstrument(itemStack);
        if (optional.isPresent()) {
            Instrument instrument = optional.get().value();
            user.setCurrentHand(hand);

            playSound(instrument, user, world, new CallbackInfo("playSound", true));

            return TypedActionResult.consume(itemStack);
        } else {
            return TypedActionResult.fail(itemStack);
        }
    }

    private void playSound(Instrument instrument, PlayerEntity user, World world, CallbackInfo info) {
        info.cancel();
        SoundEvent soundEvent = instrument.soundEvent();
        float range = instrument.range() / 16.0F;
        float soundPitch = (float) Math.pow(2.0D, (Math.round(((user.getPitch() - 90F) * -1) * 0.06666667F) + this.shift) * 0.08333333F);

        if (!world.isClient) {
            EasyPacket.createMovingRestrictionLoopingSound(world, user, soundEvent, SoundCategory.RECORDS, range, soundPitch, WilderWild.id("copper_horn"));
        }
        world.emitGameEvent(GameEvent.INSTRUMENT_PLAY, user.getPos(), GameEvent.Emitter.of(user));
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        Optional<RegistryEntry<Instrument>> optional = this.getInstrument(stack);
        return optional.map(instrumentRegistryEntry -> instrumentRegistryEntry.value().useDuration()).orElse(0);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.TOOT_HORN;
    }

}
