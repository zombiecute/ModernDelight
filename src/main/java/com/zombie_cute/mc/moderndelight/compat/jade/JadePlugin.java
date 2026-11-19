package com.zombie_cute.mc.moderndelight.compat.jade;

import com.zombie_cute.mc.moderndelight.ModernDelightMain;
import com.zombie_cute.mc.moderndelight.block.biogas.*;
import com.zombie_cute.mc.moderndelight.block.fermenter.IndustrialFermenterBlock;
import com.zombie_cute.mc.moderndelight.block.kitchenware.*;
import com.zombie_cute.mc.moderndelight.block.kitchenware.gas_cooking.BakingTrayBlock;
import com.zombie_cute.mc.moderndelight.block.kitchenware.gas_cooking.deep_frying.DeepFryerBlock;
import com.zombie_cute.mc.moderndelight.block.kitchenware.ice_cream_maker.IceCreamMakerBlock;
import com.zombie_cute.mc.moderndelight.block.kitchenware.juice_extractor.JuiceExtractorBlock;
import com.zombie_cute.mc.moderndelight.block.kitchenware.juice_extractor.JuiceExtractorBlockEntity;
import com.zombie_cute.mc.moderndelight.block.kitchenware.gas_cooking.BakingTrayBlockEntity;
import com.zombie_cute.mc.moderndelight.block.kitchenware.gas_cooking.deep_frying.DeepFryerBlockEntity;
import com.zombie_cute.mc.moderndelight.block.kitchenware.ice_cream_maker.IceCreamMakerBlockEntity;
import com.zombie_cute.mc.moderndelight.block.kitchenware.steaming.BambooGrateBlock;
import com.zombie_cute.mc.moderndelight.block.kitchenware.steaming.ElectricSteamerBlock;
import com.zombie_cute.mc.moderndelight.block.kitchenware.steaming.ElectricSteamerBlockEntity;
import com.zombie_cute.mc.moderndelight.block.kitchenware.steaming.BambooGrateBlockEntity;
import com.zombie_cute.mc.moderndelight.block.fermenter.IndustrialFermenterBlockEntity;
import com.zombie_cute.mc.moderndelight.block.multiblock.LargeMachinePartBlock;
import com.zombie_cute.mc.moderndelight.block.power.ChargingPostBlock;
import com.zombie_cute.mc.moderndelight.block.power.ChargingPostBlockEntity;
import com.zombie_cute.mc.moderndelight.block.power.TeslaCoilBlock;
import com.zombie_cute.mc.moderndelight.block.power.TeslaCoilBlockEntity;
import com.zombie_cute.mc.moderndelight.block.power.alternator.ACDCConverterBlock;
import com.zombie_cute.mc.moderndelight.block.power.alternator.ACDCConverterBlockEntity;
import com.zombie_cute.mc.moderndelight.block.power.alternator.PhotovoltaicGeneratorBlock;
import com.zombie_cute.mc.moderndelight.block.power.alternator.PhotovoltaicGeneratorBlockEntity;
import com.zombie_cute.mc.moderndelight.block.power.batteries.*;
import com.zombie_cute.mc.moderndelight.block.power.alternator.thermal_power.FaradayGeneratorBlock;
import com.zombie_cute.mc.moderndelight.block.power.alternator.thermal_power.FaradayGeneratorBlockEntity;
import com.zombie_cute.mc.moderndelight.block.power.alternator.thermal_power.SterlingEngineBlock;
import com.zombie_cute.mc.moderndelight.block.power.alternator.thermal_power.SterlingEngineBlockEntity;
import com.zombie_cute.mc.moderndelight.block.power.alternator.wind_power.WindTurbineControllerBlock;
import com.zombie_cute.mc.moderndelight.block.power.alternator.wind_power.WindTurbineControllerBlockEntity;
import com.zombie_cute.mc.moderndelight.block.trawler.TrawlerBlock;
import com.zombie_cute.mc.moderndelight.block.trawler.TrawlerBlockEntity;
import com.zombie_cute.mc.moderndelight.compat.jade.provider.MachineDetailsComponentProvider;
import net.minecraft.util.Identifier;
import snownee.jade.api.Accessor;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

import java.util.function.Supplier;

