package org.hsneptune.elixirs.materials;

import net.minecraft.item.Item;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.hsneptune.elixirs.Elixirs;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.item.equipment.EquipmentType;

import java.util.Map;

public class ElixirsMaterial {

    public static ArmorMaterial createMaterial(String id, int baseDurability, Map<EquipmentType, Integer> defensePoints, int enchantability, RegistryEntry<SoundEvent> equipSound, float toughness, float knockbackResistance) {

		RegistryKey<EquipmentAsset> MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(Elixirs.MOD_ID, id));
		TagKey<Item> TAG_KEY = TagKey.of(Registries.ITEM.getKey(), Identifier.of(Elixirs.MOD_ID, "repairs_" + id + "_armor"));
        ArmorMaterial material = new ArmorMaterial(baseDurability, defensePoints, enchantability, equipSound, toughness, knockbackResistance, TAG_KEY, MATERIAL_KEY);

        // The majority of the time, you'll want the RegistryEntry of the material - especially for the ArmorItem constructor.
        return material;
    }

}
