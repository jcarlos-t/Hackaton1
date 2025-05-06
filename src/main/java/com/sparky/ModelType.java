package com.sparky;

public enum ModelType {
    GPT_4("openai-gpt4"),
    META_LLAMA("meta-llama"),
    DEEPSPEAK("deepspeak"),
    GITHUB_MULTIMODAL("github-multimodal");

    private final String modelName;
    ModelType(String modelName) { this.modelName = modelName; }
    public String getModelName() { return modelName; }
}