@WailaPlugin
public class JadePlugin implements IWailaPlugin {
    public static final Identifier MACHINE_DETAILS = Identifier.of(ModernDelightMain.MOD_ID, "machine_details");

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(MachineDetailsComponentProvider.INSTANCE, TrawlerBlockEntity.class);
        registration.registerBlockDataProvider(MachineDetailsComponentProvider.INSTANCE, SterlingEngineBlockEntity.class);
        registration.registerBlockDataProvider(MachineDetailsComponentProvider.INSTANCE, FaradayGeneratorBlockEntity.class);
        registration.registerBlockDataProvider(MachineDetailsComponentProvider.INSTANCE, WindTurbineControllerBlockEntity.class);
        registration.registerBlockDataProvider(MachineDetailsComponentProvider.INSTANCE, JuiceExtractorBlockEntity.class);
        registration.registerBlockDataProvider(MachineDetailsComponentProvider.INSTANCE, IndustrialFermenterBlockEntity.class);
        registration.registerBlockDataProvider(MachineDetailsComponentProvider.INSTANCE, ElectricSteamerBlockEntity.class);
        registration.registerBlockDataProvider(MachineDetailsComponentProvider.INSTANCE, IceCreamMakerBlockEntity.class);
        registration.registerBlockDataProvider(MachineDetailsComponentProvider.INSTANCE, FreezerBlockEntity.class);
        registration.registerBlockDataProvider(MachineDetailsComponentProvider.INSTANCE, OvenBlockEntity.class);
        registration.registerBlockDataProvider(MachineDetailsComponentProvider.INSTANCE, AdvanceFurnaceBlockEntity.class);
        registration.registerBlockDataProvider(MachineDetailsComponentProvider.INSTANCE, DeepFryerBlockEntity.class);
        registration.registerBlockDataProvider(MachineDetailsComponentProvider.INSTANCE, BakingTrayBlockEntity.class);
        registration.registerBlockDataProvider(MachineDetailsComponentProvider.INSTANCE, BambooGrateBlockEntity.class);
        registration.registerBlockDataProvider(MachineDetailsComponentProvider.INSTANCE, GasCanisterBlockEntity.class);
        registration.registerBlockDataProvider(MachineDetailsComponentProvider.INSTANCE, BiogasDigesterControllerBlockEntity.class);
        registration.registerBlockDataProvider(MachineDetailsComponentProvider.INSTANCE, BiogasDigesterIOBlockEntity.class);
        registration.registerBlockDataProvider(MachineDetailsComponentProvider.INSTANCE, PhotovoltaicGeneratorBlockEntity.class);
        registration.registerBlockDataProvider(MachineDetailsComponentProvider.INSTANCE, ACDCConverterBlockEntity.class);
        registration.registerBlockDataProvider(MachineDetailsComponentProvider.INSTANCE, TeslaCoilBlockEntity.class);

    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.addRayTraceCallback((hit, accessor, original) ->
                redirectMachinePart(accessor, registration::blockAccessor));
        registration.registerBlockComponent(MachineDetailsComponentProvider.INSTANCE, TrawlerBlock.class);
        registration.registerBlockComponent(MachineDetailsComponentProvider.INSTANCE, SterlingEngineBlock.class);
        registration.registerBlockComponent(MachineDetailsComponentProvider.INSTANCE, FaradayGeneratorBlock.class);
        registration.registerBlockComponent(MachineDetailsComponentProvider.INSTANCE, WindTurbineControllerBlock.class);
        registration.registerBlockComponent(MachineDetailsComponentProvider.INSTANCE, JuiceExtractorBlock.class);
        registration.registerBlockComponent(MachineDetailsComponentProvider.INSTANCE, IndustrialFermenterBlock.class);
        registration.registerBlockComponent(MachineDetailsComponentProvider.INSTANCE, ElectricSteamerBlock.class);
        registration.registerBlockComponent(MachineDetailsComponentProvider.INSTANCE, IceCreamMakerBlock.class);
        registration.registerBlockComponent(MachineDetailsComponentProvider.INSTANCE, FreezerBlock.class);
        registration.registerBlockComponent(MachineDetailsComponentProvider.INSTANCE, OvenBlock.class);
        registration.registerBlockComponent(MachineDetailsComponentProvider.INSTANCE, AdvanceFurnaceBlock.class);
        registration.registerBlockComponent(MachineDetailsComponentProvider.INSTANCE, DeepFryerBlock.class);
        registration.registerBlockComponent(MachineDetailsComponentProvider.INSTANCE, BakingTrayBlock.class);
        registration.registerBlockComponent(MachineDetailsComponentProvider.INSTANCE, BambooGrateBlock.class);
        registration.registerBlockComponent(MachineDetailsComponentProvider.INSTANCE, GasCanisterBlock.class);
        registration.registerBlockComponent(MachineDetailsComponentProvider.INSTANCE, BiogasDigesterControllerBlock.class);
        registration.registerBlockComponent(MachineDetailsComponentProvider.INSTANCE, BiogasDigesterIOBlock.class);
        registration.registerBlockComponent(MachineDetailsComponentProvider.INSTANCE, PhotovoltaicGeneratorBlock.class);
        registration.registerBlockComponent(MachineDetailsComponentProvider.INSTANCE, ACDCConverterBlock.class);
        registration.registerBlockComponent(MachineDetailsComponentProvider.INSTANCE, TeslaCoilBlock.class);

    }

    static Accessor<?> redirectMachinePart(Accessor<?> accessor, Supplier<BlockAccessor.Builder> builder) {
        if (!(accessor instanceof BlockAccessor block) || !(block.getBlock() instanceof LargeMachinePartBlock part)) {
            return accessor;
        }
        var world = block.getLevel();
        var partPos = block.getPosition();
        var rootPos = part.getMachinePos(block.getBlockState(), partPos);
        if (!world.isChunkLoaded(rootPos)) return accessor;
        var rootState = part.findMachine(world, partPos, block.getBlockState());
        if (rootState == null) return accessor;

        // Jade uses the accessor's hit position to request server data and its state to select providers.
        return builder.get().from(block)
                .hit(block.getHitResult().withBlockPos(rootPos))
                .blockState(rootState)
                .blockEntity(() -> world.getBlockEntity(rootPos))
                .requireVerification()
                .build();
    }
}
