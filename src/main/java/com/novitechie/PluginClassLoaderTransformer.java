package com.novitechie;

import com.janetfilter.core.plugin.MyTransformer;
import jdk.internal.org.objectweb.asm.*;
import jdk.internal.org.objectweb.asm.commons.AdviceAdapter;

import static jdk.internal.org.objectweb.asm.Opcodes.ASM5;

public class PluginClassLoaderTransformer implements MyTransformer {
    @Override
    public String getHookClassName() {
        return "com/intellij/ide/plugins/cl/PluginClassLoader";
    }

    @Override
    public byte[] transform(String className, byte[] classBytes, int order) throws Exception {
        ClassReader cr = new ClassReader(classBytes);
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_FRAMES);
        cr.accept(new ClassVisitor(ASM5, cw) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                if (name.equals("loadClass")) {
                    MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
                    return new AdviceAdapter(api, mv, access, name, desc) {
                        @Override
                        protected void onMethodEnter() {
                            mv.visitVarInsn(ALOAD, 1);
                            mv.visitMethodInsn(INVOKESTATIC, "com/novitechie/LoadClassRule", "check", "(Ljava/lang/String;)V", false);
                        }
                    };
                }
                return super.visitMethod(access, name, desc, signature, exceptions);
            }
        }, 6);
        return cw.toByteArray();
    }
}
