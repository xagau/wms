/**
 * Copyright (c) 2023 AGUA.SAT, Permission is hereby granted, free of charge,
 * to any person obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */
package ai.genpen;

import java.util.HashMap;

public class PromptLibrary {
    static HashMap<String, Prompt> promptLibrary = new HashMap<>();
    static HashMap<String, String> templateLibrary = new HashMap<>();
    static {
        templateLibrary.put("generate_models", "Question:\\n\" +\n" +
                "It is a strict requirement that your objects DO NOT include any information about them. Names only. Application for a dairy farm. Good Example: -Milk.\n\n Good Example: -Eggs.\n\n Wrong output example: -Milk a substance good for your bones.\n\n" +
                "Please keep your list of objects to exactly @@numberOfModels of business objects/models. Evaluate the most appropriate number of business objects and use this as your guide.\n\n" +
                "\n\n" +
                "Using a bullet form list with the `-' character preceding each business object and then ending with a period `.' please summarize the following:\n\n" +
                "If you were creating a program called an \"@@application\". The objects created should consider that these objects will live within this general context:\n\n" +
                "@@context.\n\n" +
                "Answer:\n");

        templateLibrary.put("generate_attributes", "Evaluate the highest value attributes for a business object named @@targetModel. This model is designed to interact as a building block in a greater application.\n\n" +
                "Pay special attention to the relationships between objects and the applications likely goal. Use the fk prefix against attributes that would make suitable places to use foreign keys.\n\n" +
                "Check that the foreign key you have created exists in a model that has been defined and the variable is correct and suitable as an index to link the tables\n\n" +
                "The greater relationship and interactions between objects are defined by this context:\n\n" +
                "@@context\n\n" +
                "The ideal attribute followed by the best-suited primitive data type from this list: [boolean, byte, integer, short, long, float, date, double, string]. \n" +
                "Use the format: [attribute name, attribute datatype]. Example [has mouth, boolean]. Your attributes must be lower case for example [great attribute name]. Do not use any special characters or punctuation in your attribute names. \n" +
                "Omit all periods and end line characters. All attributes must be 3 words or less. A maximum and minimum number ( @@numberOfFields ) of attributes may be applied to the model.\n\n");


        templateLibrary.put("demo_models", "-Nose.\n-Eyes.\n-Eye Brows.\n-Mustache.-Beard.\n-Mouth.\n-Head Dimensions.\n");


    }


    public static Prompt createModelsPrompt(
            String name,
            String description,
            String template,
            String programmedPrompt,
            String context,
            String target,
            int modelCount,
            int fieldCount,
            String model
    ) {
        Prompt prompt = get(name);
        prompt.setTemplate(template);
        prompt.setDescription(description);
        prompt.setModel(model == null || model.isEmpty()? "text-davinci-003":model);
        prompt.setVariable("@@prompt", programmedPrompt);
        prompt.setVariable("@@context", context);
        prompt.setVariable("@@numberOfModels", "" + new Integer(modelCount));
        prompt.setVariable("@@numberOfFields", "" + new Integer(fieldCount));
        prompt.setVariable("@@targetModel", "" + target);
        prompt.setVariable("@@listAttributes", Boolean.TRUE.toString());
        prompt.resolve();
        return prompt;
    }

    public static Prompt createAttributesPrompt(
            String name,
            String description,
            String template,
            String programmedPrompt,
            String context,
            String provider,
            int modelCount,
            int fieldCount,
            String model,
            String target
    ) {
        Prompt prompt = get(name);
        prompt.setDescription(description);
        prompt.setTemplate(template);
        prompt.setModel(provider == null || provider.isEmpty()? "text-davinci-003":model);
        prompt.setVariable("@@prompt", programmedPrompt);
        prompt.setVariable("@@context", context);
        prompt.setVariable("@@numberOfModels", "" + new Integer(modelCount));
        prompt.setVariable("@@numberOfFields", "" + new Integer(fieldCount));
        prompt.setVariable("@@targetModel", "" + target);
        prompt.setVariable("@@listAttributes", Boolean.TRUE.toString());
        prompt.resolve();
        return prompt;
    }


    public static Prompt get(String name)
    {
        return promptLibrary.get(name);
    }
    public static String fromTemplate(String name) { return templateLibrary.get(name); }


    public void add(Prompt p)
    {
        if ( promptLibrary.containsValue(p)){
            System.out.println("prompt already contained");
            return;
        } else {
            p.resolve();
            promptLibrary.put(p.getPrompt(), p);
        }
    }
}
