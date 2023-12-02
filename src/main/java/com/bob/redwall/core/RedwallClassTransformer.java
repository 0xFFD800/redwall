package com.bob.redwall.core;

import static org.objectweb.asm.Opcodes.AASTORE;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.ANEWARRAY;
import static org.objectweb.asm.Opcodes.ARETURN;
import static org.objectweb.asm.Opcodes.ASM5;
import static org.objectweb.asm.Opcodes.DUP;
import static org.objectweb.asm.Opcodes.F2I;
import static org.objectweb.asm.Opcodes.FLOAD;
import static org.objectweb.asm.Opcodes.FMUL;
import static org.objectweb.asm.Opcodes.FRETURN;
import static org.objectweb.asm.Opcodes.GETFIELD;
import static org.objectweb.asm.Opcodes.GETSTATIC;
import static org.objectweb.asm.Opcodes.I2F;
import static org.objectweb.asm.Opcodes.ICONST_3;
import static org.objectweb.asm.Opcodes.ICONST_4;
import static org.objectweb.asm.Opcodes.IFNE;
import static org.objectweb.asm.Opcodes.INVOKEINTERFACE;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static org.objectweb.asm.Opcodes.IRETURN;
import static org.objectweb.asm.Opcodes.PUTSTATIC;
import static org.objectweb.asm.Opcodes.RETURN;

import java.util.ArrayList;
import java.util.Iterator;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.ParameterNode;
import org.objectweb.asm.tree.VarInsnNode;

import net.minecraft.client.Minecraft;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.ReflectionHelper.UnableToFindFieldException;

public class RedwallClassTransformer implements IClassTransformer {
	@Override
	public byte[] transform(String clazz, String arg, byte[] bytes) {
		if (clazz.equals("net.minecraft.item.ItemStack")) {
			System.out.println(this.getClass().getName() + ".transform() about to patch class net.minecraft.item.ItemStack (deobf)");
			return this.patchItemStackClassASM(clazz, bytes, false);
		}

		if (clazz.equals("afi")) {
			System.out.println(this.getClass().getName() + ".transform() about to patch class afi (obf)");
			return this.patchItemStackClassASM(clazz, bytes, true);
		}

		if (clazz.equals("net.minecraft.block.BlockLeaves")) {
			System.out.println(this.getClass().getName() + ".transform() about to patch class net.minecraft.block.BlockLeaves (deobf)");
			return this.patchBlockLeavesClassASM(clazz, bytes, false);
		}

		if (clazz.equals("aol")) {
			System.out.println(this.getClass().getName() + ".transform() about to patch class aol (obf)");
			return this.patchBlockLeavesClassASM(clazz, bytes, true);
		}

		if (clazz.equals("net.minecraft.block.BlockNewLeaf")) {
			System.out.println(this.getClass().getName() + ".transform() about to patch class net.minecraft.block.BlockNewLeaf (deobf)");
			return this.patchBlockLeafClassASM(clazz, bytes, false);
		}

		if (clazz.equals("apa")) {
			System.out.println(this.getClass().getName() + ".transform() about to patch class apa (obf)");
			return this.patchBlockLeafClassASM(clazz, bytes, true);
		}

		if (clazz.equals("net.minecraft.block.BlockOldLeaf")) {
			System.out.println(this.getClass().getName() + ".transform() about to patch class net.minecraft.block.BlockOldLeaf (deobf)");
			return this.patchBlockLeafClassASM(clazz, bytes, false);
		}

		if (clazz.equals("aph")) {
			System.out.println(this.getClass().getName() + ".transform() about to patch class aph (obf)");
			return this.patchBlockLeafClassASM(clazz, bytes, true);
		}

		if (clazz.equals("net.minecraft.client.renderer.color.BlockColors$4")) {
			System.out.println(this.getClass().getName() + ".transform() about to patch class net.minecraft.client.renderer.color.BlockColors$4 (deobf)");
			return this.patchBlockColors$4ClassASM(clazz, bytes, false);
		}

		if (clazz.equals("bez$7")) {
			System.out.println(this.getClass().getName() + ".transform() about to patch class bez$7 (obf)");
			return this.patchBlockColors$4ClassASM(clazz, bytes, true);
		}

		if (clazz.equals("net.minecraft.block.BlockSnow")) {
			System.out.println(this.getClass().getName() + ".transform() about to patch class net.minecraft.block.BlockSnow (deobf)");
			return this.patchBlockSnowClassASM(clazz, bytes, false);
		}

		if (clazz.equals("aqq")) {
			System.out.println(this.getClass().getName() + ".transform() about to patch class aqq (obf)");
			return this.patchBlockSnowClassASM(clazz, bytes, true);
		}

		if (clazz.equals("net.minecraft.util.CombatRules")) {
			System.out.println(this.getClass().getName() + ".transform() about to patch class net.minecraft.util.CombatRules (deobf)");
			return this.patchCombatRulesClassASM(clazz, bytes, false);
		}

		if (clazz.equals("up")) {
			System.out.println(this.getClass().getName() + ".transform() about to patch class up (obf)");
			return this.patchCombatRulesClassASM(clazz, bytes, true);
		}

		if (clazz.equals("net.minecraft.client.model.ModelBiped")) {
			System.out.println(this.getClass().getName() + ".transform() about to patch class net.minecraft.client.model.ModelBiped (deobf)");
			return this.patchModelBipedClassASM(clazz, bytes, false);
		}

		if (clazz.equals("bpx")) {
			System.out.println(this.getClass().getName() + ".transform() about to patch class bpx (obf)");
			return this.patchModelBipedClassASM(clazz, bytes, true);
		}

		if (clazz.equals("net.minecraft.client.Minecraft")) {
			System.out.println(this.getClass().getName() + ".transform() about to patch class net.minecraft.client.Minecraft (deobf)");
			return this.patchMinecraftClassASM(clazz, bytes, false);
		}

		if (clazz.equals("bib")) {
			System.out.println(this.getClass().getName() + ".transform() about to patch class bib (obf)");
			return this.patchMinecraftClassASM(clazz, bytes, true);
		}

		/*
		 * if(clazz.equals("net.minecraft.block.BlockGravel")) {
		 * System.out.println(this.getClass().getName() +
		 * ".transform() about to patch class net.minecraft.block.BlockGravel (deobf)");
		 * return this.patchBlockGravelClassASM(clazz, bytes, false); }
		 * 
		 * if(clazz.equals("anx")) { System.out.println(this.getClass().getName() +
		 * ".transform() about to patch class anx (obf)"); return
		 * this.patchBlockGravelClassASM(clazz, bytes, true); }
		 * 
		 * if(clazz.equals("net.minecraft.util.FoodStats")) {
		 * System.out.println(this.getClass().getName() +
		 * ".transform() about to patch class net.minecraft.util.FoodStats (deobf)");
		 * return this.patchFoodStatsClassASM(clazz, bytes, false); }
		 * 
		 * if(clazz.equals("aci")) { System.out.println(this.getClass().getName() +
		 * ".transform() about to patch class aci (obf)"); return
		 * this.patchFoodStatsClassASM(clazz, bytes, true); }
		 */

		return bytes;
	}

