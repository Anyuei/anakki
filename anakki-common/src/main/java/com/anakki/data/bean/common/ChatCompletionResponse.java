package com.anakki.data.bean.common;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ClassName: WXYYMessage
 * Description:
 *
 * @author Anakki
 * @date 2023/11/29 17:19
 */

public class ChatCompletionResponse {
    private String id;
    private String object;
    private long created;
    private String result;
    @JsonProperty("is_truncated")
    private boolean isTruncated;
    @JsonProperty("need_clear_history")
    private boolean needClearHistory;
    @JsonProperty("finish_reason")
    private String finishReason;
    private Usage usage;
    // Getter and Setter methods
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getObject() {
        return object;
    }
    public void setObject(String object) {
        this.object = object;
    }
    public long getCreated() {
        return created;
    }
    public void setCreated(long created) {
        this.created = created;
    }
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public boolean isTruncated() {
        return isTruncated;
    }
    public void setTruncated(boolean truncated) {
        isTruncated = truncated;
    }
    public boolean isNeedClearHistory() {
        return needClearHistory;
    }
    public void setNeedClearHistory(boolean needClearHistory) {
        this.needClearHistory = needClearHistory;
    }
    public String getFinishReason() {
        return finishReason;
    }
    public void setFinishReason(String finishReason) {
        this.finishReason = finishReason;
    }
    public Usage getUsage() {
        return usage;
    }
    public void setUsage(Usage usage) {
        this.usage = usage;
    }
    public static class Usage {
        @JsonProperty("prompt_tokens")
        private int promptTokens;
        @JsonProperty("completion_tokens")
        private int completionTokens;
        @JsonProperty("total_tokens")
        private int totalTokens;
        // Getter and Setter methods
        public int getPromptTokens() {
            return promptTokens;
        }
        public void setPromptTokens(int promptTokens) {
            this.promptTokens = promptTokens;
        }
        public int getCompletionTokens() {
            return completionTokens;
        }
        public void setCompletionTokens(int completionTokens) {
            this.completionTokens = completionTokens;
        }
        public int getTotalTokens() {
            return totalTokens;
        }
        public void setTotalTokens(int totalTokens) {
            this.totalTokens = totalTokens;
        }
    }
}