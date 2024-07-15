package ai.genpen;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Prompt {
    private String initiator;
    private String template = "";
    private String prompt;
    private String context;
    private int ink;
    private int maxTokens;
    private String model;
    private String name;
    private String description;
    private Timestamp modified = new Timestamp(System.currentTimeMillis());
    private Timestamp created = new Timestamp(System.currentTimeMillis());

    private HashMap<String, Prompt> mappings = new HashMap<>();

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public int getInk() {
        return ink;
    }

    public void setInk(int ink) {
        this.ink = ink;
    }

    public int getMaxTokens() {
        return maxTokens;
    }

    public void setMaxTokens(int maxTokens) {
        this.maxTokens = maxTokens;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void add(String name, Prompt prompt){

        mappings.put(name, prompt);
    }

    public HashMap<String, Prompt> getMappings() {
        return mappings;
    }

    public void setMappings(HashMap<String, Prompt> mappings) {
        this.mappings = mappings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getModified() {
        return modified;
    }

    public void setModified(Timestamp modified) {
        this.modified = modified;
    }

    HashMap<String, String> variables = new HashMap<>();
    public void setVariable(String varName, String arg) {
        variables.put(varName, arg);
    }

    public void resolve() {
        Set set = variables.keySet();
        Iterator itr = set.iterator();
        while(itr.hasNext()){
            String vn = (String)itr.next();
            String arg = variables.get(vn);
            template = template.replaceAll(vn, arg);
        }
        setModified(new Timestamp(System.currentTimeMillis()));
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