	public byte[] patchItemStackClassASM(String name, byte[] bytes, boolean obfuscated) {
		String targetMethodName = "";
		if (obfuscated)
			targetMethodName = "k";
		else targetMethodName = "getMaxDamage";
		boolean finished = false;

		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);

		Iterator<MethodNode> methods = classNode.methods.iterator();
		while (methods.hasNext()) {
			MethodNode m = methods.next();
			// Check if this is getMaxDamage and it's method signature is ()I which means
			// that it accepts nothing () and returns an integer (I)
			if ((m.name.equals(targetMethodName) && m.desc.equals("()I"))) {
				System.out.println(this.getClass().getName() + ".patchItemStackClassASM() inside target method " + targetMethodName);
				AbstractInsnNode currentNode = null;
				AbstractInsnNode targetNode = null;

				Iterator<AbstractInsnNode> iter = m.instructions.iterator();

				// Loop over the instruction set and find the instruction IRETURN which returns
				// an integer
				while (iter.hasNext()) {
					currentNode = iter.next();

					// Found it! save the index location of instruction IRETURN
					if (currentNode.getOpcode() == IRETURN)
						targetNode = currentNode;
				}

				InsnList toInject = new InsnList();

				toInject.add(new InsnNode(I2F));
				toInject.add(new VarInsnNode(ALOAD, 0));
				toInject.add(new MethodInsnNode(INVOKESTATIC, "com/bob/redwall/crafting/smithing/EquipmentModifierUtils", "getDurabilityMultiplier", obfuscated ? "(Lafi;)F" : "(Lnet/minecraft/item/ItemStack;)F", false));
				toInject.add(new InsnNode(FMUL));
				toInject.add(new InsnNode(F2I));

				m.instructions.insertBefore(targetNode, toInject);

				System.out.println(this.getClass().getName() + ".patchItemStackClassASM() patched method " + targetMethodName);
				finished = true;
				break;
			}
		}

		if (!finished) {
			System.out.println("Couldn't find method " + targetMethodName + ", trying again with func_77958_k.");
			targetMethodName = "func_77958_k";

			while (methods.hasNext()) {
				MethodNode m = methods.next();
				// Check if this is getMaxDamage and it's method signature is ()I which means
				// that it accepts nothing () and returns an integer (I)
				if ((m.name.equals(targetMethodName) && m.desc.equals("()I"))) {
					System.out.println(this.getClass().getName() + ".patchItemStackClassASM() inside target method " + targetMethodName);
					AbstractInsnNode currentNode = null;
					AbstractInsnNode targetNode = null;

					Iterator<AbstractInsnNode> iter = m.instructions.iterator();

					// Loop over the instruction set and find the instruction IRETURN which returns
					// an integer
					while (iter.hasNext()) {
						currentNode = iter.next();

						// Found it! save the index location of instruction IRETURN
						if (currentNode.getOpcode() == IRETURN)
							targetNode = currentNode;
					}

					InsnList toInject = new InsnList();

					toInject.add(new InsnNode(I2F));
					toInject.add(new VarInsnNode(ALOAD, 0));
					toInject.add(new MethodInsnNode(INVOKESTATIC, "com/bob/redwall/crafting/smithing/EquipmentModifierUtils", "getDurabilityMultiplier", obfuscated ? "(Lafi;)F" : "(Lnet/minecraft/item/ItemStack;)F", false));
					toInject.add(new InsnNode(FMUL));
					toInject.add(new InsnNode(F2I));

					m.instructions.insertBefore(targetNode, toInject);

					System.out.println(this.getClass().getName() + ".patchItemStackClassASM() patched method " + targetMethodName);
					finished = true;
					break;
				}
			}
		}

		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchBlockLeavesClassASM(String name, byte[] bytes, boolean obfuscated) {
		String staticFieldName = "WINTER"; // We want to insert this static field.
		String targetMethodName;
		if (obfuscated)
			targetMethodName = "c";
		else targetMethodName = "getActualState";

		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);

		boolean srg = false;
		if (!obfuscated) {
			try {
				ReflectionHelper.findField(Minecraft.class, "hasCrashed");
			} catch (UnableToFindFieldException ex) {
				srg = true;
			}
		}

		Iterator<MethodNode> iter = classNode.methods.iterator();
		while (iter.hasNext()) {
			MethodNode m = iter.next();
			if (m.name.equals(srg ? "func_176221_a" : targetMethodName)) {
				System.out.println("dundundun");
				classNode.methods.remove(m);
				continue;
			}

			if (m.name.equals("<clinit>") && m.desc.equals("()V")) {
				System.out.println(this.getClass().getName() + ".patchBlockLeavesClassASM() inside target method <clinit>");
				AbstractInsnNode currentNode = null;
				AbstractInsnNode targetNode = null;

				Iterator<AbstractInsnNode> instruct = m.instructions.iterator();

				while (instruct.hasNext()) {
					currentNode = instruct.next();

					if (currentNode instanceof InsnNode && currentNode.getOpcode() == RETURN) {
						targetNode = currentNode;
						break;
					}
				}

				InsnList toInject = new InsnList();

				LabelNode L2 = new LabelNode();
				toInject.add(L2);
				toInject.add(new LineNumberNode(26, L2));
				toInject.add(new LdcInsnNode("winter"));
				toInject.add(new MethodInsnNode(INVOKESTATIC, obfuscated ? "atv" : "net/minecraft/block/properties/PropertyBool", obfuscated ? "a" : srg ? "func_177716_a" : "create", obfuscated ? "(Ljava/lang/String;)Latv;" : "(Ljava/lang/String;)Lnet/minecraft/block/properties/PropertyBool;", false));
				toInject.add(new FieldInsnNode(PUTSTATIC, obfuscated ? "aol" : "net/minecraft/block/BlockLeaves", staticFieldName, obfuscated ? "Latv;" : "Lnet/minecraft/block/properties/PropertyBool;"));

				m.instructions.insertBefore(targetNode, toInject);

				System.out.println(this.getClass().getName() + ".patchBlockLeavesClassASM() patched method <clinit>");
				continue;
			}
		}

