package org.fish.compiler;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;

import org.fish.silas.Rune;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

public class RuneProcessor extends AbstractProcessor {
    private static final String TAG = "RuneProcessor";

    private List<Element> mElements;

    @Override
    public synchronized void init(ProcessingEnvironment env) {
        super.init(env);
        mElements = new ArrayList<>();
        logD("init");
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new TreeSet<>(Collections.singletonList(
                Rune.class.getCanonicalName()));
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment env) {
        Set<? extends Element> elements = env.getElementsAnnotatedWith(Rune.class);
        if (!env.processingOver()) {
            mElements.addAll(elements);
        }
        if (env.processingOver()) {
            generateNodePool(mElements);
            mElements.clear();
        }

        return true;
    }

    private void generateNodePool(Collection<? extends Element> elements) {
        CodeBlock.Builder initBlockBuilder = CodeBlock.builder();
        initBlockBuilder.addStatement("pool = new HashMap<String, String>(64)");
        for (Element ele : elements) {
            Rune rune = ele.getAnnotation(Rune.class);
            logD("id=" + rune.id() + ",type:" + ele.asType());
            String type = ele.asType().toString();
            initBlockBuilder.addStatement("pool.put($S, $S)", rune.id(), type);
        }
        CodeBlock initBlock = initBlockBuilder.build();

        TypeSpec pool = TypeSpec.classBuilder("NodePool")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addStaticBlock(initBlock)
                .addMethod(MethodSpec.methodBuilder("find")
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .addParameter(ParameterSpec.builder(String.class, "id").build())
                        .addCode(CodeBlock.builder().addStatement("return (String)pool.get(id)").build())
                        .returns(String.class)
                        .build())
                .addField(FieldSpec.builder(HashMap.class, "pool")
                        .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                        .build())
                .build();

        JavaFile file = JavaFile.builder("org.karic.silas", pool)
                .build();

        try {
            file.writeTo(System.out);
            file.writeTo(processingEnv.getFiler());
        } catch (Exception e) {
            logE(e.getMessage());
        }
    }

    private void logD(String message) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, TAG + ":" + message);
    }

    private void logE(String message) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, TAG + ":" + message);
    }
}
