package net.frozenblock.wilderwild.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;

public class FloatingSculkBubbleParticle extends RisingParticle {
    private final SpriteSet spriteProvider;
    private final SoundEvent sound;
    private final int stayInflatedTime;

    private float currentInflation = 0;
    private float targetInflation = 2;

    public int getLightColor(float tint) {
        return 240;
    }

    protected FloatingSculkBubbleParticle(ClientLevel clientWorld, double x, double y, double z, double size, double maxAge, double yVel, SpriteSet spriteProvider) {
        super(clientWorld, x, y, z, 0, 0, 0);
        this.xd = (Math.random() - 0.5) / 9.5;
        this.zd = (Math.random() - 0.5) / 9.5;
        this.spriteProvider = spriteProvider;
        this.setSpriteFromAge(spriteProvider);
        this.yd = yVel;
        this.sound = size <= 0 ? RegisterSounds.FLOATING_SCULK_BUBBLE_POP : RegisterSounds.FLOATING_SCULK_BUBBLE_BIG_POP;
        if (size >= 1) {
            this.scale((float) (1.4F + size));
            this.xd = (Math.random() - 0.5) / 10.5;
            this.zd = (Math.random() - 0.5) / 10.5;
        }
        this.lifetime = (int) Math.max(maxAge, 10);
        this.stayInflatedTime = (4 - this.lifetime) * -1;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public void setSpriteFromAge(SpriteSet spriteProvider) {
        if (!this.removed) {
            int i = this.age < 3 ? this.age : (this.age < this.stayInflatedTime ? 3 : this.age - (this.stayInflatedTime) + 4);
            this.setSprite(spriteProvider.get(i, 7));
        }
    }

    @Override
    public void tick() {
        super.tick();
        int flateAge = this.age - (this.stayInflatedTime) + 4;
        switch (this.age) {
            case 1 -> {
                this.currentInflation = 0;
                this.targetInflation = 2;
            }
            case 2 -> {
                this.currentInflation = 1;
                this.targetInflation = 1.4F;
            }
            case 3 -> {
                this.currentInflation = 1;
                this.targetInflation = 1.3F;
            }
            case 4 -> {
                this.currentInflation = 1.3F;
                this.targetInflation = 0.7F;
            }
            case 5 -> {
                this.currentInflation = 0.7F;
                this.targetInflation = 1.2F;
            }
            case 6 -> {
                this.currentInflation = 1.2F;
                this.targetInflation = 0.9F;
            }
            case 7 -> {
                this.currentInflation = 0.9F;
                this.targetInflation = 1.04F;
            }
            case 8 -> {
                this.currentInflation = 1.02F;
                this.targetInflation = 0.95F;
            }
            case 9 -> {
                this.currentInflation = 0.95F;
                this.targetInflation = 1;
            }
            default -> {
                switch (flateAge) {
                    case 3 -> {
                        this.currentInflation = 1F;
                        this.targetInflation = 1.3F;
                    }
                    case 4 -> {
                        this.currentInflation = 1;
                        this.targetInflation = 1.2F;
                    }
                    case 5 -> {
                        this.currentInflation = 1;
                        this.targetInflation = 1.05F;
                    }
                    case 6 -> {
                        this.currentInflation = 1.05F;
                        this.targetInflation = 1.1F;
                    }
                    case 7 -> {
                        this.currentInflation = 1.1F;
                        this.targetInflation = 1.4F;
                    }
                    case 8 -> {
                        this.currentInflation = 1.4F;
                        this.targetInflation = 1.65F;
                    }
                    default -> {
                        this.currentInflation = 1;
                        this.targetInflation = 1;
                    }
                }
            }
        }

        if (this.age == this.stayInflatedTime + 1) {
            Minecraft client = Minecraft.getInstance();
            if (client != null) {
                level.playSound(client.player, this.x, this.y, this.z, this.sound, SoundSource.BLOCKS, 0.4F, level.random.nextFloat() * 0.2F + 0.8F);
                this.setParticleSpeed(0, 0, 0);
            }
        }
        this.setSpriteFromAge(this.spriteProvider);
    }

    public float getQuadSize(float tickDelta) {
        return this.quadSize * Mth.lerp(tickDelta, this.currentInflation, this.targetInflation);
    }

    @Environment(EnvType.CLIENT)
    public static class BubbleFactory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public BubbleFactory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double x, double y, double z, double size, double maxAge, double yVel) {
            FloatingSculkBubbleParticle bubble = new FloatingSculkBubbleParticle(clientWorld, x, y, z, size, maxAge, yVel, this.spriteProvider);
            bubble.setAlpha(1.0F);
            return bubble;
        }
    }

}
