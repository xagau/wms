package ai.genpen;
import org.primefaces.diamond.shared.Response;
public class GPAI {

    private String sessionId = null;
    private String email = null;

    public Response request(String _prompt, String email) {
        Prompt prompt = new Prompt();
        prompt.setPrompt(_prompt);
        prompt.setInitiator(email);

        try {
            Brain brain = new Brain();
            String r = brain.evaluate(_prompt, email);
            Response res = new Response();
            res.setResponse(r);
            res.setRecipient(email);
            return res;
        } catch(Exception ex) { ex.printStackTrace(); }
        return null;
    }

    public Response request(String email) {

        try {
            Brain brain = new Brain();

            String r = brain.request(email);
            Response res = new Response();
            res.setResponse(r);
            res.setRecipient(email);
            return res;

        } catch(Exception ex) { ex.printStackTrace(); }
        return null;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
