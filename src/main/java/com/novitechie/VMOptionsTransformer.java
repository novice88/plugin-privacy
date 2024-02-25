package com.novitechie;

import com.janetfilter.core.plugin.MyTransformer;
import jdk.internal.org.objectweb.asm.*;
import jdk.internal.org.objectweb.asm.commons.AdviceAdapter;
import jdk.internal.org.objectweb.asm.tree.*;

import static jdk.internal.org.objectweb.asm.Opcodes.*;

public class VMOptionsTransformer implements MyTransformer {
    @Override
    public String getHookClassName() {
        return "com/intellij/diagnostic/VMOptions";
    }

    @Override
    public byte[] transform(String className, byte[] classBytes, int order) throws Exception {
        ClassReader reader = new ClassReader(classBytes);
        ClassNode node = new ClassNode(ASM5);
        reader.accept(node, 0);
        for (MethodNode m : node.methods) {
            if ("getUserOptionsFile".equals(m.name)) {
                InsnList list = new InsnList();
                list.add(new MethodInsnNode(INVOKESTATIC, "com/novitechie/StackTraceRule", "check", "()Z", false));
                LabelNode labelNode = new LabelNode();
                list.add(new JumpInsnNode(IFEQ,labelNode));
                list.add(new InsnNode(ACONST_NULL));
                list.add(new InsnNode(ARETURN));
                list.add(labelNode);
                m.instructions.insert(list);
            }
        }
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        node.accept(writer);
        return writer.toByteArray();
    }
}
