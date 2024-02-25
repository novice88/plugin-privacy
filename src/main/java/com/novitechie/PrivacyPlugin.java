package com.novitechie;

import com.janetfilter.core.plugin.MyTransformer;
import com.janetfilter.core.plugin.PluginEntry;

import java.util.Arrays;
import java.util.List;

public class PrivacyPlugin  implements PluginEntry {
    @Override
    public String getName() {
        return "PRIVACY";
    }

    @Override
    public String getAuthor() {
        return "novice.li";
    }

    @Override
    public List<MyTransformer> getTransformers() {
        return Arrays.asList(new VMOptionsTransformer(),new PluginClassLoaderTransformer());
    }
}
