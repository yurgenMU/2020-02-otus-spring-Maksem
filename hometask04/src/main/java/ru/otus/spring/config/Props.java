package ru.otus.spring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import ru.otus.spring.domain.User;


@ConfigurationProperties(prefix = "application")
public class Props {

    private String questionsResource;
    private String questionsFileThreshold;
    private String successMessage;
    private String failureMessage;
    private String nameQuery;

    private User user;

    private String surnameQuery;

    public String getQuestionsResource() {
        return questionsResource;
    }

    public void setQuestionsResource(String questionsResource) {
        this.questionsResource = questionsResource;
    }

    public String getQuestionsFileThreshold() {
        return questionsFileThreshold;
    }

    public void setQuestionsFileThreshold(String questionsFileThreshold) {
        this.questionsFileThreshold = questionsFileThreshold;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }

    public String getNameQuery() {
        return nameQuery;
    }

    public void setNameQuery(String nameQuery) {
        this.nameQuery = nameQuery;
    }

    public String getSurnameQuery() {
        return surnameQuery;
    }

    public void setSurnameQuery(String surnameQuery) {
        this.surnameQuery = surnameQuery;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
