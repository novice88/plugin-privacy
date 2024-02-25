package com.novitechie;

import com.janetfilter.core.plugin.MyTransformer;
import jdk.internal.org.objectweb.asm.*;
import jdk.internal.org.objectweb.asm.commons.AdviceAdapter;

import static jdk.internal.org.objectweb.asm.Opcodes.ASM5;

public class VMOptionsTransformer implements MyTransformer {
    @Override
    public String getHookClassName() {
        return "com/intellij/diagnostic/VMOptions";
    }

    @Override
    public byte[] transform(String className, byte[] classBytes, int order) throws Exception {
        ClassReader cr = new ClassReader(classBytes);
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_FRAMES);
        cr.accept(new ClassVisitor(ASM5, cw) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                if (name.equals("getUserOptionsFile")) {
                    MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
                    return new AdviceAdapter(api, mv, access, name, desc) {
                        @Override
                        protected void onMethodEnter() {
                            mv.visitMethodInsn(INVOKESTATIC, "com/novitechie/StackTraceRule", "check", "()Z", false);
                            Label l0 = new Label();
                            mv.visitJumpInsn(IFEQ, l0);
                            mv.visitInsn(ACONST_NULL);
                            mv.visitInsn(ARETURN);
                            mv.visitLabel(l0);
                        }
                    };
                }
                return super.visitMethod(access, name, desc, signature, exceptions);
            }
        }, 6);
        return cw.toByteArray();
    }
}