		if (srg)
			targetMethodName = "func_176221_a";
		classNode.methods.add(RedwallClassTransformer.constructBlockLeavesMethod0(targetMethodName, staticFieldName, obfuscated));
		classNode.fields.add(new FieldNode(ASM5, 0x19, staticFieldName, obfuscated ? "Latv;" : "Lnet/minecraft/block/properties/PropertyBool;", null, null));

		System.out.println(this.getClass().getName() + ".patchBlockLeavesClassASM() patched class " + name);
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public static MethodNode constructBlockLeavesMethod0(String targetMethodName, String staticFieldName, boolean obfuscated) {
		MethodNode method1 = new MethodNode();
		method1.name = targetMethodName;
		method1.desc = obfuscated ? "(Latj;Laju;Lco;)Latj;" : "(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;";
		method1.exceptions = new ArrayList<String>();
		ParameterNode param1 = new ParameterNode("state", 0);
		ParameterNode param2 = new ParameterNode("worldIn", 0);
		ParameterNode param3 = new ParameterNode("pos", 0);
		method1.parameters = new ArrayList<ParameterNode>();
		method1.parameters.add(param1);
		method1.parameters.add(param2);
		method1.parameters.add(param3);
		method1.access = Opcodes.ACC_PUBLIC;
		method1.maxStack = 4;
		method1.maxLocals = 4;
		LabelNode L0 = new LabelNode();
		LabelNode L1 = new LabelNode();
		LocalVariableNode thiz = new LocalVariableNode("this", obfuscated ? "Laol;" : "Lnet/minecraft/block/BlockLeaves;", null, L0, L1, 0);
		LocalVariableNode state = new LocalVariableNode("state", obfuscated ? "Latj;" : "Lnet/minecraft/block/state/IBlockState;", null, L0, L1, 1);
		LocalVariableNode worldIn = new LocalVariableNode("worldIn", obfuscated ? "Laju;" : "Lnet/minecraft/world/IBlockAccess;", null, L0, L1, 2);
		LocalVariableNode pos = new LocalVariableNode("pos", obfuscated ? "Lco;" : "Lnet/minecraft/util/math/BlockPos;", null, L0, L1, 3);
		method1.localVariables = new ArrayList<LocalVariableNode>();
		method1.localVariables.add(thiz);
		method1.localVariables.add(state);
		method1.localVariables.add(worldIn);
		method1.localVariables.add(pos);
		InsnList toInject = new InsnList();
		toInject.add(L0);
		toInject.add(new LineNumberNode(316, L0));
		toInject.add(new VarInsnNode(ALOAD, 1));
		toInject.add(new FieldInsnNode(GETSTATIC, obfuscated ? "aol" : "net/minecraft/block/BlockLeaves", staticFieldName, obfuscated ? "Latv;" : "Lnet/minecraft/block/properties/PropertyBool;"));
		toInject.add(new VarInsnNode(ALOAD, 2));
		toInject.add(new MethodInsnNode(INVOKESTATIC, "com/bob/redwall/RedwallUtils", "getSeason", obfuscated ? "(Lajq;)Lcom/bob/redwall/dimensions/redwall/EnumSeasons;" : "(Lnet/minecraft/world/IBlockAccess;)Lcom/bob/redwall/dimensions/redwall/EnumSeasons;", false));
		toInject.add(new MethodInsnNode(INVOKEVIRTUAL, "com/bob/redwall/dimensions/redwall/EnumSeasons", "isWinter", "()Z", false));
		toInject.add(new MethodInsnNode(INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;", false));
		toInject.add(new MethodInsnNode(INVOKEINTERFACE, obfuscated ? "atj" : "net/minecraft/block/state/IBlockState", obfuscated ? "a" : "withProperty", obfuscated ? "(Latz;Ljava/lang/Comparable;)Latj;" : "(Lnet/minecraft/block/properties/IProperty;Ljava/lang/Comparable;)Lnet/minecraft/block/state/IBlockState;", true));
		toInject.add(new InsnNode(ARETURN));
		toInject.add(L1);
		method1.instructions.add(toInject);
		return method1;
	}

	public byte[] patchBlockLeafClassASM(String name, byte[] bytes, boolean obfuscated) {
		String targetMethodName = "";
		if (obfuscated)
			targetMethodName = "b";
		else targetMethodName = "createBlockState";
		boolean finished = false;

		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);

		Iterator<MethodNode> methods = classNode.methods.iterator();
		while (methods.hasNext()) {
			MethodNode m = methods.next();
			if ((m.name.equals(targetMethodName) && m.desc.equals(obfuscated ? "()Latk;" : "()Lnet/minecraft/block/state/BlockStateContainer;"))) {
				System.out.println(this.getClass().getName() + ".patchBlockLeafClassASM() inside target method " + targetMethodName);
				AbstractInsnNode currentNode = null;
				AbstractInsnNode targetNode = null;
				int targetIndex2 = -1;

				Iterator<AbstractInsnNode> iter = m.instructions.iterator();

				int index = -1;

				while (iter.hasNext()) {
					index++;
					currentNode = iter.next();

					if (currentNode.getOpcode() == INVOKESPECIAL) {
						targetNode = currentNode;
					} else if (currentNode.getOpcode() == ANEWARRAY) {
						targetIndex2 = index - 1;
					}
				}

				AbstractInsnNode node = m.instructions.get(targetIndex2);
				m.instructions.set(node, new InsnNode(ICONST_4));

				InsnList toInject = new InsnList();

				toInject.add(new InsnNode(DUP));
				toInject.add(new InsnNode(ICONST_3));
				toInject.add(new FieldInsnNode(GETSTATIC, obfuscated ? "aol" : "net/minecraft/block/BlockLeaves", "WINTER", obfuscated ? "atv" : "Lnet/minecraft/block/properties/PropertyBool;"));
				toInject.add(new InsnNode(AASTORE));

				m.instructions.insertBefore(targetNode, toInject);

				System.out.println(this.getClass().getName() + ".patchBlockLeafClassASM() patched method " + targetMethodName);
				finished = true;
				break;
			}
		}

		if (!finished) {
			System.out.println("Couldn't find method " + targetMethodName + ", trying again with func_180661_e.");
			targetMethodName = "func_180661_e";

			while (methods.hasNext()) {
				MethodNode m = methods.next();
				if ((m.name.equals(targetMethodName) && m.desc.equals(obfuscated ? "()Latk;" : "()Lnet/minecraft/block/state/BlockStateContainer;"))) {
					System.out.println(this.getClass().getName() + ".patchBlockLeafClassASM() inside target method " + targetMethodName);
					AbstractInsnNode currentNode = null;
					AbstractInsnNode targetNode = null;
					int targetIndex2 = -1;

					Iterator<AbstractInsnNode> iter = m.instructions.iterator();

					int index = -1;

					while (iter.hasNext()) {
						index++;
						currentNode = iter.next();

						if (currentNode.getOpcode() == INVOKESPECIAL)
							targetNode = currentNode;
						else if (currentNode.getOpcode() == ANEWARRAY) 
							targetIndex2 = index - 1;
					}

					AbstractInsnNode node = m.instructions.get(targetIndex2);
					m.instructions.set(node, new InsnNode(ICONST_4));

					InsnList toInject = new InsnList();

					toInject.add(new InsnNode(DUP));
					toInject.add(new InsnNode(ICONST_3));
					toInject.add(new FieldInsnNode(GETSTATIC, obfuscated ? "aol" : "net/minecraft/block/BlockLeaves", "WINTER", obfuscated ? "atv" : "Lnet/minecraft/block/properties/PropertyBool;"));
					toInject.add(new InsnNode(AASTORE));

					m.instructions.insertBefore(targetNode, toInject);

					System.out.println(this.getClass().getName() + ".patchBlockLeafClassASM() patched method " + targetMethodName);
					finished = true;
					break;
				}
			}
		}

		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchBlockColors$4ClassASM(String name, byte[] bytes, boolean obfuscated) {
		String targetMethodName;
		String targetMethodDesc;
		if (obfuscated)
			targetMethodName = "a";
		else targetMethodName = "colorMultiplier";
		if (obfuscated)
			targetMethodDesc = "(Latj;Laju;Lco;I)I";
		else targetMethodDesc = "(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/math/BlockPos;I)I";
		boolean finished = false;

		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);

		boolean srg = false;

		Iterator<MethodNode> methods = classNode.methods.iterator();
		while (methods.hasNext()) {
			MethodNode m = methods.next();
			if ((m.name.equals(targetMethodName) && m.desc.equals(targetMethodDesc))) {
				System.out.println(this.getClass().getName() + ".patchBlockColors$4ClassASM() inside target method " + targetMethodName);
				AbstractInsnNode currentNode = null;
				AbstractInsnNode targetNode = null;

				Iterator<AbstractInsnNode> iter = m.instructions.iterator();

				while (iter.hasNext()) {
					currentNode = iter.next();
					if (currentNode.getOpcode() == INVOKESTATIC && ((MethodInsnNode) currentNode).name.equals(obfuscated ? "b" : srg ? "func_77469_b" : "getFoliageColorBirch")) {
						targetNode = currentNode;
						break;
					}
				}

				InsnList toInject = new InsnList();

				toInject.add(new VarInsnNode(ALOAD, 2));
				toInject.add(new VarInsnNode(ALOAD, 3));
				toInject.add(new MethodInsnNode(INVOKESTATIC, "com/bob/redwall/RedwallUtils", "getModdedBiomeFoliageColorBirch", obfuscated ? "(Lajq;Lco;)I" : "(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/math/BlockPos;)I", false));

				m.instructions.insertBefore(targetNode, toInject);
				m.instructions.remove(targetNode);

				System.out.println(this.getClass().getName() + ".patchBlockColors$4ClassASM() patched method " + targetMethodName);
				finished = true;
				break;
			}
		}

		if (!finished) {
			System.out.println("Couldn't find method " + targetMethodName + ", trying again with func_186720_a.");
			targetMethodName = "func_186720_a";
			srg = true;

			while (methods.hasNext()) {
				MethodNode m = methods.next();
				if ((m.name.equals(targetMethodName) && m.desc.equals(targetMethodDesc))) {
					System.out.println(this.getClass().getName() + ".patchBlockColors$4ClassASM() inside target method " + targetMethodName);
					AbstractInsnNode currentNode = null;
					AbstractInsnNode targetNode = null;

					Iterator<AbstractInsnNode> iter = m.instructions.iterator();

					while (iter.hasNext()) {
						currentNode = iter.next();
						if (currentNode.getOpcode() == INVOKESTATIC && ((MethodInsnNode) currentNode).name.equals(obfuscated ? "b" : srg ? "func_77469_b" : "getFoliageColorBirch")) {
							targetNode = currentNode;
							break;
						}
					}

					InsnList toInject = new InsnList();

					toInject.add(new VarInsnNode(ALOAD, 2));
					toInject.add(new VarInsnNode(ALOAD, 3));
					toInject.add(new MethodInsnNode(INVOKESTATIC, "com/bob/redwall/RedwallUtils", "getModdedBiomeFoliageColorBirch", obfuscated ? "(Lajq;Lco;)I" : "(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/math/BlockPos;)I", false));

					m.instructions.insertBefore(targetNode, toInject);
					m.instructions.remove(targetNode);

					System.out.println(this.getClass().getName() + ".patchBlockColors$4ClassASM() patched method " + targetMethodName);
					finished = true;
					break;
				}
			}
		}

		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchBlockSnowClassASM(String name, byte[] bytes, boolean obfuscated) {
		String targetMethodName = "";
		String targetMethodDesc = "";
		if (obfuscated) {
			targetMethodName = "b";
			targetMethodDesc = "(Lajq;Lco;Latj;Ljava/util/Random;)V";
		} else {
			targetMethodName = "updateTick";
			targetMethodDesc = "(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;Ljava/util/Random;)V";
		}

		boolean finished = false;

		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);

		Iterator<MethodNode> methods = classNode.methods.iterator();
		while (methods.hasNext()) {
			MethodNode m = methods.next();
			if ((m.name.equals(targetMethodName) && m.desc.equals(targetMethodDesc))) {
				System.out.println(this.getClass().getName() + ".patchBlockSnowClassASM() inside target method " + targetMethodName);
				AbstractInsnNode currentNode = null;
				AbstractInsnNode targetNode = null;

				Iterator<AbstractInsnNode> iter = m.instructions.iterator();

				while (iter.hasNext()) {
					currentNode = iter.next();

					if (currentNode instanceof InsnNode && ((InsnNode) currentNode).getOpcode() == RETURN) {
						targetNode = currentNode;
						break;
					}

					m.instructions.remove(currentNode);
				}

				InsnList toInject = new InsnList();

				toInject.add(new VarInsnNode(ALOAD, 1));
				toInject.add(new VarInsnNode(ALOAD, 2));
				toInject.add(new VarInsnNode(ALOAD, 3));
				toInject.add(new VarInsnNode(ALOAD, 4));
				toInject.add(new MethodInsnNode(INVOKESTATIC, "com/bob/redwall/RedwallUtils", "updateSnowBlock", targetMethodDesc, false));

				m.instructions.insertBefore(targetNode, toInject);

				System.out.println(this.getClass().getName() + ".patchBlockSnowClassASM() patched method " + targetMethodName);
				finished = true;
				break;
			}
		}

		if (!finished) {
			System.out.println("Couldn't find method " + targetMethodName + ", trying again with func_180650_b.");
			targetMethodName = "func_180650_b";

			while (methods.hasNext()) {
				MethodNode m = methods.next();
				if ((m.name.equals(targetMethodName) && m.desc.equals(targetMethodDesc))) {
					System.out.println(this.getClass().getName() + ".patchBlockSnowClassASM() inside target method " + targetMethodName);
					AbstractInsnNode currentNode = null;
					AbstractInsnNode targetNode = null;

					Iterator<AbstractInsnNode> iter = m.instructions.iterator();

					while (iter.hasNext()) {
						currentNode = iter.next();

						if (currentNode instanceof InsnNode && ((InsnNode) currentNode).getOpcode() == RETURN) {
							targetNode = currentNode;
							break;
						}

						m.instructions.remove(currentNode);
					}

					InsnList toInject = new InsnList();

					toInject.add(new VarInsnNode(ALOAD, 1));
					toInject.add(new VarInsnNode(ALOAD, 2));
					toInject.add(new VarInsnNode(ALOAD, 3));
					toInject.add(new VarInsnNode(ALOAD, 4));
					toInject.add(new MethodInsnNode(INVOKESTATIC, "com/bob/redwall/RedwallUtils", "updateSnowBlock", targetMethodDesc, false));

					m.instructions.insertBefore(targetNode, toInject);

					System.out.println(this.getClass().getName() + ".patchBlockSnowClassASM() patched method " + targetMethodName);
					finished = true;
					break;
				}
			}
		}

		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchCombatRulesClassASM(String name, byte[] bytes, boolean obfuscated) {
		String targetMethodName = "";
		String targetMethodDesc = "";
		if (obfuscated) {
			targetMethodName = "a";
			targetMethodDesc = "(FFF)F";
		} else {
			targetMethodName = "getDamageAfterAbsorb";
			targetMethodDesc = "(FFF)F";
		}

		boolean finished = false;

		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);

		Iterator<MethodNode> methods = classNode.methods.iterator();
		while (methods.hasNext()) {
			MethodNode m = methods.next();
			if ((m.name.equals(targetMethodName) && m.desc.equals(targetMethodDesc))) {
				System.out.println(this.getClass().getName() + ".patchCombatRulesClassASM() inside target method " + targetMethodName);
				AbstractInsnNode currentNode = null;
				AbstractInsnNode targetNode = null;

				Iterator<AbstractInsnNode> iter = m.instructions.iterator();

				while (iter.hasNext()) {
					currentNode = iter.next();

					if (currentNode instanceof InsnNode && ((InsnNode) currentNode).getOpcode() == FRETURN) {
						targetNode = currentNode;
						break;
					}

					m.instructions.remove(currentNode);
				}

				InsnList toInject = new InsnList();

				toInject.add(new VarInsnNode(FLOAD, 0));
				toInject.add(new VarInsnNode(FLOAD, 1));
				toInject.add(new VarInsnNode(FLOAD, 2));
				toInject.add(new MethodInsnNode(INVOKESTATIC, "com/bob/redwall/RedwallUtils", "doArmorCalc", targetMethodDesc, false));

				m.instructions.insertBefore(targetNode, toInject);

				System.out.println(this.getClass().getName() + ".patchCombatRulesClassASM() patched method " + targetMethodName);
				finished = true;
				break;
			}
		}

		if (!finished) {
			System.out.println("Couldn't find method " + targetMethodName + ", trying again with func_189427_a.");
			targetMethodName = "func_189427_a";

			while (methods.hasNext()) {
				MethodNode m = methods.next();
				if ((m.name.equals(targetMethodName) && m.desc.equals(targetMethodDesc))) {
					System.out.println(this.getClass().getName() + ".patchCombatRulesClassASM() inside target method " + targetMethodName);
					AbstractInsnNode currentNode = null;
					AbstractInsnNode targetNode = null;

					Iterator<AbstractInsnNode> iter = m.instructions.iterator();

					while (iter.hasNext()) {
						currentNode = iter.next();

						if (currentNode instanceof InsnNode && ((InsnNode) currentNode).getOpcode() == FRETURN) {
							targetNode = currentNode;
							break;
						}

						m.instructions.remove(currentNode);
					}

					InsnList toInject = new InsnList();

					toInject.add(new VarInsnNode(FLOAD, 0));
					toInject.add(new VarInsnNode(FLOAD, 1));
					toInject.add(new VarInsnNode(FLOAD, 2));
					toInject.add(new MethodInsnNode(INVOKESTATIC, "com/bob/redwall/RedwallUtils", "doArmorCalc", targetMethodDesc, false));

					m.instructions.insertBefore(targetNode, toInject);

					System.out.println(this.getClass().getName() + ".patchCombatRulesClassASM() patched method " + targetMethodName);
					finished = true;
					break;
				}
			}
		}

		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchModelBipedClassASM(String name, byte[] bytes, boolean obfuscated) {
		String targetMethodName = "";
		if (obfuscated)
			targetMethodName = "a";
		else targetMethodName = "setRotationAngles";
		boolean finished = false;

		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);

		Iterator<MethodNode> methods = classNode.methods.iterator();
		while (methods.hasNext()) {
			MethodNode m = methods.next();
			if ((m.name.equals(targetMethodName) && m.desc.equals(obfuscated ? "(FFFFFFLvg;)V" : "(FFFFFFLnet/minecraft/entity/Entity;)V"))) {
				System.out.println(this.getClass().getName() + ".patchModelBipedClassASM() inside target method " + targetMethodName);
				AbstractInsnNode currentNode = null;
				AbstractInsnNode targetNode = null;

				Iterator<AbstractInsnNode> iter = m.instructions.iterator();

				while (iter.hasNext()) {
					currentNode = iter.next();

					if (currentNode instanceof InsnNode && currentNode.getOpcode() == RETURN) {
						targetNode = currentNode;
						break;
					}

					m.instructions.remove(currentNode);
				}

				InsnList toInject = new InsnList();

				toInject.add(new VarInsnNode(ALOAD, 0));
				toInject.add(new VarInsnNode(FLOAD, 1));
				toInject.add(new VarInsnNode(FLOAD, 2));
				toInject.add(new VarInsnNode(FLOAD, 3));
				toInject.add(new VarInsnNode(FLOAD, 4));
				toInject.add(new VarInsnNode(FLOAD, 5));
				toInject.add(new VarInsnNode(FLOAD, 6));
				toInject.add(new VarInsnNode(ALOAD, 7));
				toInject.add(new MethodInsnNode(INVOKESTATIC, "com/bob/redwall/RedwallUtils", "doAnimationBiped", obfuscated ? "(Lbpx;FFFFFFLvg;)V" : "(Lnet/minecraft/client/model/ModelBiped;FFFFFFLnet/minecraft/entity/Entity;)V", false));

				m.instructions.insertBefore(targetNode, toInject);

				System.out.println(this.getClass().getName() + ".patchModelBipedClassASM() patched method " + targetMethodName);
				finished = true;
				break;
			}
		}

		if (!finished) {
			System.out.println("Couldn't find method " + targetMethodName + ", trying again with func_78087_a.");
			targetMethodName = "func_78087_a";

			while (methods.hasNext()) {
				MethodNode m = methods.next();
				if ((m.name.equals(targetMethodName) && m.desc.equals(obfuscated ? "(FFFFFFLvg;)V" : "(FFFFFFLnet/minecraft/entity/Entity;)V"))) {
					System.out.println(this.getClass().getName() + ".patchModelBipedClassASM() inside target method " + targetMethodName);
					AbstractInsnNode currentNode = null;
					AbstractInsnNode targetNode = null;

					Iterator<AbstractInsnNode> iter = m.instructions.iterator();

					while (iter.hasNext()) {
						currentNode = iter.next();

						if (currentNode instanceof InsnNode && currentNode.getOpcode() == RETURN) {
							targetNode = currentNode;
							break;
						}

						m.instructions.remove(currentNode);
					}

					InsnList toInject = new InsnList();

					toInject.add(new VarInsnNode(ALOAD, 0));
					toInject.add(new VarInsnNode(FLOAD, 1));
					toInject.add(new VarInsnNode(FLOAD, 2));
					toInject.add(new VarInsnNode(FLOAD, 3));
					toInject.add(new VarInsnNode(FLOAD, 4));
					toInject.add(new VarInsnNode(FLOAD, 5));
					toInject.add(new VarInsnNode(FLOAD, 6));
					toInject.add(new VarInsnNode(ALOAD, 7));
					toInject.add(new MethodInsnNode(INVOKESTATIC, "com/bob/redwall/RedwallUtils", "doAnimationBiped", obfuscated ? "(Lbpx;FFFFFFLvg;)V" : "(Lnet/minecraft/client/model/ModelBiped;FFFFFFLnet/minecraft/entity/Entity;)V", false));

					m.instructions.insertBefore(targetNode, toInject);

					System.out.println(this.getClass().getName() + ".patchModelBipedClassASM() patched method " + targetMethodName);
					finished = true;
					break;
				}
			}
		}

		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public byte[] patchMinecraftClassASM(String name, byte[] bytes, boolean obfuscated) {
		String targetMethodName;
		String targetMethodDesc;
		if (obfuscated)
			targetMethodName = "aE";
		else targetMethodName = "processKeyBinds";
		if (obfuscated)
			targetMethodDesc = "()V";
		else targetMethodDesc = "()V";
		boolean finished = false;

		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);

		boolean srg = false;

		Iterator<MethodNode> methods = classNode.methods.iterator();
		while (methods.hasNext()) {
			MethodNode m = methods.next();
			if ((m.name.equals(targetMethodName) && m.desc.equals(targetMethodDesc))) {
				System.out.println(this.getClass().getName() + ".patchMinecraftClassASM() inside target method " + targetMethodName);
				AbstractInsnNode currentNode = null;
				AbstractInsnNode targetNode = null;
				LabelNode targetLabel = null;

				Iterator<AbstractInsnNode> iter = m.instructions.iterator();

				while (iter.hasNext()) {
					currentNode = iter.next();
					if (currentNode.getOpcode() == GETFIELD && ((FieldInsnNode) currentNode).name.equals(obfuscated ? "ad" : srg ? "field_74313_G" : "keyBindUseItem")) {
						targetNode = currentNode.getNext().getNext();
						targetLabel = ((JumpInsnNode) targetNode).label;
						break;
					}
				}

				InsnList toInject = new InsnList();

				toInject.add(new VarInsnNode(ALOAD, 0));
				toInject.add(new FieldInsnNode(GETFIELD, obfuscated ? "bib" : "net/minecraft/client/Minecraft", obfuscated ? "t" : srg ? "field_71474_y" : "gameSettings", obfuscated ? "Lbid;" : "Lnet/minecraft/client/settings/GameSettings;"));
				toInject.add(new FieldInsnNode(GETFIELD, obfuscated ? "bid" : "net/minecraft/client/settings/GameSettings", obfuscated ? "af" : srg ? "field_74322_I" : "keyBindPickBlock", obfuscated ? "Lbhy;" : "Lnet/minecraft/client/settings/KeyBinding;"));
				toInject.add(new MethodInsnNode(INVOKEVIRTUAL, obfuscated ? "bhy" : "net/minecraft/client/settings/KeyBinding", obfuscated ? "e" : srg ? "func_151470_d" : "isKeyDown", "()Z", false));
				toInject.add(new JumpInsnNode(IFNE, targetLabel));
				toInject.add(new MethodInsnNode(INVOKESTATIC, "com/bob/redwall/RedwallUtils", "shouldNotReleaseMouse", "()Z", false));
				toInject.add(new JumpInsnNode(IFNE, targetLabel));

				m.instructions.insert(targetNode, toInject);

				System.out.println(this.getClass().getName() + ".patchMinecraftClassASM() patched method " + targetMethodName);
				finished = true;
				break;
			}
		}

		if (!finished) {
			System.out.println("Couldn't find method " + targetMethodName + ", trying again with func_184117_aA.");
			targetMethodName = "func_184117_aA";
			srg = true;

			while (methods.hasNext()) {
				MethodNode m = methods.next();
				if ((m.name.equals(targetMethodName) && m.desc.equals(targetMethodDesc))) {
					System.out.println(this.getClass().getName() + ".patchMinecraftClassASM() inside target method " + targetMethodName);
					AbstractInsnNode currentNode = null;
					AbstractInsnNode targetNode = null;
					LabelNode targetLabel = null;

					Iterator<AbstractInsnNode> iter = m.instructions.iterator();

					while (iter.hasNext()) {
						currentNode = iter.next();
						if (currentNode.getOpcode() == GETFIELD && ((FieldInsnNode) currentNode).name.equals(obfuscated ? "ad" : srg ? "field_74313_G" : "keyBindUseItem")) {
							targetNode = currentNode.getNext().getNext();
							targetLabel = ((JumpInsnNode) targetNode).label;
							break;
						}
					}

					InsnList toInject = new InsnList();

					toInject.add(new VarInsnNode(ALOAD, 0));
					toInject.add(new FieldInsnNode(GETFIELD, obfuscated ? "bib" : "net/minecraft/client/Minecraft", obfuscated ? "t" : srg ? "field_71474_y" : "gameSettings", obfuscated ? "Lbid;" : "Lnet/minecraft/client/settings/GameSettings;"));
					toInject.add(new FieldInsnNode(GETFIELD, obfuscated ? "bid" : "net/minecraft/client/settings/GameSettings", obfuscated ? "af" : srg ? "field_74322_I" : "keyBindPickBlock", obfuscated ? "Lbhy;" : "Lnet/minecraft/client/settings/KeyBinding;"));
					toInject.add(new MethodInsnNode(INVOKEVIRTUAL, obfuscated ? "bhy" : "net/minecraft/client/settings/KeyBinding", obfuscated ? "e" : srg ? "func_151470_d" : "isKeyDown", "()Z", false));
					toInject.add(new JumpInsnNode(IFNE, targetLabel));
					toInject.add(new MethodInsnNode(INVOKESTATIC, "com/bob/redwall/RedwallUtils", "shouldNotReleaseMouse", "()Z", false));
					toInject.add(new JumpInsnNode(IFNE, targetLabel));

					m.instructions.insert(targetNode, toInject);

					System.out.println(this.getClass().getName() + ".patchMinecraftClassASM() patched method " + targetMethodName);
					finished = true;
					break;
				}
			}
		}

		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	/*
	 * public byte[] patchBlockGravelClassASM(String name, byte[] bytes, boolean
	 * obfuscated) { String targetMethodName = ""; if(obfuscated) targetMethodName =
	 * "a"; else targetMethodName = "getItemDropped"; boolean finished = false;
	 * 
	 * ClassNode classNode = new ClassNode(); ClassReader classReader = new
	 * ClassReader(bytes); classReader.accept(classNode, 0);
	 * 
	 * Iterator<MethodNode> methods = classNode.methods.iterator();
	 * while(methods.hasNext()) { MethodNode m = methods.next(); int ireturn_index =
	 * -1;
	 * 
	 * if ((m.name.equals(targetMethodName) && m.desc.equals(obfuscated ?
	 * "(Latj;Ljava/util/Random;I)Lafg;" :
	 * "(Lnet/minecraft/block/state/IBlockState;Ljava/util/Random;I)Lnet/minecraft/item/Item;"
	 * ))) { System.out.println(this.getClass().getName() +
	 * ".patchBlockGravelClassASM() inside target method " + targetMethodName);
	 * AbstractInsnNode currentNode = null; AbstractInsnNode targetNode = null;
	 * 
	 * Iterator<AbstractInsnNode> iter = m.instructions.iterator();
	 * 
	 * int index = -1; int found = -1; while (iter.hasNext()) { index++; currentNode
	 * = iter.next();
	 * 
	 * if (currentNode instanceof FrameNode) { targetNode = currentNode; found++;
	 * if(found > 0) break; }
	 * 
	 * if(found == 0) m.instructions.remove(currentNode); }
	 * 
	 * System.out.println(this.getClass().getName() +
	 * ".patchBlockGravelClassASM() patched method " + targetMethodName); finished =
	 * true; break; } }
	 * 
	 * if(!finished) { System.out.println("Couldn't find method " + targetMethodName
	 * + ", trying again with func_180660_a."); targetMethodName = "func_180660_a";
	 * 
	 * 
	 * while(methods.hasNext()) { MethodNode m = methods.next(); int ireturn_index =
	 * -1;
	 * 
	 * if ((m.name.equals(targetMethodName) && m.desc.equals(obfuscated ?
	 * "(Latj;Ljava/util/Random;I)Lafg;" :
	 * "(Lnet/minecraft/block/state/IBlockState;Ljava/util/Random;I)Lnet/minecraft/item/Item;"
	 * ))) { System.out.println(this.getClass().getName() +
	 * ".patchBlockGravelClassASM() inside target method " + targetMethodName);
	 * AbstractInsnNode currentNode = null; AbstractInsnNode targetNode = null;
	 * 
	 * Iterator<AbstractInsnNode> iter = m.instructions.iterator();
	 * 
	 * int index = -1; int found = -1; while (iter.hasNext()) { index++; currentNode
	 * = iter.next();
	 * 
	 * if (currentNode instanceof FrameNode) { targetNode = currentNode; found++;
	 * if(found > 0) break; }
	 * 
	 * if(found == 0) m.instructions.remove(currentNode); }
	 * 
	 * System.out.println(this.getClass().getName() +
	 * ".patchBlockGravelClassASM() patched method " + targetMethodName); finished =
	 * true; break; } } }
	 * 
	 * ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS |
	 * ClassWriter.COMPUTE_FRAMES); classNode.accept(writer); return
	 * writer.toByteArray(); }
	 * 
	 * public byte[] patchFoodStatsClassASM(String name, byte[] bytes, boolean
	 * obfuscated) { String targetMethodName = ""; String targetMethodDesc = "";
	 * if(obfuscated) { targetMethodName = "a"; targetMethodDesc = "(Laax;)V"; }
	 * else { targetMethodName = "onUpdate"; targetMethodDesc =
	 * "(Lnet/minecraft/entity/player/EntityPlayer;)V"; } boolean finished = false;
	 * 
	 * ClassNode classNode = new ClassNode(); ClassReader classReader = new
	 * ClassReader(bytes); classReader.accept(classNode, 0);
	 * 
	 * Iterator<MethodNode> methods = classNode.methods.iterator();
	 * while(methods.hasNext()) { MethodNode m = methods.next(); int ireturn_index =
	 * -1;
	 * 
	 * if ((m.name.equals(targetMethodName) && m.desc.equals(targetMethodDesc))) {
	 * System.out.println(this.getClass().getName() +
	 * ".patchFoodStatsClassASM() inside target method " + targetMethodName);
	 * AbstractInsnNode currentNode = null; AbstractInsnNode targetNode = null;
	 * 
	 * Iterator<AbstractInsnNode> iter = m.instructions.iterator();
	 * 
	 * int index = -1; boolean found = false; while (iter.hasNext()) { index++;
	 * currentNode = iter.next();
	 * 
	 * if(found) m.instructions.remove(currentNode); if (currentNode instanceof
	 * LineNumberNode && ((LineNumberNode)currentNode).line == 59) { targetNode =
	 * currentNode; found = true; } else if (currentNode instanceof LineNumberNode
	 * && ((LineNumberNode)currentNode).line == 61) { break; } }
	 * 
	 * InsnList toInject = new InsnList();
	 * 
	 * toInject.add(new InsnNode(ICONST_0)); toInject.add(new VarInsnNode(ISTORE,
	 * 3)); toInject.add(new VarInsnNode(ALOAD, 1)); toInject.add(new
	 * MethodInsnNode(INVOKESTATIC, "com/bob/valour/ValourUtils", "updateFoodStats",
	 * targetMethodDesc, false));
	 * 
	 * m.instructions.insertBefore(targetNode, toInject);
	 * 
	 * System.out.println(this.getClass().getName() +
	 * ".patchFoodStatsClassASM() patched method " + targetMethodName); finished =
	 * true; break; } }
	 * 
	 * if(!finished) { System.out.println("Couldn't find method " + targetMethodName
	 * + ", trying again with func_75118_a."); targetMethodName = "func_75118_a";
	 * 
	 * 
	 * while(methods.hasNext()) { MethodNode m = methods.next(); int ireturn_index =
	 * -1;
	 * 
	 * if ((m.name.equals(targetMethodName) && m.desc.equals(targetMethodDesc))) {
	 * System.out.println(this.getClass().getName() +
	 * ".patchFoodStatsClassASM() inside target method " + targetMethodName);
	 * AbstractInsnNode currentNode = null; AbstractInsnNode targetNode = null;
	 * 
	 * Iterator<AbstractInsnNode> iter = m.instructions.iterator();
	 * 
	 * int index = -1; boolean found = false; while (iter.hasNext()) { index++;
	 * currentNode = iter.next();
	 * 
	 * if(found) m.instructions.remove(currentNode); if (currentNode instanceof
	 * LineNumberNode && ((LineNumberNode)currentNode).line == 59) { targetNode =
	 * currentNode; found = true; } else if (currentNode instanceof LineNumberNode
	 * && ((LineNumberNode)currentNode).line == 61) { break; } }
	 * 
	 * InsnList toInject = new InsnList();
	 * 
	 * toInject.add(new InsnNode(ICONST_0)); toInject.add(new VarInsnNode(ISTORE,
	 * 3)); toInject.add(new VarInsnNode(ALOAD, 1)); toInject.add(new
	 * MethodInsnNode(INVOKESTATIC, "com/bob/valour/ValourUtils", "updateFoodStats",
	 * targetMethodDesc, false));
	 * 
	 * m.instructions.insertBefore(targetNode, toInject);
	 * 
	 * System.out.println(this.getClass().getName() +
	 * ".patchFoodStatsClassASM() patched method " + targetMethodName); finished =
	 * true; break; } } }
	 * 
	 * ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS |
	 * ClassWriter.COMPUTE_FRAMES); classNode.accept(writer); return
	 * writer.toByteArray(); }
	 */

	public static void bla() {
		Minecraft thi = Minecraft.getMinecraft();
		if (!thi.gameSettings.keyBindUseItem.isKeyDown() && !thi.gameSettings.keyBindPickBlock.isKeyDown()) {
			thi.playerController.onStoppedUsingItem(thi.player);
		}
	}
}