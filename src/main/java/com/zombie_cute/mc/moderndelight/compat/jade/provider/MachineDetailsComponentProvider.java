package com.zombie_cute.mc.moderndelight.compat.jade.provider;

import com.zombie_cute.mc.moderndelight.block.biogas.BiogasDigesterControllerBlockEntity;
import com.zombie_cute.mc.moderndelight.block.biogas.BiogasDigesterIOBlockEntity;
import com.zombie_cute.mc.moderndelight.block.biogas.GasCanisterBlockEntity;
import com.zombie_cute.mc.moderndelight.block.fermenter.IndustrialFermenterBlockEntity;
import com.zombie_cute.mc.moderndelight.block.kitchenware.AdvanceFurnaceBlockEntity;
import com.zombie_cute.mc.moderndelight.block.kitchenware.gas_cooking.BakingTrayBlockEntity;
import com.zombie_cute.mc.moderndelight.block.kitchenware.FreezerBlockEntity;
import com.zombie_cute.mc.moderndelight.block.kitchenware.OvenBlockEntity;
import com.zombie_cute.mc.moderndelight.block.kitchenware.gas_cooking.deep_frying.DeepFryerBlockEntity;
import com.zombie_cute.mc.moderndelight.block.kitchenware.ice_cream_maker.IceCreamMakerBlockEntity;
import com.zombie_cute.mc.moderndelight.block.kitchenware.steaming.ElectricSteamerBlockEntity;
import com.zombie_cute.mc.moderndelight.block.kitchenware.steaming.BambooGrateBlockEntity;
import com.zombie_cute.mc.moderndelight.block.power.TeslaCoilBlockEntity;
import com.zombie_cute.mc.moderndelight.block.kitchenware.juice_extractor.JuiceExtractorBlockEntity;
import com.zombie_cute.mc.moderndelight.block.power.alternator.ACDCConverterBlockEntity;
import com.zombie_cute.mc.moderndelight.block.power.alternator.PhotovoltaicGeneratorBlockEntity;
import com.zombie_cute.mc.moderndelight.block.power.alternator.thermal_power.SterlingEngineBlock;
import com.zombie_cute.mc.moderndelight.block.power.alternator.thermal_power.SterlingEngineBlockEntity;
import com.zombie_cute.mc.moderndelight.block.trawler.TrawlerBlockEntity;
import com.zombie_cute.mc.moderndelight.compat.jade.JadePlugin;
import com.zombie_cute.mc.moderndelight.util.enums.BacteriaType;
import com.zombie_cute.mc.moderndelight.util.enums.CreamFlavor;
import com.zombie_cute.mc.moderndelight.util.power_util.ACGenerateAble;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum MachineDetailsComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;

    private static final String DATA_KEY = "MachineDetails";

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        NbtList details = accessor.getServerData().getList(DATA_KEY, NbtElement.COMPOUND_TYPE);
        for (int i = 0; i < details.size(); i++) {
            NbtCompound detail = details.getCompound(i);
            String key = detail.getString("Key");
            Text line;
            if (detail.getBoolean("Direct")) {
                line = Text.translatable(key).formatted(detail.getBoolean("Positive")
                        ? Formatting.GREEN : Formatting.RED);
            } else if (detail.contains("Text", NbtElement.STRING_TYPE)) {
                line = Text.translatable(key, Text.translatable(detail.getString("Text")));
            } else if (detail.contains("Argument", NbtElement.STRING_TYPE)) {
                line = Text.translatable(key, Text.translatable(detail.getString("Argument")), detail.getLong("Value"));
            } else if (detail.contains("Max")) {
                line = Text.translatable(key, detail.getLong("Value"), detail.getLong("Max"));
            } else {
                line = Text.translatable(key, detail.getLong("Value"));
            }
            tooltip.add(line);
        }
    }

    @Override
    public Identifier getUid() {
        return JadePlugin.MACHINE_DETAILS;
    }

    @Override
    public void appendServerData(NbtCompound data, BlockAccessor accessor) {
        BlockEntity entity = accessor.getBlockEntity();
        NbtList details = new NbtList();

        if (entity instanceof TrawlerBlockEntity trawler) {
            add(details, "trawler.trawler_interval", (trawler.getIntervalTick() - trawler.getFishingTicker()) / 20);
        } else if (entity instanceof SterlingEngineBlockEntity engine) {
            BlockState state = engine.getCachedState();
            addStatus(details, state.get(SterlingEngineBlock.SMALL_SOUND)
                    ? "sterling_engine.has_sponge" : "sterling_engine.no_sponge",
                    state.get(SterlingEngineBlock.SMALL_SOUND));
            addStatus(details, state.get(SterlingEngineBlock.IS_WORKING)
                    ? "sterling_engine.working" : "sterling_engine.not_working",
                    state.get(SterlingEngineBlock.IS_WORKING));
        } else if (entity instanceof JuiceExtractorBlockEntity juicer) {
            boolean powered = juicer.getCachedPower() > 0;
            addStatus(details, powered ? "ac_device.powered" : "ac_device.not_powered", powered);
            if (juicer.getProgress() > 0) {
                add(details, "juice_extractor.time", juicer.getProgress() / 20);
            }
        } else if (entity instanceof IndustrialFermenterBlockEntity fermenter) {
            BacteriaType dominate = fermenter.getDominantBacteria();
            boolean powered = fermenter.isPowered();
            addStatus(details, powered ? "ac_device.powered" : "ac_device.not_powered", powered);
            if (dominate != null) {
                add(details, "industrial_fermenter.dominate", dominate.getTranslationKey());
            }
            add(details, "industrial_fermenter.temperature", fermenter.getRealTemperature());
            if (fermenter.getMaxProgress() > 0) {
                add(details, "industrial_fermenter.progress", fermenter.getMaxProgress() - fermenter.getProgress());
            }
        } else if (entity instanceof ElectricSteamerBlockEntity steamer) {
            add(details, "electric_steamer.steam", steamer.getSteam(), 1000);
            add(details, "electric_steamer.active_slots", steamer.getActiveCookingSlots());
        } else if (entity instanceof IceCreamMakerBlockEntity iceCreamMaker) {
            boolean powered = iceCreamMaker.isPowered();
            addStatus(details, powered ? "ac_device.powered" : "ac_device.not_powered", powered);
            if (iceCreamMaker.getProgress() > 0) {
                add(details, "ice_cream_maker.progress", iceCreamMaker.getProgress());
            }
            for (int i = 0; i < 3; i++) {
                CreamFlavor flavor = iceCreamMaker.getSelectedFlavor(i);
                if (flavor != CreamFlavor.NULL) {
                    add(details, "ice_cream_maker.flavor", iceCreamMaker.getSelectedFlavorAmount(i),
                            flavor.getTranslationKey());
                }
            }
        } else if (entity instanceof FreezerBlockEntity freezer) {
            boolean powered = freezer.isPowered();
            addStatus(details, powered ? "ac_device.powered" : "ac_device.not_powered", powered);
            add(details, "freezer.cooling_time", freezer.getCoolTime() / 20);
            if (freezer.getProgress() > 0) {
                add(details, "freezer.progress", (freezer.getMaxProgress()) - (freezer.getProgress() / 20));
            }
        } else if (entity instanceof OvenBlockEntity oven) {
            if (oven.getBurnTime() > 0) {
                add(details, "oven.fuel_time", oven.getBurnTime() / 20);
            }
            if (oven.getProgress() > 0) {
                add(details, "oven.progress", (oven.getMaxProgress() / 20) - (oven.getProgress() / 20));
            }
        } else if (entity instanceof AdvanceFurnaceBlockEntity furnace) {
            add(details, "advance_furnace.active_slots", furnace.getActiveCookingSlots());
            if (furnace.getBurnTime() > 0) {
                add(details, "advance_furnace.fuel_time", furnace.getBurnTime() / 20);
            }
        } else if (entity instanceof DeepFryerBlockEntity fryer) {
            add(details, "deep_fryer.heated", fryer.isHeated() ? "moderndelight.jade.tooltip.deep_fryer.running"
                    : "moderndelight.jade.tooltip.deep_fryer.stopped");
            add(details, "deep_fryer.active_slots", fryer.getActiveCookingSlots());
        } else if (entity instanceof BakingTrayBlockEntity tray) {
            add(details, "baking_tray.stir_fry", tray.getStirFryTimes(), 5);
            add(details, "baking_tray.heat_source", tray.isHeated()
                    ? "moderndelight.jade.tooltip.baking_tray.heated"
                    : "moderndelight.jade.tooltip.baking_tray.cold");
        } else if (entity instanceof BambooGrateBlockEntity grate) {
            add(details, "bamboo_grate.heat", grate.isHeated()
                    ? "moderndelight.jade.tooltip.bamboo_grate.heated"
                    : "moderndelight.jade.tooltip.bamboo_grate.cold");
            add(details, "bamboo_grate.cover", grate.isCovered()
                    ? "moderndelight.jade.tooltip.bamboo_grate.covered"
                    : "moderndelight.jade.tooltip.bamboo_grate.uncovered");
            add(details, "bamboo_grate.layer", grate.getCurrentLayer(), 3);
            add(details, "bamboo_grate.active_slots", grate.getActiveCookingSlots());
        } else if (entity instanceof GasCanisterBlockEntity canister) {
            addStatus(details, canister.fluidIsGas() ? "gas_canister.gas" : "gas_canister.not_gas", !canister.fluidIsGas());
        } else if (entity instanceof BiogasDigesterControllerBlockEntity digester) {
            if (digester.isChecked()) {
                add(details, "biogas_digester_controller.gas", digester.getGasValue(), digester.getMaxGasValue());
                add(details, "biogas_digester_controller.volume", digester.getCurrentSize());
            }
            add(details, "biogas_digester_controller.structure", digester.isChecked()
                    ? "moderndelight.jade.tooltip.biogas_digester_controller.valid"
                    : "moderndelight.jade.tooltip.biogas_digester_controller.invalid");
        } else if (entity instanceof BiogasDigesterIOBlockEntity io) {
            add(details, "biogas_digester_controller.structure", io.isChecked()
                    ? "moderndelight.jade.tooltip.biogas_digester_controller.valid"
                    : "moderndelight.jade.tooltip.biogas_digester_controller.invalid");
            if (io.isChecked()) {
                add(details, "biogas_digester_io.gas", io.getGasValue());
                if (io.isCrafting()) {
                    add(details, "biogas_digester_io.progress", (io.getMaxProgress() / 20) - (io.getProgress() / 20));
                }
            }
        } else if (entity instanceof PhotovoltaicGeneratorBlockEntity generator) {
            add(details, "dc_generator.power", generator.getEfficiency());
        } else if (entity instanceof ACDCConverterBlockEntity converter) {
            add(details, "ac_dc_converter.mode", converter.getIsACMode()
                    ? "moderndelight.jade.tooltip.ac_dc_converter.mode_ac"
                    : "moderndelight.jade.tooltip.ac_dc_converter.mode_dc");
            add(details, "ac_dc_converter.speed", converter.getWorkSpeed(), converter.getMaxWorkSpeed());
        } else if (entity instanceof TeslaCoilBlockEntity coil) {
            add(details, "tesla_coil.efficiency", coil.getEfficiency());
            add(details, "tesla_coil.devices", coil.getAvailableDevices());
        }

        if (entity instanceof ACGenerateAble generator) {
            add(details, "ac_generator.power", generator.getEfficiency());
        }

        if (!details.isEmpty()) {
            data.put(DATA_KEY, details);
        }
    }

    private static void add(NbtList details, String key, long value) {
        NbtCompound detail = new NbtCompound();
        detail.putString("Key", "moderndelight.jade.tooltip." + key);
        detail.putLong("Value", value);
        details.add(detail);
    }

    private static void add(NbtList details, String key, long value, long max) {
        NbtCompound detail = new NbtCompound();
        detail.putString("Key", "moderndelight.jade.tooltip." + key);
        detail.putLong("Value", value);
        detail.putLong("Max", max);
        details.add(detail);
    }

    private static void add(NbtList details, String key, long value, String argument) {
        NbtCompound detail = new NbtCompound();
        detail.putString("Key", "moderndelight.jade.tooltip." + key);
        detail.putLong("Value", value);
        detail.putString("Argument", argument);
        details.add(detail);
    }

    private static void add(NbtList details, String key, String text) {
        NbtCompound detail = new NbtCompound();
        detail.putString("Key", "moderndelight.jade.tooltip." + key);
        detail.putString("Text", text);
        details.add(detail);
    }

    private static void addStatus(NbtList details, String key, boolean positive) {
        NbtCompound detail = new NbtCompound();
        detail.putString("Key", "moderndelight.jade.tooltip." + key);
        detail.putBoolean("Direct", true);
        detail.putBoolean("Positive", positive);
        details.add(detail);
    }
}
